import { useForm } from "@vaadin/hilla-react-form";
import { Signal, useSignal } from "@vaadin/hilla-react-signals";
import { Button, Checkbox, CheckboxGroup, ComboBox, Dialog, FormLayout, Select, TextField } from "@vaadin/react-components";
import UserApp from "Frontend/generated/com/example/application/data/UserApp";
import UserAppModel from "Frontend/generated/com/example/application/data/UserAppModel";
import KeycloakUserRecord from "Frontend/generated/com/example/application/services/KeycloakService/KeycloakUserRecord";
import { KeycloakService, RoleService, UserAppService } from "Frontend/generated/endpoints";
import { useEffect, useState } from "react";

interface dialogProps {
    modalOpen : Signal<boolean>,
    username: string | undefined
    refreshGrid: ()=>void
}

export default function UserAction({modalOpen, username,refreshGrid} : dialogProps) {
    const { read,field,model,submit } = useForm(UserAppModel,{
        onSubmit: async (userapp)=>{
            await UserAppService.saveUserApp(userapp).then(x => {
                refreshGrid()
                modalOpen.value = false
            })
        }
    })
    const keycloakUsers = useSignal<KeycloakUserRecord[]>([])
    const [roles, setRoles] = useState<string[]>([])
    useEffect(()=>{
        const fetchData = async()=>{
            if(username === undefined) {
                await UserAppService.newUserApp().then(read)
            }
            else {
                await UserAppService.findByUsername(username).then(read)
            }
            await KeycloakService.getKeycloakUsers().then(results => keycloakUsers.value = results)
            await RoleService.list().then(result => setRoles(result))
        }
        fetchData()
    },[username])

   
  return (
    <Dialog
        headerTitle={'Add/Edit User'}
        opened={modalOpen.value}
        footer={
            <>
                <Button onClick={submit}>Save</Button>
                <Button onClick={()=>modalOpen.value = false}>Cancel</Button>
            </>
        }
    >
        <FormLayout>
            {/* <TextField {...field(model.username)} label={"Username"}  /> */}
            {/* <ComboBox label={'Keycloak users'} itemLabelPath={"label"} itemIdPath={"id"} items={keycloakUsers.value} {...field(model.username)} /> */}
            <Select items={keycloakUsers.value} {...field(model.username)} label={"Username"} />
            <TextField  {...field(model.name)} label={"Name"} />
            <CheckboxGroup {...field(model.roles)}>
                {
                roles.map((value,index) => (
                    <Checkbox value={value} key={index} label={value}  />
                ))
                }
            </CheckboxGroup>
        </FormLayout>

    </Dialog>
  )
}

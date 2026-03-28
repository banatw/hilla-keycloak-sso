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
            if(selectedItem.value)
            userapp.username = selectedItem.value
            await UserAppService.saveUserApp(userapp).then(x => {
                refreshGrid()
                modalOpen.value = false
            })
        }
    })
    // const keycloakUsers = useSignal<KeycloakUserRecord[]>([])
    const keycloakUsers = useSignal<string[]>([])
    const [roles, setRoles] = useState<string[]>([])
    // const selectedItem = useSignal<KeycloakUserRecord | undefined>(undefined)
    const selectedItem = useSignal<string >('')
    useEffect(()=>{
        const fetchData = async()=>{
            if(username === undefined) {
                await UserAppService.newUserApp().then(r=>{
                    read(r)
                    selectedItem.value = ''
                })
            }
            else {
                await UserAppService.findByUsername(username).then(r=>{
                    read(r)
                    selectedItem.value = r.username
                })
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
            {/* {
                username === undefined ? <ComboBox  items={keycloakUsers2.value} 
                    onSelectedItemChanged={(e) => {
                        const username = e.detail.value
                        selectedItem2.value = username ? username : ''
                    }}
                /> 
                :  <Select items={keycloakUsers2.value} {...field(model.username)} label={"Username"} readonly  />
            }            */}
            {/* <ComboBox  items={keycloakUsers.value} itemLabelPath="label" itemValuePath="id"  selectedItem={selectedItem.value} /> */}
            <ComboBox  items={keycloakUsers.value} 
                    onSelectedItemChanged={(e) => {
                        const username = e.detail.value
                        selectedItem.value = username ? username : ''
                    }} selectedItem={selectedItem.value}
                /> 
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

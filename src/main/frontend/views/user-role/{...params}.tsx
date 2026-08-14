import { ViewConfig } from "@vaadin/hilla-file-router/types.js"
import { useForm } from "@vaadin/hilla-react-form"
import { Button, FormLayout, FormRow, MultiSelectComboBox, Notification, TextField } from "@vaadin/react-components"
import UserModel from "Frontend/generated/com/example/application/data/UserModel"
import { RoleService, UserRoleService } from "Frontend/generated/endpoints"
import { useEffect, useState } from "react"
import { useNavigate, useParams } from "react-router"

export const config: ViewConfig = {
    title: 'Add/Edit User',
    rolesAllowed: ['ADMIN'],
    menu: {
        exclude: true
    },
}
export default function ActionView() {
    const { "*": params } = useParams()
    const id = params?.split('/').at(1)

    const [roles, setRoles] = useState<string[]>([])
    const navigate = useNavigate()

    const form = useForm(UserModel, {
        onSubmit: async (user) => {
            await UserRoleService.save(user).then((user) => {
                Notification.show(`User ${user.username} telah disimpan`, { theme: 'success', position: 'top-end' })
            }).catch(() => {
                Notification.show(`User sudah ada`, { theme: 'error', position: 'top-end' })
            })
        }
    })

    const fetchData = async () => {
        if (id) {
            await UserRoleService.get(Number(id)).then(form.read)
        }
        await RoleService.roles().then(result => setRoles(result))
    }

    useEffect(() => {
        fetchData()
    }, [])
    return (
        <FormLayout>
            <TextField  {...form.field(form.model.username)} label={'Username'} />
            <TextField  {...form.field(form.model.name)} label={'Name'} />
            <MultiSelectComboBox {...form.field(form.model.roles)} items={roles} />
            <FormRow theme="spacing">
                <Button onClick={form.submit} disabled={form.invalid || form.submitting}>Simpan</Button>
                <Button onClick={() => navigate(`/user-role`)}>Kembali</Button>
            </FormRow>
        </FormLayout>
    )
}

import { ViewConfig } from "@vaadin/hilla-file-router/types.js";
import { AutoGrid, AutoGridRef, useGridDataProvider } from "@vaadin/hilla-react-crud";
import { Button, Grid, GridColumn, HorizontalLayout, Notification, VerticalLayout } from "@vaadin/react-components";
import User from "Frontend/generated/com/example/application/data/User";
import UserModel from "Frontend/generated/com/example/application/data/UserModel";
import { UserRoleService } from "Frontend/generated/endpoints";
import React from "react";
import { NavLink, useNavigate } from "react-router";

export const config: ViewConfig = {
    menu: { order: 0, icon: 'line-awesome/svg/users-cog-solid.svg' },
    title: 'User-role',
    rolesAllowed: ['ADMIN'],
};


export default function UserView() {
    const navigate = useNavigate()

    const btnActionRenderer = ({ item }: { item: User }) => {
        const user = item
        return (
            <HorizontalLayout theme="spacing">
                <NavLink to={`/user-role/edit/${user.id}`}>Edit</NavLink>
                <Button onClick={async () => {
                    if (confirm('are u sure?'))
                        UserRoleService.delete(user).then(() => {
                            gridRef.current?.refresh()
                            Notification.show(`User : ${user.name} berhasil dihapus`, { theme: 'success', position: 'top-end' })
                        })
                }}>Delete</Button>
            </HorizontalLayout>
        )
    }

    const gridRef = React.useRef<AutoGridRef>(null)
    return (
        <VerticalLayout>
            <NavLink to={`/user-role/add`} >Add</NavLink>
            <AutoGrid
                ref={gridRef}
                service={UserRoleService}
                model={UserModel}
                customColumns={
                    [
                        <GridColumn renderer={btnActionRenderer} />
                    ]
                }
            />
        </VerticalLayout>
    )
}

import { ViewConfig } from "@vaadin/hilla-file-router/types.js";
import { AutoGrid, useGridDataProvider } from "@vaadin/hilla-react-crud";
import { Button, Grid, GridColumn, HorizontalLayout, VerticalLayout } from "@vaadin/react-components";
import User from "Frontend/generated/com/example/application/data/User";
import UserModel from "Frontend/generated/com/example/application/data/UserModel";
import { UserRoleService } from "Frontend/generated/endpoints";
import { useNavigate } from "react-router";

export const config: ViewConfig = {
    menu: { order: 0, icon: 'line-awesome/svg/users-cog-solid.svg' },
    title: 'User-role',
    rolesAllowed: ['ADMIN'],
};


export default function UserRoleView() {
    const navigate = useNavigate()

    const btnActionRenderer = ({ item }: { item: User }) => {
        const user = item
        return (
            <HorizontalLayout theme="spacing">
                <Button onClick={() => {
                    navigate(`/user-role/edit/${item.id}`)
                }}>Edit</Button>
                <Button>Delete</Button>
            </HorizontalLayout>
        )
    }

    return (
        <VerticalLayout>
            <Button onClick={() => navigate(`/user-role/add`)}>Add</Button>
            <AutoGrid
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

import { ViewConfig } from "@vaadin/hilla-file-router/types.js"
import { AutoGrid, AutoGridRef } from "@vaadin/hilla-react-crud"
import { useSignal } from "@vaadin/hilla-react-signals"
import { Button, ContextMenu, GridColumn, GridElement, MenuBar, Notification, VerticalLayout } from "@vaadin/react-components"
import UserApp from "Frontend/generated/com/example/application/data/UserApp"
import UserAppModel from "Frontend/generated/com/example/application/data/UserAppModel"
import { UserAppService } from "Frontend/generated/endpoints"
import React, { useEffect } from "react"
import UserAction from "./_UserAction"

const config: ViewConfig = {
    rolesAllowed: ['ROLE_ADMIN'],
    title: 'Assign Role',
    menu: {
        title: "Assign role",
        icon: 'line-awesome/svg/users-solid.svg'
    }
}

export default function IndexAdmin() {
  const selectedUserApp = useSignal<UserApp[]>([])
  const selectedUsername = useSignal<string | undefined>(undefined)
  const gridRef = React.useRef<AutoGridRef>(null)
  const menuItems = [
    {text: 'Edit'},
    {text: 'Delete'}
  ]

   const refreshGrid = () => {
    gridRef.current?.refresh()      
  }
  const modalOpen = useSignal<boolean>(false)

  const ButtonActionRenderer = ({item} : {item: UserApp})=>{
    const userApp = item
    return(
      <MenuBar items={menuItems} onItemSelected={(e)=>{
            if(selectedUserApp.value.length === 0) {
              Notification.show('Pegawai belum dipilih')
              return
            }
          
        const text = e.detail.value.text.toLowerCase()
        if(text === 'edit') {
          modalOpen.value = true
        }
        else {
          if(confirm("Are you sure?")) {
            const deleteUserApp = async(x: UserApp)=>{
              await UserAppService.delete(x).then(x => {
                refreshGrid();
              })
            }
            deleteUserApp(userApp)
          }
        }
      }}  />
    )
  }

  return (
    
    <VerticalLayout>
      <Button onClick={()=>{
        modalOpen.value = true
        selectedUsername.value = undefined
      }}>Add</Button>
        <AutoGrid service={UserAppService} model={UserAppModel} selectedItems={selectedUserApp.value} ref={gridRef}
          onActiveItemChanged={(e)=>{
            const userApp = e.detail.value
            selectedUserApp.value = userApp ? [userApp] : []
            selectedUsername.value = e.detail.value.username
          }}
          customColumns={[
            <GridColumn key={'action'} renderer={ButtonActionRenderer} header="Action"   />
          ]}
        />
        { selectedUserApp.value && <UserAction modalOpen={modalOpen} username={selectedUsername.value} refreshGrid={refreshGrid}  />}
    </VerticalLayout>
  )
}

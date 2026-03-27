import { createMenuItems, useViewConfig } from '@vaadin/hilla-file-router/runtime.js';
import { effect, signal, useSignal } from '@vaadin/hilla-react-signals';
import { AppLayout, Avatar, Button, DrawerToggle, Icon, SideNav, SideNavItem } from '@vaadin/react-components';
import { UserEndpoint } from 'Frontend/generated/endpoints';
import { useAuth } from 'Frontend/util/auth.js';
import { Suspense, useEffect } from 'react';
import { Link, NavLink, Outlet, useLocation, useNavigate } from 'react-router';

const documentTitleSignal = signal('');
effect(() => {
  document.title = documentTitleSignal.value;
});

// Publish for Vaadin to use
(window as any).Vaadin.documentTitleSignal = documentTitleSignal;

export default function MainLayout() {
  const currentTitle = useViewConfig()?.title;
  const navigate = useNavigate();
  const location = useLocation();

  useEffect(() => {
    if (currentTitle) {
      documentTitleSignal.value = currentTitle;
    }
  }, [currentTitle]);

  const { state, logout } = useAuth();
  // const profilePictureUrl =
  //   state.user &&
  //   `data:image;base64,${btoa(
  //     state.user.profilePicture.reduce((str, n) => str + String.fromCharCode((n + 256) % 256), ''),
  //   )}`;
  const LogoutButton =()=>{
    return(
      <Button onClick={()=>{
        // const link = document.createElement('a')
        // link.href = "http://localhost:8082"
        // link.click()
        logout()
      }

      }>Logout</Button>
    )
  }

  return (
    <AppLayout primarySection="drawer">
      <div slot="drawer" className="flex flex-col justify-between h-full p-m">
        <header className="flex flex-col gap-m">
          <span className="font-semibold text-l">hilla-keycloak-sso</span>
          <SideNav onNavigate={({ path }) => navigate(path!)} location={location}>
            {createMenuItems().map(({ to, title, icon }) => (
              <SideNavItem path={to} key={to}>
                {icon ? <Icon src={icon} slot="prefix"></Icon> : <></>}
                {title}
              </SideNavItem>
            ))}
          </SideNav>
        </header>
        <footer className="flex flex-col gap-s">
          {state.user ? (
            <>
              <div className="flex items-center gap-s">
                <Avatar theme="xsmall"  name={state.user.username} />
                {state.user.username}
              </div>
              <LogoutButton />
            </>
          ) : (
            <Link to="/login">Sign in</Link>
          )}
        </footer>
      </div>

      <DrawerToggle slot="navbar" aria-label="Menu toggle"></DrawerToggle>
      <h1 slot="navbar" className="text-l m-0">
        {documentTitleSignal}
      </h1>

      <Suspense>
        <Outlet />
      </Suspense>
    </AppLayout>
  );
}

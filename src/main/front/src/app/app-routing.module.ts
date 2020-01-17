import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {RegistrationComponent} from "./registration/registration.component";
import {HomeComponent} from "./home/home.component";
import {LoginComponent} from "./login/login.component";
import {PanelComponent} from "./panel/panel.component";
import {AdminPanelComponent} from "./admin-panel/admin-panel.component";
import {MagazineComponent} from "./magazine/magazine.component";
import {MagazineUsersComponent} from "./magazine-users/magazine-users.component";
import {PanelMagazinesComponent} from "./panel-magazines/panel-magazines.component";
import {AuthGuard} from "./security/auth.guard";
import {LoginGuard} from "./security/login.guard";
import {AdminPanelMagazineComponent} from "./admin-panel-magazine/admin-panel-magazine.component";


const routes: Routes = [
  {path: '', redirectTo: '/home', pathMatch: 'full'},
  {path: 'home', component: HomeComponent},
  {
    path: 'signup',
    component: RegistrationComponent,
    canActivate: [LoginGuard],
  },
  {
    path: 'login',
    component: LoginComponent,
    canActivate: [LoginGuard],
  },

  {
    path: 'panel', component: PanelComponent,
    children: [
      {path: '', redirectTo: 'adminpanel', pathMatch: 'full'},
      {
        path: 'adminpanel',
        component: AdminPanelComponent,
        canActivate: [AuthGuard],
        data: {roles: ["camunda-admin"]}
      },
      {
        path: 'magazine',
        component: MagazineComponent,
        canActivate: [AuthGuard],
        data: {roles: ["camunda-admin", "editor"]}
      },
      {
        path: 'magazineTasks',
        component: PanelMagazinesComponent,
        canActivate: [AuthGuard],
        data: {roles: ["camunda-admin", "editor", "reviewer"]}

      },
      {
        path: 'magazineUsers',
        component: MagazineUsersComponent,
        canActivate: [AuthGuard],
        data: {roles: ["camunda-admin", "editor"]}
      },
    ]
  },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}

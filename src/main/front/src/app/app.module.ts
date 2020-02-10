import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {RegistrationComponent} from './registration/registration.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {FieldAddComponent} from './field-add/field-add.component';
import {NavbarComponent} from './navbar/navbar.component';
import {HomeComponent} from './home/home.component';
import {LoginComponent} from './login/login.component';
import {Authinterceptor} from "./security/authinterceptor.interceptor";
import {PanelComponent} from './panel/panel.component';
import {AdminPanelComponent} from './admin-panel/admin-panel.component';
import {MagazineComponent} from './magazine/magazine.component';
import {MagazineUsersComponent} from './magazine-users/magazine-users.component';
import {PanelMagazinesComponent} from './panel-magazines/panel-magazines.component';
import {AdminPanelMagazineComponent} from './admin-panel-magazine/admin-panel-magazine.component';
import {RouterStateSnapshot} from "@angular/router";
import { IssueComponent } from './issue/issue.component';
import { IssueDetailsComponent } from './issue-details/issue-details.component';

@NgModule({
  declarations: [
    AppComponent,
    RegistrationComponent,
    FieldAddComponent,
    NavbarComponent,
    HomeComponent,
    LoginComponent,
    PanelComponent,
    AdminPanelComponent,
    MagazineComponent,
    MagazineUsersComponent,
    PanelMagazinesComponent,
    AdminPanelMagazineComponent,
    IssueComponent,
    IssueDetailsComponent,

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    FormsModule

  ],
  providers: [

    {
      provide: HTTP_INTERCEPTORS,
      useClass: Authinterceptor,
      multi: true
    }

  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

import {Component, OnInit} from '@angular/core';
import {FormGroup} from "@angular/forms";
import {AuthService} from "../security/auth.service";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  navBarForm: FormGroup;

  buttonName = 'Sign up';
  loginDiv = true;
  signupDiv = false;

  isAdmin = false;
  isEditor = false;
  isReviewer = false;


  isLoggedIn = false;

  roles: string[] = [];
  adminRoles: string[] = ['camunda-admin'];

  name = 'Log in';

  username = '';

  constructor(private authService: AuthService) {
  }

  ngOnInit() {

    if (this.authService.isUserLoggedIn()) {
      this.isLoggedIn = true;
      this.username = this.authService.getLoggedInUserName();

      let groups = this.authService.getLoggedInGroups();

      if (groups) {
        if (groups.includes("camunda-admin")) {
          this.isAdmin = true
        }

        if (groups.includes("editor"))
          this.isEditor = true;

        if (groups.includes("reviewer"))
          this.isReviewer = true;
      }

      // this.authService.getGroups().subscribe(data=>{
      //     this.roles = data;
      //     console.log(data);
      //
      //   for (let role of this.adminRoles){
      //     if (this.roles.includes(role)){
      //       console.log(role);
      //       this.isAdmin = true;
      //     }
      //   };
      // });
    } else {
      this.isLoggedIn = false;
      this.username = '';
    }


  }

  logout() {
    this.authService.logout()
    window.location.href = "home";
  }
}

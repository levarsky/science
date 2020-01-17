import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {AuthService} from "../security/auth.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {


  isLoginFailed = false;
  errorMessage = '';
  roles: string[] = [];
  loginForm: FormGroup;

  constructor(private route: Router, private formBuilder: FormBuilder, private authService: AuthService) {
  }

  ngOnInit() {

    this.loginForm = this.formBuilder.group({
      username: ['', [Validators.required]],
      password: ['', [Validators.required]]
    });


  }


  onSubmit() {

    this.authService.checkUser(this.loginForm.get('username').value).subscribe(data => {

      this.authService.authenticationService(this.loginForm.get('username').value, this.loginForm.get('password').value).subscribe(data => {
        console.log(data);

      });

    }, error => {
      this.isLoginFailed = true;
      this.errorMessage = error.error.message;
    });

  }
}

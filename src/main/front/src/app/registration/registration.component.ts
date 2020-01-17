import {Component, OnInit} from '@angular/core';
import {FormArray, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {ConfirmPasswordValidator} from "../validator/confirm-pass.validator";
import {RegistrationService} from "../service/registration.service";
import {ProcessService} from "../service/process.service";
import {User} from "../model";
import {AuthService} from "../security/auth.service";

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  signUpForm: FormGroup;
  fieldIsVisible: boolean = false;
  signUpIsVisible = true;

  user: User;

  processId: string;
  isSignedUp = false;
  failed = false;
  errorMessage = '';

  message = '';

  constructor(private route: Router, private formBuilder: FormBuilder,
              private registrationService: RegistrationService,
              private processService: ProcessService,
              private authService: AuthService) {
  }

  ngOnInit() {


    this.signUpForm = this.formBuilder.group({

      firstName: ['', [Validators.required, Validators.pattern('[a-zA-Z ]*'), Validators.maxLength(30)]],
      lastName: ['', [Validators.required, Validators.pattern('[a-zA-Z ]*'), Validators.maxLength(30)]],
      state: ['', [Validators.required, Validators.pattern('[a-zA-Z ]*'), Validators.maxLength(30)]],
      city: ['', [Validators.required, Validators.pattern('[a-zA-Z ]*'), Validators.maxLength(30)]],
      title: [''],
      reviewer: [false],
      username: ['', [Validators.required, Validators.pattern('[a-zA-Z0-9_ ]*'), Validators.maxLength(15)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.pattern('[a-zA-Z0-9_!?*#/]*'), Validators.minLength(8), Validators.maxLength(30)]],
      passwordConfirm: ['', [Validators.required, Validators.pattern('[a-zA-Z0-9_!?*#/]*'), Validators.minLength(8), Validators.maxLength(30)]]
    }, {
      validator: ConfirmPasswordValidator.validate.bind(this)
    });


    this.processService.process().subscribe(data => {
      console.log(data);
      this.processId = data.processId;
    });


  }


  onSubmit() {
    console.log(this.signUpForm.value);

    this.fieldIsVisible = true;

    this.signUpIsVisible = false;

    this.user = this.signUpForm.value;

  }


  objectToReceive($event) {

    this.user.fields = $event;

    console.log(this.user);

    this.registrationService.signUp(this.processId, this.user).subscribe(data => {
      console.log(data);

      this.message = 'Successful,Check your email!';
      this.fieldIsVisible = false;

      setTimeout(() => {
        this.message = '';

        window.location.href = 'home';
      }, 4000);


    }, error => {

      this.failed = true;

      this.fieldIsVisible = false;

      this.signUpIsVisible = true;

      this.errorMessage = error.error.message;

    });


  }

  backButton($event: boolean) {
    this.fieldIsVisible = false;

    this.signUpIsVisible = true;
  }
}

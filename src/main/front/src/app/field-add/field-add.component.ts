import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {FormArray, FormBuilder, FormGroup} from "@angular/forms";
import {Router} from "@angular/router";
import {MagazineService} from "../service/magazine.service";
import {RegistrationService} from "../service/registration.service";

@Component({
  selector: 'app-field-add',
  templateUrl: './field-add.component.html',
  styleUrls: ['./field-add.component.css']
})
export class FieldAddComponent implements OnInit {

  @Output() objectToEmmit = new EventEmitter<object>();
  @Output() backButton = new EventEmitter<boolean>();

  fieldGroup: FormGroup;

  allFields;

  constructor(private route: Router,
              private formBuilder: FormBuilder,
              private magazineService: MagazineService,
              private registrationService: RegistrationService) {
  }

  ngOnInit() {

    this.fieldGroup = this.formBuilder.group({
      fields: [[]]
    });

    this.registrationService.getAllFields().subscribe(data => {
      this.allFields = data;
    });

  }


  onSubmitField() {

    console.log(this.fieldGroup.get('fields').value);

    this.objectToEmmit.emit(this.fieldGroup.get('fields').value);

  }

  back() {
    this.backButton.emit(true);
  }
}

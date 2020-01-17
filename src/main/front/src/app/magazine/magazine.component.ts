import {Component, Input, OnInit} from '@angular/core';
import {MagazineService} from "../service/magazine.service";
import {FormArray, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {PaymentTypeService} from "../service/payment-type.service";
import {ConfirmPasswordValidator} from "../validator/confirm-pass.validator";
import {Magazine} from "../model";

@Component({
  selector: 'app-magazine',
  templateUrl: './magazine.component.html',
  styleUrls: ['./magazine.component.css']
})
export class MagazineComponent implements OnInit {

  @Input() magazine;
  @Input() processId;

  magazineGroup: FormGroup;
  paymentType: any;

  magTitle = '';

  allFields;

  fields = [];

  failed = false;
  errorMessage = '';

  constructor(private magazineService: MagazineService,
              private paymentService: PaymentTypeService,
              private formBuilder: FormBuilder) {
  }

  ngOnInit() {

    console.log("Poslao " + this.processId);

    if (!this.processId) {
      this.magazineService.getMagazineProcess().subscribe(data => {
        this.processId = data.processId;

      });
      this.magTitle = 'Add magazine';
    }

    this.magazineGroup = this.formBuilder.group({

      issn: ['', [Validators.required]],
      name: ['', [Validators.required]],
      paymentTypes: [[], [Validators.required]],
      //fields: this.formBuilder.array([this.formBuilder.group({name:''})])
      fields: [[], [Validators.required]],
    });

    this.paymentService.getAllPaymentTypes().subscribe(data => {
      this.paymentType = data;

      if (this.magazine) {

        let pt = [];

        for (let payment of this.magazine.paymentTypes) {
          for (let py of this.paymentType) {
            if (JSON.stringify(py).includes((JSON.stringify(payment)))) {
              pt.push(py);
            }
          }
        }

        this.magazineGroup.get('paymentTypes').setValue(pt);
      }

    });


    this.magazineService.getAllFields().subscribe(data => {
      this.allFields = data;

      if (this.magazine) {
        this.magazineService.getFields(this.magazine.issn).subscribe(data => {
          this.fields = data;

          let fi = [];


          for (let field of this.fields) {
            for (let fl of this.allFields) {
              if (JSON.stringify(fl).includes((JSON.stringify(field)))) {
                fi.push(fl);
              }
            }
          }
          this.magazineGroup.get('fields').setValue(fi);

        });

      }

    });


    if (this.magazine) {

      this.magTitle = this.magazine.name;

      this.magazineGroup.get('issn').setValue(this.magazine.issn);
      this.magazineGroup.get('name').setValue(this.magazine.name);

    }


  }

  // get fields() {
  //   return this.magazineGroup.get('fields') as FormArray;
  // }
  //
  // addField() {
  //   this.fields.push(this.formBuilder.group({name:''}));
  // }
  //
  // deleteField(index) {
  //   this.fields.removeAt(index);
  // }


  onSubmit() {

    var mag: Magazine = this.magazineGroup.value;
    if (this.magazine) {
      mag.id = this.magazine.id
    }

    console.log(mag);

    this.magazineService.submitMagazine(this.processId, mag).subscribe(data => {
      console.log(data);

      window.location.href = "/panel/magazineUsers?processId=" + this.processId + "&issn=" + mag.issn;

    }, error => {
      this.failed = true;
      this.errorMessage = error.error.message;

      setTimeout(() => {
        this.failed = false;
        this.errorMessage = '';

      }, 4000);

    });


  }
}

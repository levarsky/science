import {Component, OnInit} from '@angular/core';
import {MagazineService} from "../service/magazine.service";
import {UserService} from "../service/user.service";

@Component({
  selector: 'app-admin-panel-magazine',
  templateUrl: './admin-panel-magazine.component.html',
  styleUrls: ['./admin-panel-magazine.component.css']
})
export class AdminPanelMagazineComponent implements OnInit {

  tasks;
  userListDTO;
  fields = [];

  editors = [];
  reviewers = [];
  payments: any;
  magazineName = '';


  constructor(private magazineService: MagazineService,
              private userService: UserService
  ) {
  }

  ngOnInit() {

    this.initTable();


  }

  initTable() {
    this.magazineService.getMagazinesForApprove().subscribe(data => {
      console.log(data);
      this.tasks = data;

    });
  }

  submitApproval(task, pass) {

    var magazine = task.variable.magazine;
    magazine.active = pass;

    console.log(magazine);

    this.magazineService.approveMagazine(task.id, magazine).subscribe(data => {
      this.initTable();
    });

  }

  viewDetails(magazine) {

    this.magazineName = magazine.name;
    console.log(magazine.issn);

    this.magazineService.getERMagazine(magazine.issn).subscribe(data => {
      this.userListDTO = data;
      this.editors = data.editors;
      this.reviewers = data.reviewers;
    });

    this.magazineService.getFields(magazine.issn).subscribe(data => {
      this.fields = data;
    });

    this.magazineService.getPaymentType(magazine.issn).subscribe(data => {
      this.payments = data;
    });

    this.userService.getUser().subscribe(data => {
      console.log(data);
    });


  }

}

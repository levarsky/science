import {Component, OnInit} from '@angular/core';
import {MagazineService} from "../service/magazine.service";
import {UserService} from "../service/user.service";
import {AuthService} from "../security/auth.service";

declare var $: any;

@Component({
  selector: 'app-panel-magazines',
  templateUrl: './panel-magazines.component.html',
  styleUrls: ['./panel-magazines.component.css']
})
export class PanelMagazinesComponent implements OnInit {

  tasks;
  userListDTO;
  fields = [];

  title = '';

  editors = [];
  reviewers = [];
  mainEditor = '';
  payments: any;
  magazineName = '';

  isAdmin = false;
  isEditor = false;

  groups = [];
  view = false;
  edit = false;

  magazine: any;
  processId: any;

  showAll = false;

  constructor(private magazineService: MagazineService,
              private userService: UserService,
              private authService: AuthService,
  ) {
  }

  ngOnInit() {

    this.authService.getGroups().subscribe(data => {

      this.showAll = false;

      this.groups = data;

      if (this.groups.includes("editor")) {
        this.initTable();
        this.isEditor = true;
        this.isAdmin = false;
        this.title = 'Check and resubmit magazine';
      } else if (this.groups.includes("camunda_admin")) {
        this.initAdminTable();
        this.isEditor = false;
        this.isAdmin = true;
        this.title = 'Approve magazine';
      } else {
        this.initAll();
      }

    });

  }

  initTable() {

    this.magazineService.getUserMagazinesTasks().subscribe(data => {
      console.log(data);
      this.tasks = data;
    });

  }

  submitApproval(task, pass) {


    var magazine = task.variable.magazine;
    magazine.isActive = pass;
    magazine.active = pass;

    console.log(magazine);

    this.magazineService.approveMagazine(task.id, magazine).subscribe(data => {
      this.ngOnInit();
    });

  }

  initAll() {

    this.showAll = true
    this.magazineService.getAllUserMagazines().subscribe(data => {
      this.title = 'All magazines';
      this.tasks = data;
    });

  }

  viewDetails(magazine) {

    this.view = true;
    this.edit = false;

    this.magazineName = magazine.name;
    console.log(magazine.issn);

    this.magazineService.getERMagazine(magazine.issn).subscribe(data => {
      this.userListDTO = data;
      this.editors = data.editors;
      this.reviewers = data.reviewers;
      this.mainEditor = data.mainEditor;
      console.log(data);
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


  initAdminTable() {

    this.magazineService.getMagazinesForApprove().subscribe(data => {
      console.log(data);
      this.tasks = data;

    });
  }

  editMagazine(task) {


    if (task.name == "Editor reviewer") {
      window.location.href = "/panel/magazineUsers?processId=" + task.variable.processId + "&issn=" + task.variable.magazine.issn;
    }

    this.view = false;
    this.edit = true;

    this.processId = task.variable.processId

    this.magazine = task.variable.magazine;


  }

  onChange(event) {
    console.log(event.target.value);

    if (event.target.value == 'all') {
      this.initAll()
    } else {
      this.ngOnInit()
    }

  }
}


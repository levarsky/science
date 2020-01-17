import {Component, OnInit} from '@angular/core';
import {UserService} from "../service/user.service";
import {ProcessService} from "../service/process.service";

@Component({
  selector: 'app-admin-panel',
  templateUrl: './admin-panel.component.html',
  styleUrls: ['./admin-panel.component.css']
})
export class AdminPanelComponent implements OnInit {
  tasks: any;
  title = '';

  constructor(private userService: UserService) {
  }

  ngOnInit() {

    this.userService.getRevRequest().subscribe(data => {
      this.tasks = data;
      console.log(data);
      this.title = 'Approve user';
    });


  }

  enable(id: any) {

    this.userService.approveReviewer(id, true).subscribe(data => {
      console.log(data);
      this.ngOnInit();
    });

  }

  disable(id: any) {
    this.userService.approveReviewer(id, false).subscribe(data => {
      console.log(data);
      this.ngOnInit();
    });
  }
}

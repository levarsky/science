import {Component, OnInit} from '@angular/core';
import {AuthService} from "../security/auth.service";

@Component({
  selector: 'app-panel',
  templateUrl: './panel.component.html',
  styleUrls: ['./panel.component.css']
})
export class PanelComponent implements OnInit {

  isAdmin = false;
  isEditor = false;
  isReviewer = false;


  constructor(private authService: AuthService) {
  }

  ngOnInit() {

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

  }

}

import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {MagazineService} from "../service/magazine.service";
import {ProcessService} from "../service/process.service";

@Component({
  selector: 'app-issue-details',
  templateUrl: './issue-details.component.html',
  styleUrls: ['./issue-details.component.css']
})
export class IssueDetailsComponent implements OnInit {

  processId;
  private formFieldsDto = null;
  private formFields = [];
  private processInstance;
  private enumValues = [];

  constructor(private route: ActivatedRoute,
              private magazineService: MagazineService,
              private processService:ProcessService) { }

  ngOnInit() {

    this.route.queryParams.subscribe(data => {

      this.processId = data.processId;

      this.magazineService.getIssueDetailsForm(this.processId).subscribe(res=>{

        this.formFieldsDto = res;
        this.formFields = res.formFields;
        this.processInstance = res.processInstanceId;


        this.formFields.forEach( (field) =>{

          if( field.type.name=='enum'){
            this.enumValues = Object.keys(field.type.values);
          }
        });

      })



    })

  }

}

import { Component, OnInit } from '@angular/core';
import {MagazineService} from "../service/magazine.service";
import {ProcessService} from "../service/process.service";

@Component({
  selector: 'app-issue',
  templateUrl: './issue.component.html',
  styleUrls: ['./issue.component.css']
})
export class IssueComponent implements OnInit {

  private formFieldsDto = null;
  private formFields = [];
  private processInstance;
  private enumValues = [];

  constructor(private magazineService: MagazineService,
              private processService:ProcessService) { }

  ngOnInit() {

    this.magazineService.getChooseMagazineProcess().subscribe(res => {
      console.log(res);

      this.formFieldsDto = res;
      this.formFields = res.formFields;
      this.processInstance = res.processInstanceId;


      this.formFields.forEach( (field) =>{

        if( field.type.name=='enum'){
          this.enumValues = Object.keys(field.type.values);
        }
      });


    })

  }

  onSubmitDynamic(f) {

    this.formFieldsDto.object = f.value;

    console.log(f.value);


    this.processService.submitTask(f.value,this.formFieldsDto.taskId,"magazineChoice").subscribe(data=>{
      console.log(data);

      window.location.href = "/panel/issueDetails?processId=" + this.processInstance;
    })



  }


}

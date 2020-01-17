import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MagazineService} from "../service/magazine.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-magazine-users',
  templateUrl: './magazine-users.component.html',
  styleUrls: ['./magazine-users.component.css']
})
export class MagazineUsersComponent implements OnInit {

  issn: string;
  processId;
  userGroup: FormGroup;
  reviewers = [];
  editors = [];
  failed = false;
  errorMessage = '';
  message = '';

  constructor(private magazineService: MagazineService,
              private formBuilder: FormBuilder,
              private route: ActivatedRoute) {
  }

  ngOnInit() {

    this.route.queryParams.subscribe(data => {
      this.processId = data.processId;
      this.issn = data.issn;
      console.log(data);

      this.userGroup = this.formBuilder.group({
        editors: [[], [Validators.required]],
        reviewers: [[], [Validators.required]],
      });

      this.magazineService.getMagazineUsers(this.issn).subscribe(data => {
        console.log(data);
        this.reviewers = data.reviewers;
        this.editors = data.editors;

        this.magazineService.getAddedUsers(this.issn).subscribe(data => {

          let ed = [];
          let re = [];

          for (let editor of data.editors) {
            for (let edit of this.editors) {
              if (JSON.stringify(edit).includes((JSON.stringify(editor)))) {
                ed.push(edit);
              }
            }
          }

          for (let reviewer of data.reviewers) {
            for (let rev of this.reviewers) {
              if (JSON.stringify(rev).includes((JSON.stringify(reviewer)))) {
                re.push(rev);
              }
            }
          }

          console.log(re);

          this.userGroup.get('editors').setValue(ed);
          this.userGroup.get('reviewers').setValue(re);

        });

      });

    });


  }

  onSubmit() {
    console.log(this.userGroup.value);

    this.magazineService.submitUserMagazine(this.processId, this.userGroup.value).subscribe(data => {
      console.log(data);

      this.message = 'Successful!';
      setTimeout(() => {
        this.message = '';
        window.location.href = 'panel/magazineTasks';

      }, 4000);

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

import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Router} from "@angular/router";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class MagazineService {

  private basicPath = 'science/magazine';

  constructor(private http: HttpClient) {
  }

  getMagazineProcess(): Observable<any> {
    return this.http.get(this.basicPath + "/magazineProcess");
  }

  getChooseMagazineProcess(): Observable<any> {
    return this.http.get(this.basicPath + "/chooseMagazineProcess");
  }

  getIssueDetailsForm(processId): Observable<any> {
    let param = new HttpParams();
    param = param.append("processId", processId);
    return this.http.get(this.basicPath + "/issueDetailsForm",{params:param});
  }

  getMagazineUsers(issn): Observable<any> {
    let param = new HttpParams();
    param = param.append("issn", issn);
    return this.http.get(this.basicPath + "/getMagazineUsers", {params: param});
  }

  getFields(issn): Observable<any> {
    let param = new HttpParams();
    param = param.append("issn", issn);
    return this.http.get(this.basicPath + "/getFields", {params: param});
  }

  getPaymentType(issn): Observable<any> {
    let param = new HttpParams();
    param = param.append("issn", issn);
    return this.http.get(this.basicPath + "/getPaymentType", {params: param});
  }

  getERMagazine(issn): Observable<any> {
    let param = new HttpParams();
    param = param.append("issn", issn);
    return this.http.get(this.basicPath + "/getERMagazine", {params: param});
  }

  getMagazinesForApprove(): Observable<any> {
    return this.http.get(this.basicPath + "/getMagazines");
  }

  submitUserMagazine(processId, userListDTO): Observable<any> {

    let param = new HttpParams();
    param = param.append('processId', processId);

    return this.http.post(this.basicPath + "/submitUserMagazine", userListDTO, {params: param});
  }

  submitMagazine(processId, magazine): Observable<any> {

    let param = new HttpParams();
    param = param.append('processId', processId);

    return this.http.post(this.basicPath + "/submitMagazine", magazine, {params: param});
  }

  approveMagazine(taskId, approveMagazine): Observable<any> {

    let param = new HttpParams();
    param = param.append('taskId', taskId);

    return this.http.put(this.basicPath + "/approveMagazine", approveMagazine, {params: param});
  }

  getUserMagazinesTasks(): Observable<any> {
    return this.http.get(this.basicPath + "/getUserMagazinesTasks");
  }

  getAllUserMagazines(): Observable<any> {
    return this.http.get(this.basicPath + "/getAllUserMagazines");
  }

  getAllFields(): Observable<any> {
    return this.http.get(this.basicPath + "/getAllFields")
  }


  getAddedUsers(issn): Observable<any> {
    let param = new HttpParams();
    param = param.append("issn", issn);
    return this.http.get(this.basicPath + "/getAddedUsers", {params: param});
  }

}

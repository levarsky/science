import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Router} from "@angular/router";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private basicPath = 'science/user';

  constructor(private http: HttpClient) {
  }

  approveReviewer(taskId, approve): Observable<any> {

    let param = new HttpParams();
    param = param.append("taskId", taskId);
    param = param.append("approve", approve);

    return this.http.get(this.basicPath + "/confirmReviewer", {params: param});
  }

  getRevRequest(): Observable<any> {
    return this.http.get(this.basicPath + "/getRevRequest");
  }

  getUser(): Observable<any> {
    return this.http.get(this.basicPath + "/getUser");
  }


}

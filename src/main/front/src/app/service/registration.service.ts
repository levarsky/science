import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Router} from "@angular/router";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {

  private basicPath = 'science/auth';

  constructor(private http: HttpClient, private router: Router) {
  }

  signUp(processId, user): Observable<any> {

    let param = new HttpParams();
    param = param.append('processId', processId);

    return this.http.post(this.basicPath + "/signUp", user, {params: param});
  }

  getAllFields(): Observable<any> {
    return this.http.get(this.basicPath + "/getAllFields")
  }


}

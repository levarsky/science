import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Router} from "@angular/router";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ProcessService {

  private basicPath = 'science/';

  constructor(private http: HttpClient, private router: Router) {
  }

  process(): Observable<any> {
    return this.http.get(this.basicPath + "auth/regProcess");
  }


}

import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from "rxjs";
import {HttpClient, HttpParams} from "@angular/common/http";
import {map} from "rxjs/operators";
import {User} from "../model";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  USER_NAME_SESSION_ATTRIBUTE_NAME = 'authenticatedUser'
  private currentUserSubject: BehaviorSubject<User>;
  public currentUser: Observable<User>;


  constructor(private http: HttpClient) {
    this.currentUserSubject = new BehaviorSubject<User>(JSON.parse(localStorage.getItem('currentUser')));
  }

  checkUser(username): Observable<any> {

    let param = new HttpParams();
    param = param.append("username", username);


    return this.http.get(`science/auth/check`, {params: param});
  }

  authenticationService(username, password) {


    return this.http.get(`science/user/auth`,
      {headers: {authorization: this.createBasicAuthToken(username, password)}}).pipe(map((res) => {
      const user = new User(null, null, null, username, null, password, null, null, false);
      this.currentUserSubject.next(user);
      this.registerSuccessfulLogin(username, password);
      localStorage.setItem('currentUser', JSON.stringify(user));
      this.getGroups().subscribe(data => {
        console.log(data);
        sessionStorage.setItem('authenticatedGroups', JSON.stringify(data));
        window.location.href = "home";
      });

    }));
  }

  getGroups(): Observable<any> {
    return this.http.get(`science/user/getGroup`);
  };

  public get currentUserValue(): User {
    return this.currentUserSubject.value;
  }

  createBasicAuthToken(username: String, password: String) {
    return 'Basic ' + window.btoa(username + ":" + password)
  }

  registerSuccessfulLogin(username, password) {
    sessionStorage.setItem(this.USER_NAME_SESSION_ATTRIBUTE_NAME, username)

  }

  logout() {
    sessionStorage.removeItem(this.USER_NAME_SESSION_ATTRIBUTE_NAME);
    sessionStorage.removeItem('authenticatedGroups');
    this.currentUserSubject.next(null);
  }

  isUserLoggedIn() {
    let user = sessionStorage.getItem(this.USER_NAME_SESSION_ATTRIBUTE_NAME)
    if (user === null) return false
    return true
  }

  getLoggedInGroups() {
    let groups = JSON.parse(sessionStorage.getItem('authenticatedGroups'));
    return groups;
  }

  getLoggedInUserName() {
    let user = sessionStorage.getItem(this.USER_NAME_SESSION_ATTRIBUTE_NAME)
    if (user === null) return ''
    return user
  }

}

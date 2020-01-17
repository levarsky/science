import {Injectable} from '@angular/core';
import {
  CanActivate,
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
  UrlTree,
  ActivatedRoute,
  Router
} from '@angular/router';
import {Observable} from 'rxjs';
import {AuthService} from "./auth.service";

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private route: ActivatedRoute, private router: Router, private authService: AuthService) {
  }

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

    if (this.authService.isUserLoggedIn()) {
      let data = this.authService.getLoggedInGroups();
      console.log("AUTH");
      console.log(data);
      const authorities = data;
      for (let role of next.data.roles) {
        if (authorities.includes(role)) {
          console.log(role);
          return true;
        }
      }
      window.location.href = 'home';
      return false;
    }

    window.location.href = 'login';
    return false;
  }


}

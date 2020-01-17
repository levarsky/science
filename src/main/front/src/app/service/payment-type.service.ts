import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class PaymentTypeService {

  private basicPath = 'science/payment';

  constructor(private http: HttpClient) {
  }

  getAllPaymentTypes(): Observable<any> {
    return this.http.get(this.basicPath + "/all");
  }
}

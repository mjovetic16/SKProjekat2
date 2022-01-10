import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';


@Injectable({
  providedIn: 'root'
})
export class HomeService {

  private readonly userUrl = "http://localhost:8083/users/user";
  private readonly reservationUrl = "http://localhost:8083/main/reservation";


  constructor(private http: HttpClient) { }

  loadUser(){

    return JSON.parse(localStorage.getItem("user"))

  }

 

  loadPage(filter){

    let headers = new HttpHeaders();
    headers = headers.set('Authorization', "Bearer "+localStorage.getItem('jwt'));

  

    return this.http.get(this.reservationUrl+"/termin",{headers:headers});

  }
}

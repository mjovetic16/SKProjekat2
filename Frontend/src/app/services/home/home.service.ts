import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { LoginService } from 'src/app/services/login/login.service';



@Injectable({
  providedIn: 'root'
})
export class HomeService {

  private readonly userUrl = "http://localhost:8083/users/user";
  private readonly reservationUrl = "http://localhost:8083/main/reservation";


  constructor(private http: HttpClient, private loginService: LoginService) { }

  loadUser(){
    let headers = new HttpHeaders();
    headers = headers.set('Authorization', "Bearer "+localStorage.getItem('jwt'));


    let user =  JSON.parse(localStorage.getItem("user"))

    this.loginService.getUser(user.id).subscribe((response)=>{
      localStorage.setItem("user", JSON.stringify(response));
      
    });

    user =  JSON.parse(localStorage.getItem("user"))

    return user;

  }

 

  loadPage(filter){

    let headers = new HttpHeaders();
    headers = headers.set('Authorization', "Bearer "+localStorage.getItem('jwt'));

  

    return this.http.get(this.reservationUrl+"/termin",{headers:headers});

  }

  reserve(termin){


    let headers = new HttpHeaders();
    headers = headers.set('Authorization', "Bearer "+localStorage.getItem('jwt'));

    return this.http.post(this.reservationUrl,termin,{headers:headers});
  }

  loadReservations(){


    let headers = new HttpHeaders();
    headers = headers.set('Authorization', "Bearer "+localStorage.getItem('jwt'));

    let user = JSON.parse(localStorage.getItem("user"));

    return this.http.get(this.reservationUrl+"/"+user["id"],{headers:headers});
  }

  cancel(reservation){

    let headers = new HttpHeaders();
    headers = headers.set('Authorization', "Bearer "+localStorage.getItem('jwt'));

    return this.http.post(this.reservationUrl+"/cancel",reservation,{headers:headers});
  }
}

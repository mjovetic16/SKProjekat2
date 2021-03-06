import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { LoginService } from 'src/app/services/login/login.service';



@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor(private http: HttpClient, private loginService: LoginService) { }


  private readonly userUrl = "http://localhost:8083/users/user";
  private readonly notificationUrl = "http://localhost:8083/notification/notification";
  private readonly reservationUrl = "http://localhost:8083/main/reservation";

  loadBlocklist(){
    let headers = new HttpHeaders();
    headers = headers.set('Authorization', "Bearer "+localStorage.getItem('jwt'));

    return this.http.get(this.userUrl+"/block/all",{headers:headers});

  }

  loadUsers(){
    let headers = new HttpHeaders();
    headers = headers.set('Authorization', "Bearer "+localStorage.getItem('jwt'));

    return this.http.get(this.userUrl+"",{headers:headers});
  }

  block(user){
    let headers = new HttpHeaders();
    headers = headers.set('Authorization', "Bearer "+localStorage.getItem('jwt'));

    return this.http.post(this.userUrl+"/block",user,{headers:headers});
  }

  unblock(user){
    let headers = new HttpHeaders();
    headers = headers.set('Authorization', "Bearer "+localStorage.getItem('jwt'));

    return this.http.post(this.userUrl+"/block/unblock",user,{headers:headers});
  }

  update(user){
    let headers = new HttpHeaders();
    headers = headers.set('Authorization', "Bearer "+localStorage.getItem('jwt'));

    return this.http.post(this.userUrl+"/update",user,{headers:headers});
  }

  loadRanks(){
    let headers = new HttpHeaders();
    headers = headers.set('Authorization', "Bearer "+localStorage.getItem('jwt'));

    return this.http.get(this.userUrl+"/rank/all",{headers:headers});
  }

  updateRank(rank){
    let headers = new HttpHeaders();
    headers = headers.set('Authorization', "Bearer "+localStorage.getItem('jwt'));

    return this.http.post(this.userUrl+"/rank",rank,{headers:headers});
  }

  loadHotels(){
    let headers = new HttpHeaders();
    headers = headers.set('Authorization', "Bearer "+localStorage.getItem('jwt'));

    return this.http.get(this.reservationUrl+"/hotel",{headers:headers});
  }

  updateHotel(hotel){
    let headers = new HttpHeaders();
    headers = headers.set('Authorization', "Bearer "+localStorage.getItem('jwt'));

    return this.http.post(this.reservationUrl+"/hotel/update",hotel,{headers:headers});
  }

  updateHotelAlloc(allocation){
    let headers = new HttpHeaders();
    headers = headers.set('Authorization', "Bearer "+localStorage.getItem('jwt'));

    return this.http.post(this.reservationUrl+"/hotel/allocation",allocation,{headers:headers});
  }

  loadNotifications(filter){
    let headers = new HttpHeaders();
    headers = headers.set('Authorization', "Bearer "+localStorage.getItem('jwt'));

    return this.http.post(this.notificationUrl,filter,{headers:headers});
  }

  loadNotificationTypes(){
    let headers = new HttpHeaders();
    headers = headers.set('Authorization', "Bearer "+localStorage.getItem('jwt'));

    return this.http.get(this.notificationUrl+"/types",{headers:headers});
  }

  updateType(type){
    let headers = new HttpHeaders();
    headers = headers.set('Authorization', "Bearer "+localStorage.getItem('jwt'));

    return this.http.post(this.notificationUrl+"/types",type,{headers:headers});
  }

  deleteType(type){
    let headers = new HttpHeaders();
    headers = headers.set('Authorization', "Bearer "+localStorage.getItem('jwt'));

    

    const options = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': "Bearer "+localStorage.getItem('jwt')
      }),
      body: type,
    };

    return this.http.delete(this.notificationUrl+"/types",options);
  }

}

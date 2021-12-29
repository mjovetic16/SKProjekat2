import { Injectable, OnDestroy } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Credentials } from '../../models/credentials.model';
import { map } from 'rxjs/operators'


@Injectable({
  providedIn: 'root'
})
export class LoginService implements OnDestroy {

  private readonly loginUrl = 'http://localhost:8080/login'
  private readonly userUrl = "http://localhost:8080/users"

  constructor(private http: HttpClient) { }

  ngOnDestroy(): void {
    this.logout()
  }

  login(credentials) {

    let user = {username: credentials.username, password:credentials.password};

    return this.http.post(this.loginUrl,user,{observe:'response'});
    
    
  }

  getUser(credentials){

    let headers = new HttpHeaders();
    headers = headers.set('Authorization', localStorage.getItem('jwt'));

     return this.http.get(this.userUrl+"/"+credentials.username,{headers:headers});
  }


  

  postExample(data){
    this.http.post(this.loginUrl, data)
  }

  logout(){
    localStorage.removeItem("jwt")
    localStorage.removeItem("user")
  }
}

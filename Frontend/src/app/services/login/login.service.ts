import { Injectable, OnDestroy } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Credentials } from '../../models/credentials.model';
import { map } from 'rxjs/operators'


@Injectable({
  providedIn: 'root'
})
export class LoginService implements OnDestroy {

  private readonly userUrl = "http://localhost:8083/users"

  constructor(private http: HttpClient) { }

  ngOnDestroy(): void {
    this.logout()
  }

  login(credentials) {

    let user = {email: credentials.email, password:credentials.password};

    return this.http.post(this.userUrl+"/user/login",user,{observe:'response'});
    
    
  }

  getUser(id){

    let headers = new HttpHeaders();
    headers = headers.set('Authorization', localStorage.getItem('jwt')); 

    return this.http.get(this.userUrl+"/user/"+id,{headers:headers});
  }


  logout(){
    localStorage.removeItem("jwt")
    localStorage.removeItem("user")
  }
}

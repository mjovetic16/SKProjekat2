import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HomeService } from 'src/app/services/home/home.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  user;
  types=[];
  




  constructor(private homeService: HomeService, private router: Router){ 
    this.user = {
      email:"email@gmail.com",
      password:"password",
      username : "username",
      firstName : "FirstName",
      lastName:"LastName",
      type:"ROLE_CLIENT",
      number:"063-123-456",
      birthdate:"1997-03-03",

      passport:"BrojPasosa",
      hotel:1,
      datum:"2022-03-03",
      numberOfReservations:0
  
    }
  }

  ngOnInit(): void {



  }

  onSubmit(){
    this.homeService.createUser(this.user).subscribe(responseData=>{
    
      
      console.log(responseData);
      this.router.navigate([''])
      

    },error => {
      console.log(error);
    });
  }

}

import { Component, OnInit } from '@angular/core';
import { HomeService } from 'src/app/services/home/home.service';
import { LoginService } from 'src/app/services/login/login.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  termini = [];
  reservations = [];
  filter;
  username;
  tableRows;
  tableRowsRes;


  constructor(private homeService: HomeService,private loginService: LoginService, private router: Router) { 

    this.filter = {origin:"",destination:"",returnDate:"",departDate:"",all:true,oneWay:true};

  }

  ngOnInit(): void {
    let user = this.homeService.loadUser();

    this.filter = {city:"",hotel:"",returnDate:"",departDate:"",all:true,oneWay:true};

    
    this.username = user.username;

    console.log(user);
    


    this.loadTermini();
    this.loadReservations();

  }

  onLogout():void{
    this.loginService.logout();
    this.router.navigate(['/']);
  }

  loadReservations(){
    this.homeService.loadReservations().subscribe(responseData=>{
      
      let list = responseData['content'];

      this.reservations = list;

    

      this.tableRowsRes = [];
      for (let index = 0; index < list.length; index++) {
        const element = list[index];

        this.tableRowsRes[element.id] = element;
        this.tableRowsRes[element.id].value = 1;
        
      }

      console.log("My reservations:");
      console.log(this.reservations);
      
      
      
    },error => {
      console.log(error);
    });

  }

  loadTermini(){
    this.homeService.loadPage(this.filter).subscribe(responseData=>{
      
      let list = responseData['content'];

      this.termini = list;

    

      this.tableRows = [];
      for (let index = 0; index < list.length; index++) {
        const element = list[index];

        this.tableRows[element.id] = element;
        this.tableRows[element.id].value = 1;
        
      }

      console.log(this.tableRows);
      
      
      
      
      
    },error => {
      console.log(error);
    });

  }

  onChangeCity(){
    this.loadTermini();
  }

  onChangeHotel(){
    this.loadTermini();
  }

  onClickReserve(termin){

    this.homeService.reserve(termin).subscribe(data=>{

      console.log("Reserve:");
      
      console.log(data);



      this.loadTermini();

      this.loadReservations();
      
    },error => {
      console.log(error);
      
    
    })

  }

  onClickCancel(reservation){
    this.homeService.cancel(reservation).subscribe(data=>{

      console.log("Reserve:");
      
      console.log(data);



      this.loadTermini();

      this.loadReservations();
      
    },error => {
      console.log(error);
      
    
    })
  }



}

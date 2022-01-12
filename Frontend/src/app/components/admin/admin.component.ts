import { Component, OnInit } from '@angular/core';

import { Router } from '@angular/router';
import { LoginService } from 'src/app/services/login/login.service';
import { AdminService } from 'src/app/services/admin/admin.service';
import { HomeService } from 'src/app/services/home/home.service';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

  blocklist;
  allUsers;
  user;
  ranks;
  newrank;
  hotels;
  notifications;
  notFilter;
  notTypes;
  filterDate;
  

  constructor(private router: Router, private homeService: HomeService, private adminService: AdminService) { 
    this.notFilter = {email:'',name:'',endDate:null,startDate:null};
    this.filterDate={returnDate:"",departDate:""};
    
  }

  ngOnInit(): void {
    this.user = this.homeService.loadUser();
    this.loadBlocklist();
    this.loadUsers();
    this.loadRanks();
    this.loadHotels();
    this.loadNotifications(this.notFilter);
    this.loadTypes();

    this.newrank={name:null,value:null,discount:null}
  }


  loadBlocklist(){
    this.adminService.loadBlocklist().subscribe(blocklist => {

      this.blocklist = blocklist["blockedUsers"];
      console.log(blocklist);
      


      
    },error => {
      console.log(error);
    });
  }

  loadUsers(){
    this.adminService.loadUsers().subscribe(users => {

      this.allUsers = users["content"];
      console.log("Users:");
      
      console.log(users);
      


      
    },error => {
      console.log(error);
    });
  }

  onClickBlock(user){
    this.adminService.block(user).subscribe(data => {

      console.log(data);
      this.loadBlocklist();
      this.loadUsers();

    },error => {
      console.log(error);
    });
  }

  onClickUnBlock(user){
    this.adminService.unblock(user).subscribe(data => {

      console.log(data);
      this.loadBlocklist();
      this.loadUsers();

    },error => {
      console.log(error);
    });
  }

  onUpdate(){
    this.adminService.update(this.user).subscribe(data => {

      console.log(data);
      this.loadBlocklist();
      this.loadUsers();
      this.loadMyUser();

    },error => {
      console.log(error);
    });
  }

  loadMyUser(){
    this.user = this.homeService.loadUser();
  }

  loadRanks(){
    this.adminService.loadRanks().subscribe(data => {

      console.log("ranks:");
      
      console.log(data);
      this.ranks = data['content'];

    },error => {
      console.log(error);
    });
  }

  onClickUpdateRank(rank){
    this.adminService.updateRank(rank).subscribe(data => {

      console.log(data);
      this.loadRanks();

    },error => {
      console.log(error);
    });

  }

  onClickAddRank(){
    this.onClickUpdateRank(this.newrank);
    this.newrank = {name:null,value:null,discount:null};
  }

  loadHotels(){
    this.adminService.loadHotels().subscribe(data => {

      console.log("hotels:");
      
      console.log(data);
      this.hotels = data['content'];

    },error => {
      console.log(error);
    });
  }

  onClickUpdateHotel(hotel){
    this.adminService.updateHotel(hotel).subscribe(data => {
      console.log("Hotel updated");
      console.log(data);
      
      this.loadHotels();

    },error => {
      console.log(error);
    });
  }

  loadNotifications(filter){
    this.adminService.loadNotifications(filter).subscribe(data => {

      console.log("notifications:");
      
      console.log(data);
      this.notifications = data['content'];

    },error => {
      console.log(error);
    });
  }

  loadTypes(){
    this.adminService.loadNotificationTypes().subscribe(data => {

      console.log("notification types:");
      
      console.log(data);
      this.notTypes = data['content'];

    },error => {
      console.log(error);
    });
  }

  onChangeFilter(){
    this.loadNotifications(this.notFilter);
  }
  onChangeBegin(){
    
    
    
    let date = new Date();

    date.setFullYear(this.filterDate.departDate.year, this.filterDate.departDate.month-1, this.filterDate.departDate.day);

    if(this.filterDate.departDate.month<10){
      this.filterDate.departDate.month = "0"+this.filterDate.departDate.month;
    }

    if(this.filterDate.departDate.day<10){
      this.filterDate.departDate.day = "0"+this.filterDate.departDate.day;
    }


    this.notFilter.startDate = this.filterDate.departDate.year+"-"+(this.filterDate.departDate.month)+"-"+this.filterDate.departDate.day;


    console.log(this.filterDate);
    
    console.log(this.notFilter);

    this.loadNotifications(this.notFilter);
    


    
  }

  onChangeEnd(){
    
    
    
    let date = new Date();

    date.setFullYear(this.filterDate.returnDate.year, this.filterDate.returnDate.month-1, this.filterDate.returnDate.day);


    if(this.filterDate.returnDate.month<10){
      this.filterDate.returnDate.month = "0"+this.filterDate.returnDate.month;
    }

    if(this.filterDate.returnDate.day<10){
      this.filterDate.returnDate.day = "0"+this.filterDate.returnDate.day;
    }


    this.notFilter.endDate = this.filterDate.returnDate.year+"-"+(this.filterDate.returnDate.month)+"-"+this.filterDate.returnDate.day;

    console.log(this.notFilter);


    this.loadNotifications(this.notFilter);
    
  }

 
  
}

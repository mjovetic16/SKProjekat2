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
  

  constructor(private router: Router, private homeService: HomeService, private adminService: AdminService) { }

  ngOnInit(): void {
    this.user = this.homeService.loadUser();
    this.loadBlocklist();
    this.loadUsers();

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

  
}

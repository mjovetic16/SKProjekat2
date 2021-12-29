import { Component, OnInit } from '@angular/core';
import { LoginService } from 'src/app/services/login/login.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent implements OnInit {

  public loginForm: FormGroup
  showAlert = false;
  alertContent='';
  
  constructor(private loginService: LoginService,
              private router: Router,
              private formBuilder: FormBuilder) {
                
    this.loginForm = this.formBuilder.group({
      // Odgovarajuce HTML elemente cemo povezati atributom formControlName="..."
      // ['default value', [validators]
      username: ['', Validators.required],
      password: ['', [Validators.required, Validators.minLength(4)]]
    })
  }

  ngOnInit(): void {
  }

  public get username(){
    return this.loginForm.get('username')
  }

  public get password(){
    return this.loginForm.get('password')
  }

  public submitForm(credentials){
    this.loginService.login(credentials).subscribe(resp => {
      let auth = resp.headers.get("Authorization");
      console.log(auth);
      

      localStorage.setItem("jwt", auth);
      
      this.loginService.getUser(credentials).subscribe(resp => {
        console.log(resp);
        localStorage.setItem("user", JSON.stringify(resp));
        console.log(localStorage.getItem("user"));
        this.router.navigate(['/home'])
        
      })
      
    },error => {
      console.log(error);
      
       this.showAlert=true;
       this.alertContent = "Username or password are incorrect"; 
      
    
    })
  }
}
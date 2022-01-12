import { Component, OnInit } from '@angular/core';
import { LoginService } from 'src/app/services/login/login.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import jwt_decode from 'jwt-decode';


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
      email: ['', Validators.required],
      password: ['', [Validators.required, Validators.minLength(4)]]
    })
  }

  ngOnInit(): void {
  }

  public get email(){
    return this.loginForm.get('email')
  }

  public get password(){
    return this.loginForm.get('password')
  }

  public submitForm(credentials){
    this.loginService.login(credentials).subscribe(resp => {
      let auth = resp.headers.get("Authorization");
      let token = resp.body["token"];

      console.log(token);
      

      localStorage.setItem("jwt", token);

      let parsedToken = jwt_decode(token);

      console.log(parsedToken);
      
      
      
      this.loginService.getUser(parsedToken["id"]).subscribe(resp => {
        console.log(resp);
        localStorage.setItem("user", JSON.stringify(resp));
        console.log(localStorage.getItem("user"));
        this.router.navigate(['/home'])
        
      })

      
      
      
    },error => {
      console.log(error);
      
       this.showAlert=true;
       this.alertContent = "Email or password are incorrect"; 
      
    
    })
  }

  onClickRegister(){
    this.router.navigate(['/register'])
  }

  onResetPassword(){
    let email = this.loginForm.get('email').value;
    let user = {email:email}

    console.log(email);
    
    this.loginService.resetPassword(user).subscribe(resp => {
      console.log("Password reset");
      console.log(resp);
      
    })
  }
}
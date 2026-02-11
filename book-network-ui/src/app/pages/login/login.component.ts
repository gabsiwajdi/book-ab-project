import { Component } from '@angular/core';
import {AuthenticationReques} from '../../services/models/authentication-reques';
import {FormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';
import {Router} from '@angular/router';
import {AuthenticationService} from '../../services/services/authentication.service';
import {TokenService} from '../../services/services/token/token.service';

@Component({
  selector: 'app-login',
  imports: [
    FormsModule,
    CommonModule
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})

export class LoginComponent {


  authRequest :AuthenticationReques = {email:'' , password:''};
  errorMsg :Array<string>= [];

  constructor(
    private router:Router,
    private authService:AuthenticationService,
    private tokenService : TokenService

  ){}



  async login() {
    this.errorMsg = [];

    try {
      const res = await this.authService.authenticate({
        body: this.authRequest
      });

       this.tokenService.token = res.token as string;
      this.router.navigate(['books']);

    } catch (err: any) {
      console.log(err);
      if (err.error?.validationError) {
        this.errorMsg = err.error.validationError;
      } else if (err.error?.error) { // Updated to check for 'error' field
        this.errorMsg.push(err.error.error);
      } else {
        this.errorMsg.push('Erreur serveur');
      }
    }
  }


  register(){
    this.router.navigate(['register'])
  }
}

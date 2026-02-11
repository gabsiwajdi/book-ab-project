import { Component } from '@angular/core';
import {RegistrationRequest} from '../../services/models/registration-request';
import {FormsModule} from '@angular/forms';
import {NgForOf, NgIf} from '@angular/common';
import {Router} from '@angular/router';
import {AuthenticationService} from '../../services/services/authentication.service';

@Component({
  selector: 'app-register',
  imports: [
    FormsModule,
    NgForOf,
    NgIf
  ],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {

  registerRequest :RegistrationRequest ={email:'',firstname:'',lastname:'',password:''}
  confirmPassword = '';
  errorMsg:Array<string> = [] ;
constructor(
  private router:Router,
  private authService :AuthenticationService
  ) {
}

  async register() {

  this.errorMsg = [];

    // ✅ Validation frontend : mot de passe et confirmation
    // if (this.registerRequest.password !== this.confirmPassword) {
    //   this.errorMsg.push('Passwords do not match');
    //   return; // Arrête ici, n'envoie pas la requête
    // }
    try {
      const res = await this.authService.register({
        body: this.registerRequest
      });
      this.router.navigate(['activate-account']);

    } catch (err: any) {

      console.log(err);
      if (err.error?.validationError) {
        this.errorMsg = err.error.validationError;
      } else if (err.error?.errorMsg) {
        this.errorMsg.push(err.error.errorMsg);
      } else {
        this.errorMsg.push('Erreur serveur');
      }
    }
  }

  login() {
    this.router.navigate(["login"])

  }
}

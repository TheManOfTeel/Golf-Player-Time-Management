import { Component } from '@angular/core';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: 'login.component.html',
  styleUrls: ['login.component.css']
})
export class LoginComponent {

  loginForm: FormGroup;
  errorMessage = '';

  constructor(
    public authService: AuthService,
    private router: Router,
    private fb: FormBuilder
  ) {
    this.createForm();
  }

  createForm() {
    this.loginForm = this.fb.group({
      email: ['', Validators.required], // Validators.email?
      password: ['', Validators.required]
    });
  }

  tryGoogleLogin() {
    this.authService.doGoogleLogin()
    .then(() => {
      this.router.navigate(['/dashboard']);
    });
  }

  tryLogin(value) {
    this.authService.doLogin(value)
    .then(() => {
      this.router.navigate(['/dashboard']);
    }, err => {
      console.log(err);
      this.errorMessage = 'Invalid username/password';
      this.errorMessage = err.message;
    });
  }

}

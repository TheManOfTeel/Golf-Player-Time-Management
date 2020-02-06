import { Component } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { Router, Params } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {

  registerForm: FormGroup;
  errorMessage = '';
  successMessage = '';

  constructor(
    public authService: AuthService,
    private router: Router,
    private fb: FormBuilder
  ) {
    this.createForm();
  }

  createForm() {
    this.registerForm = this.fb.group({
     email: ['', Validators.required],
     password: ['', Validators.required],
     golfCourse: ['', Validators.required]
     });
  }

  tryGoogleLogin() {
    this.authService.doGoogleLogin()
    .then( res => {
      this.router.navigate(['/dashboard']);
    }, err => console.log(err)
    );
  }

  tryRegister(value) {
    this.authService.doRegister(value)
    .then(res => {
      console.log(res);
      this.errorMessage = '';
      this.successMessage = 'Your account has been created';
    }, err => {
      console.log(err);
      this.errorMessage = err.message;
      this.successMessage = '';
    });
  }

}

import { Component } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RegisterComponent } from '../register/register.component';
import { MatDialog } from '@angular/material';
import * as firebase from 'firebase';

@Component({
  selector: 'app-login',
  templateUrl: 'login.component.html',
  styleUrls: ['login.component.css']
})
export class LoginComponent {

  loginForm: FormGroup;
  errorMessage = '';
  isAdmin: any;
  noAdmin = false;

  constructor(
    public authService: AuthService,
    private router: Router,
    private fb: FormBuilder,
    public dialog: MatDialog
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
      this.evalAdmin()
      .then(val => {
        this.isAdmin = val;
        this.checkAdmin(this.isAdmin);
      });
    }, err => {
      console.log(err);
      this.noAdmin = false;
      this.errorMessage = 'Invalid username/password';
      this.errorMessage = err.message;
    });
  }

  evalAdmin() {
    const userId = firebase.auth().currentUser.uid;
    return firebase.database().ref('/Users/' + userId).once('value').then(function(snapshot) {
      const isAdmin = (snapshot.val() && snapshot.val().isAdmin) || false;
      return isAdmin;
      });
  }

  checkAdmin(isAdmin) {
    if (this.isAdmin === true) {
      this.noAdmin = false;
      this.router.navigate(['/dashboard']);
      console.log('here');
    }
    if (this.isAdmin === false) {
      this.noAdmin = true;
    }
  }

  tryRegister(): void {
    this.dialog.open(RegisterComponent, {
      disableClose: true,
      width: '280px',
      height: '500px'
    });
  }

}

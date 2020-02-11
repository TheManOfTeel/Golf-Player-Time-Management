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
  noAdmin = '';

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
      })
      if (this.isAdmin == true) {
        this.router.navigate(['/dashboard']);
      }
      else {
        this.noAdmin = 'You are not an admin';
      }
    }, err => {
      console.log(err);
      this.errorMessage = 'Invalid username/password';
      this.errorMessage = err.message;
    });
  }

  evalAdmin() {
    var userId = firebase.auth().currentUser.uid;
    return firebase.database().ref('/Users/' + userId).once('value').then(function(snapshot) {
      var isAdmin = (snapshot.val() && snapshot.val().isAdmin) || false;
      return isAdmin;
      });
  }

  tryRegister(): void {
    this.dialog.open(RegisterComponent, {
      disableClose: true,
      width: '280px',
      height: '500px'
    });
  }

}

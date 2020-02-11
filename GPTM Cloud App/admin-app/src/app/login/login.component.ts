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
      this.router.navigate(['/dashboard']);
    }, err => {
      console.log(err);
      this.errorMessage = 'Invalid username/password';
      this.errorMessage = err.message;
    });
    //this.evalAdminStatus(this.evalAdmin());
  }

  evalAdmin() {
    var userId = firebase.auth().currentUser.uid;
    var ref = firebase.database().ref('/Admins/' + userId);

    ref.on("value", function(snapshot) {
      console.log(snapshot.val().isAdmin);
      const adminStatus = snapshot.val().isAdmin;
      return adminStatus;
    })
  }

  evalAdminStatus(adminStatus) {
    if (adminStatus = true) {
      this.router.navigate(['/dashboard']);
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

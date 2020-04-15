import { Component, OnInit } from '@angular/core';
import * as firebase from 'firebase/app';
import 'firebase/database';
import 'firebase/auth';
import { FormGroup, Validators, FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-support',
  templateUrl: './support.component.html',
  styleUrls: ['./support.component.css']
})
export class SupportComponent implements OnInit {

  emailForm: FormGroup;
  resetForm: FormGroup;
  email = '';
  email2 = '';
  emailChanged = false;
  errorMessage: string;
  noMatchEmail = '';
  isLoading = false;
  confirm = false;
  confirmP = false;
  errorMessageP: string;
  password = '';
  password2 = '';
  hide1 = true;
  hide2 = true;
  passwordChanged = false;
  noMatch = '';
  isLoadingP = false;

  constructor(private fb: FormBuilder) {
    this.createForm();
  }

  createForm() {
    this.emailForm = this.fb.group({
      email: ['', Validators.email],
      email2: ['', Validators.email]
    });
    this.resetForm = this.fb.group({
      password: ['', Validators.minLength(6)],
      password2: ['', Validators.minLength(6)]
    });
  }

  ngOnInit() {
  }

  // Open new window with endpoint set to Google Play Store
  appDownload() {
    window.open(
      'https://play.google.com/store/apps', '_blank');
  }

  // Open new window and use current user's email to open up a blank draft email to the support account
  mailTo() {
    window.open(
      'mailto:glfmngmnt@gmail.com', '_blank');
  }

  // Asks the user to make sure that they want to change their email and checks to make sure that it is actually valid
  confirmChange(confirm) {
    this.confirm = confirm;
    if (this.emailForm.valid) {
      this.confirm = confirm;
    }
    if (!this.email || !this.emailForm.valid) {
      this.confirm = false;
    }
  }

    // Asks the user to make sure that they want to change their password and checks to make sure that it is actually valid
    confirmChangePassword(confirm) {
      this.confirmP = confirm;
      if (this.resetForm.valid) {
        this.confirmP = confirm;
      }
      if (!this.password || !this.password2 || !this.resetForm.valid) {
        this.confirmP = false;
      }
    }

  /* tslint:disable:quotemark */
  // Change email address
  changeEmail() {
    this.emailChanged = false;
    this.isLoading = true;
    const user = firebase.auth().currentUser;
    if (this.email === this.email2) {
      user.updateEmail(this.email).then(() => {
        // Update successful
        const userId = firebase.auth().currentUser.uid;
        const userRef = firebase.database().ref('/Users/' + userId);
        userRef.update({
          email: this.email,
          verified: false
        });
        user.sendEmailVerification();
        this.noMatchEmail = null;
        this.confirm = false;
        this.errorMessage = null;
        this.emailChanged = true;
        this.isLoading = false;
      }, err => {
        // An error happened
        this.noMatchEmail = null;
        this.confirm = false;
        this.emailChanged = false;
        this.errorMessage = err.message;
        this.isLoading = false;
      });
    }
    if (this.email !== this.email2) {
      this.confirm = false;
      this.noMatchEmail = "Emails don't match";
      this.emailChanged = false;
      this.errorMessage = null;
      this.isLoading = false;
    }
  }

  // Change password
  changePassword() {
    this.passwordChanged = false;
    this.isLoadingP = true;
    const user = firebase.auth().currentUser;
    if (this.password === this.password2) {
      user.updatePassword(this.password).then(() => {
        // Update successful
        this.noMatch = null;
        this.passwordChanged = true;
        this.confirmP = false;
        this.errorMessageP = null;
        this.isLoadingP = false;
      }, err => {
          // An error happened
          this.noMatch = null;
          this.passwordChanged = false;
          this.confirmP = false;
          this.errorMessageP = err.message;
          this.isLoadingP = false;
      });
    }
    if (this.password !== this.password2) {
      this.confirmP = false;
      this.noMatch = "Passwords don't match";
      this.errorMessageP = null;
      this.isLoadingP = false;
    }
  }
  /* tslint:enable:quotemark */
}

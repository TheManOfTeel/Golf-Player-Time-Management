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
  email: '';
  emailChanged = false;
  errorMessage: string;
  isLoading = false;
  confirm = false;

  constructor(private fb: FormBuilder) {
    this.createForm();
  }

  createForm() {
    this.emailForm = this.fb.group({
     email: ['', Validators.email]
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

  // Change email address
  changeEmail() {
    this.isLoading = true;
    const user = firebase.auth().currentUser;
    user.updateEmail(this.email).then(() => {
      // Update successful.
      const userId = firebase.auth().currentUser.uid;
      const userRef = firebase.database().ref('/Users/' + userId);
      userRef.update({
        email: this.email,
        verified: false
      });
      user.sendEmailVerification();
      this.confirm = false;
      this.errorMessage = null;
      this.emailChanged = true;
      this.isLoading = false;
    }, err => {
      // An error happened.
      this.confirm = false;
      this.emailChanged = false;
      this.errorMessage = err.message;
      this.isLoading = false;
    });
  }

}

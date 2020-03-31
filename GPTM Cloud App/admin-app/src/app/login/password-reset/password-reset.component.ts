import { Component, OnInit } from '@angular/core';
import * as firebase from 'firebase/app';
import 'firebase/auth';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-password-reset',
  templateUrl: './password-reset.component.html',
  styleUrls: ['./password-reset.component.css']
})
export class PasswordResetComponent implements OnInit {

  email: '';
  resetForm: FormGroup;
  emailSent = false;
  errorMessage: string;
  isLoading = false;

  constructor(private fb: FormBuilder) {
    this.createForm();
   }

  createForm() {
    this.resetForm = this.fb.group({
     email: ['', Validators.email]
     });
  }

  ngOnInit(): void {
  }

  resetPassword() {
    if (this.email != null) {
      this.isLoading = true;
      firebase.auth().sendPasswordResetEmail(this.email)
    .then(() => {
      this.emailSent = true;
      this.errorMessage = null;
      this.isLoading = false;
    }, err => {
      this.emailSent = false;
      this.errorMessage = err.message;
      this.isLoading = false;
    });
    }
  }

}

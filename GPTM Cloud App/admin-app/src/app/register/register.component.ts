import { Component } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { Router, Params } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import * as firebase from 'firebase';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {

  registerForm: FormGroup;
  errorMessage = '';
  successMessage = '';
  golfCourse = '';
  userId: any;
  email: '';
  password: '';
  course: any;
  alreadyExists: string;

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
    this.checkIfExists(this.golfCourse)
    .then(data => {
      this.course = data;
    })
    if (this.course != null) {
      this.authService.doRegister(value)
      .then(res => {
        this.successMessage = 'Your account has been created';
        firebase.database().ref('/Users/' + res.user.uid).set({
          email: res.user.email,
          password: this.password,
          isAdmin: true,
          golfCourse: this.golfCourse
        });
      }, err => {
          console.log(err);
          this.errorMessage = err.message;
          this.successMessage = '';
        });
      this.writeHoleData(this.golfCourse);
    }
    if (this.course == null) {
      this.alreadyExists = 'Already Exists';
    }
  }

  writeHoleData(golfCourse) {
   firebase.database().ref('GolfCourse/' + this.golfCourse).set({
     golfCourse: this.golfCourse,
     hole_01_desc: '',
     hole_01_yds: '',
     hole_01_par: '',
     hole_02_desc: '',
     hole_02_yds: '',
     hole_02_par: '',
     hole_03_desc: '',
     hole_03_yds: '',
     hole_03_par: '',
     hole_04_desc: '',
     hole_04_yds: '',
     hole_04_par: '',
     hole_05_desc: '',
     hole_05_yds: '',
     hole_05_par: '',
     hole_06_desc: '',
     hole_06_yds: '',
     hole_06_par: '',
     hole_07_desc: '',
     hole_07_yds: '',
     hole_07_par: '',
     hole_08_desc: '',
     hole_08_yds: '',
     hole_08_par: '',
     hole_09_desc: '',
     hole_09_yds: '',
     hole_09_par: '',
     hole_10_des: '',
     hole_10_yds: '',
     hole_10_par: '',
     hole_11_desc: '',
     hole_11_yds: '',
     hole_11_par: '',
     hole_12_desc: '',
     hole_12_yds: '',
     hole_12_par: '',
     hole_13_desc: '',
     hole_13_yds: '',
     hole_13_par: '',
     hole_14_desc: '',
     hole_14_yds: '',
     hole_14_par: '',
     hole_15_desc: '',
     hole_15_yds: '',
     hole_15_par: '',
     hole_16_desc: '',
     hole_16_yds: '',
     hole_16_par: '',
     hole_17_desc: '',
     hole_17_yds: '',
     hole_17_par: '',
     hole_18_desc: '',
     hole_18_yds: '',
     hole_18_par: '',
   });
 }

 checkIfExists(golfCourse) {
  return firebase.database().ref('GolfCourse/' + this.golfCourse).once('value').then(function(snapshot) {
    var courseName = snapshot.val();
    console.log(courseName);
    return courseName;
    });
}
}

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
     email: ['', Validators.email],
     password: ['', Validators.minLength(6)],
     golfCourse: ['', Validators.minLength(3)]
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
      this.validateCourse(data, value);
    });
  }

  writeHoleData(golfCourse) {
   firebase.database().ref('GolfCourse/' + this.golfCourse).set({
     golfCourse: this.golfCourse,
     hole_01_desc: 'No description set',
     hole_01_yds: 'No distance set',
     hole_01_par: 'No par set',
     hole_02_desc: 'No description set',
     hole_02_yds: 'No distance set',
     hole_02_par: 'No par set',
     hole_03_desc: 'No description set',
     hole_03_yds: 'No distance set',
     hole_03_par: 'No par set',
     hole_04_desc: 'No description set',
     hole_04_yds: 'No distance set',
     hole_04_par: 'No par set',
     hole_05_desc: 'No description set',
     hole_05_yds: 'No distance set',
     hole_05_par: 'No par set',
     hole_06_desc: 'No description set',
     hole_06_yds: 'No distance set',
     hole_06_par: 'No par set',
     hole_07_desc: 'No description set',
     hole_07_yds: 'No distance set',
     hole_07_par: 'No par set',
     hole_08_desc: 'No description set',
     hole_08_yds: 'No distance set',
     hole_08_par: 'No par set',
     hole_09_desc: 'No description set',
     hole_09_yds: 'No distance set',
     hole_09_par: 'No par set',
     hole_10_desc: 'No description set',
     hole_10_yds: 'No distance set',
     hole_10_par: 'No par set',
     hole_11_desc: 'No description set',
     hole_11_yds: 'No distance set',
     hole_11_par: 'No par set',
     hole_12_desc: 'No description set',
     hole_12_yds: 'No distance set',
     hole_12_par: 'No par set',
     hole_13_desc: 'No description set',
     hole_13_yds: 'No distance set',
     hole_13_par: 'No par set',
     hole_14_desc: 'No description set',
     hole_14_yds: 'No distance set',
     hole_14_par: 'No par set',
     hole_15_desc: 'No description set',
     hole_15_yds: 'No distance set',
     hole_15_par: 'No par set',
     hole_16_desc: 'No description set',
     hole_16_yds: 'No distance set',
     hole_16_par: 'No par set',
     hole_17_desc: 'No description set',
     hole_17_yds: 'No distance set',
     hole_17_par: 'No par set',
     hole_18_desc: 'No description set',
     hole_18_yds: 'No distance set',
     hole_18_par: 'No par set',
   });
 }

  checkIfExists(golfCourse) {
  return firebase.database().ref('GolfCourse/' + this.golfCourse).once('value').then(function(snapshot) {
    const courseName = snapshot.val();
    return courseName;
    });
  }

  validateCourse(course, value) {
    if (this.course === null) {
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
    if (this.course != null) {
      this.alreadyExists = 'Already Exists';
    }
  }
}

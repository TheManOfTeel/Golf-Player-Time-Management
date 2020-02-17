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
    });
    firebase.database().ref('GolfCourse/' + this.golfCourse + '/Hole01').set({
      Description: 'No description set',
      Yards: 'No distance set',
      Par: 'No par set',
    });
    firebase.database().ref('GolfCourse/' + this.golfCourse + '/Hole02').set({
    Description: 'No description set',
    Yards: 'No distance set',
    Par: 'No par set',
    });
    firebase.database().ref('GolfCourse/' + this.golfCourse + '/Hole03').set({
      Description: 'No description set',
      Yards: 'No distance set',
      Par: 'No par set',
    });
    firebase.database().ref('GolfCourse/' + this.golfCourse + '/Hole04').set({
      Description: 'No description set',
      Yards: 'No distance set',
      Par: 'No par set',
    });
    firebase.database().ref('GolfCourse/' + this.golfCourse + '/Hole05').set({
      Description: 'No description set',
      Yards: 'No distance set',
      Par: 'No par set',
    });
    firebase.database().ref('GolfCourse/' + this.golfCourse + '/Hole06').set({
      Description: 'No description set',
      Yards: 'No distance set',
      Par: 'No par set',
    });
    firebase.database().ref('GolfCourse/' + this.golfCourse + '/Hole07').set({
      Description: 'No description set',
      Yards: 'No distance set',
      Par: 'No par set',
    });
    firebase.database().ref('GolfCourse/' + this.golfCourse + '/Hole08').set({
      Description: 'No description set',
      Yards: 'No distance set',
      Par: 'No par set',
    });
    firebase.database().ref('GolfCourse/' + this.golfCourse + '/Hole09').set({
      Description: 'No description set',
      Yards: 'No distance set',
      Par: 'No par set',
    });
    firebase.database().ref('GolfCourse/' + this.golfCourse + '/Hole10').set({
      Description: 'No description set',
      Yards: 'No distance set',
      Par: 'No par set',
    });
    firebase.database().ref('GolfCourse/' + this.golfCourse + '/Hole11').set({
      Description: 'No description set',
      Yards: 'No distance set',
      Par: 'No par set',
    });
    firebase.database().ref('GolfCourse/' + this.golfCourse + '/Hole12').set({
      Description: 'No description set',
      Yards: 'No distance set',
      Par: 'No par set',
    });
    firebase.database().ref('GolfCourse/' + this.golfCourse + '/Hole13').set({
      Description: 'No description set',
      Yards: 'No distance set',
      Par: 'No par set',
    });
    firebase.database().ref('GolfCourse/' + this.golfCourse + '/Hole14').set({
      Description: 'No description set',
      Yards: 'No distance set',
      Par: 'No par set',
    });
    firebase.database().ref('GolfCourse/' + this.golfCourse + '/Hole15').set({
      Description: 'No description set',
      Yards: 'No distance set',
      Par: 'No par set',
    });
    firebase.database().ref('GolfCourse/' + this.golfCourse + '/Hole16').set({
      Description: 'No description set',
      Yards: 'No distance set',
      Par: 'No par set',
    });
    firebase.database().ref('GolfCourse/' + this.golfCourse + '/Hole17').set({
      Description: 'No description set',
      Yards: 'No distance set',
      Par: 'No par set',
    });
    firebase.database().ref('GolfCourse/' + this.golfCourse + '/Hole18').set({
      Description: 'No description set',
      Yards: 'No distance set',
      Par: 'No par set',
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
        this.errorMessage = null;
        this.alreadyExists = null;
        this.successMessage = 'Your account has been created';
        this.writeHoleData(this.golfCourse);
        firebase.database().ref('/Users/' + res.user.uid).set({
          email: res.user.email,
          password: this.password,
          isAdmin: true,
          golfCourse: this.golfCourse
        });
      }, err => {
          console.log(err);
          this.alreadyExists = null;
          this.errorMessage = err.message;
          this.successMessage = '';
        });
    }
    if (this.course != null) {
      this.alreadyExists = 'Already Exists';
      this.errorMessage = null;
    }
  }
}

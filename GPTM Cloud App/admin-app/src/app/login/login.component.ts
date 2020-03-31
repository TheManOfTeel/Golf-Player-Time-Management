import { Component } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RegisterComponent } from '../register/register.component';
import { MatDialog } from '@angular/material/dialog';
import * as firebase from 'firebase/app';
import 'firebase/database';
import 'firebase/auth';
import { PasswordResetComponent } from './password-reset/password-reset.component';

@Component({
  selector: 'app-login',
  templateUrl: 'login.component.html',
  styleUrls: ['login.component.css']
})
export class LoginComponent {

  loginForm: FormGroup;
  errorMessage = '';
  unverified = false;
  isAdmin: any;
  noAdmin = false;
  placeSearch: any;
  autocomplete: any;
  componentForm = {
    street_number: 'short_name',
    route: 'long_name',
    locality: 'long_name',
    administrative_area_level_1: 'short_name',
    country: 'long_name',
    postal_code: 'short_name'
  };
  isLoading = false;
  hide = true;
  i: number;
  golfCourse: any;
  courseExists: any;
  numberOfHoles: any;
  latitude: any;
  longitude: any;
  tryAgain = false;

  constructor(
    public authService: AuthService,
    private router: Router,
    private fb: FormBuilder,
    public dialog: MatDialog
  ) {
    this.createForm();
  }

  // Set validation
  createForm() {
    this.loginForm = this.fb.group({
      email: ['', Validators.email],
      password: ['', Validators.minLength(6)]
    });
  }

  // This method is left is in case Google sign in is allowed later
  // tryGoogleLogin() {
  //   this.authService.doGoogleLogin()
  //   .then(() => {
  //     this.router.navigate(['/dashboard']);
  //   });
  // }

  tryLogin(value) {
    this.isLoading = true;
    this.authService.doLogin(value)
    .then(() => {
      // If email verified
      if (firebase.auth().currentUser.emailVerified) {
        this.getCourseStatus()
        .then(val => {
          this.golfCourse = val;
          this.checkIfExists(this.golfCourse)
          .then(val => {
            this.courseExists = val;
            // If not a first time sign in
            if (this.courseExists !== null) {
              this.getPendingInfo()
              .then(data => {
                console.log(!data.numberOfHoles);
                if (!data.numberOfHoles) {
                  this.evalAdmin()
                  .then(val => {
                    this.isAdmin = val;
                    this.checkAdmin(this.isAdmin);
                  });
                }
                if (data.numberOfHoles) {
                  this.removeUser();
                  this.isLoading = false;
                  this.tryAgain = true;
                }
              });
            }
            // If first time sign in init a default golf course in Firebase
            if (this.courseExists === null) {
              this.getPendingInfo()
              .then(data => {
                this.golfCourse = data.golfCourse;
                this.numberOfHoles = data.numberOfHoles;
                this.latitude = data.lat;
                this.longitude = data.long;
                this.writeHoleData(this.golfCourse, this.latitude, this.longitude, this.numberOfHoles);
                this.removePending();
                this.evalAdmin()
                .then(val => {
                  this.isAdmin = val;
                  this.checkAdmin(this.isAdmin);
                });
              });
            }
          });
        });
      }
      // If email is not verified
      if (!firebase.auth().currentUser.emailVerified) {
        this.unverified = true;
        this.errorMessage = null;
        this.isLoading = false;
      }
    }, err => {
      this.tryAgain = false;
      this.unverified = false;
      this.noAdmin = false;
      this.errorMessage = 'Invalid username/password';
      this.errorMessage = err.message;
      this.isLoading = false;
    });
  }

  // Pull in admin data
  evalAdmin() {
    const userId = firebase.auth().currentUser.uid;
    return firebase.database().ref('/Users/' + userId).once('value').then(function(snapshot) {
      const isAdmin = (snapshot.val() && snapshot.val().isAdmin) || false;
      return isAdmin;
      });
  }

  // Determine if the user is an actual admin
  checkAdmin(isAdmin) {
    if (this.isAdmin === true) {
      this.noAdmin = false;
      this.router.navigate(['/dashboard']);
      this.isLoading = false;
    }
    if (this.isAdmin === false) {
      this.noAdmin = true;
      this.isLoading = false;
    }
  }

  // Grab course for user
  getCourseStatus() {
    const userId = firebase.auth().currentUser.uid;
    return firebase.database().ref('/Users/' + userId).once('value').then(function(snapshot) {
      const golfCourse = snapshot.val().golfCourse;
      return golfCourse;
    });
  }

  // See if the course is in use already
  checkIfExists(courseName) {
    return firebase.database().ref('GolfCourse/' + courseName).once('value').then(function(snapshot) {
      const courseName = snapshot.val();
      return courseName;
    });
  }

  // Get the necessary data to make a new course
  getPendingInfo() {
    const userId = firebase.auth().currentUser.uid;
    return firebase.database().ref('/Users/' + userId).once('value').then(function(snapshot) {
      const data = snapshot.val();
      return data;
    });
  }

    /* tslint:disable:quotemark */
    /* tslint:disable:object-literal-shorthand */
  // Initialize the GolfCourse Firebase structure
  writeHoleData(courseName, latitude, longitude, selectedNumber) {
    firebase.database().ref('GolfCourse/' + courseName).set({
      golfCourse: courseName,
      latitude: latitude,
      longitude: longitude
    });
    for (this.i = 1; this.i <= selectedNumber; this.i++) {
      firebase.database().ref('GolfCourse/' + courseName + '/Holes' + '/Hole' + this.i).set({
        Description: 'No description set',
        Tips: 'No tips set',
      });
      firebase.database().ref('GolfCourse/' + courseName + '/Holes' + '/Hole' + this.i + '/Red_Circle').set({
        Description: "Men's professional tee",
        Tips: 'No tips set',
        Yards: 0,
        Par: 0,
      });
      firebase.database().ref('GolfCourse/' + courseName + '/Holes' + '/Hole' + this.i + '/Blue_Square').set({
        Description: "Men's average tee",
        Tips: 'No tips set',
        Yards: 0,
        Par: 0,
      });
      firebase.database().ref('GolfCourse/' + courseName + '/Holes' + '/Hole' + this.i + '/Yellow_Triangle').set({
        Description: "Women's professional tee",
        Tips: 'No tips set',
        Yards: 0,
        Par: 0,
      });
      firebase.database().ref('GolfCourse/' + courseName + '/Holes' + '/Hole' + this.i + '/Pink_Diamond').set({
        Description: "Women's average tee",
        Tips: 'No tips set',
        Yards: 0,
        Par: 0,
      });
      firebase.database().ref('GolfCourse/' + courseName + '/WaitTimes/Hole' + this.i).set({
        Queue: 0,
        WaitTime: 0
      });
    }
  }
  /* tslint:enable:quotemark */
  /* tslint:enable:object-literal-shorthand */

  // Get rid of the unnecessary nodes
  removePending() {
    const userId = firebase.auth().currentUser.uid;
    const userRef = firebase.database().ref('/Users/' + userId);
    userRef.child('numberOfHoles').remove();
    userRef.child('lat').remove();
    userRef.child('long').remove();
    userRef.update({
      verified: true
    });
  }

  removeUser() {
    const user = firebase.auth().currentUser;
    const userId = firebase.auth().currentUser.uid;
    firebase.database().ref('/Users/' + userId).remove();
    user.delete();
  }

  // Pop-up register form
  tryRegister(): void {
    this.dialog.open(RegisterComponent, {
      disableClose: true,
      width: '800px',
      height: '550px'
    });
  }

  // Open email submission
  resetPassword(): void {
    this.dialog.open(PasswordResetComponent, {
      disableClose: true,
      width: '400px',
      height: '300px'
    });
  }

  // For golf course search autocomplete
  geolocate() {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(function(position) {
        const geolocation = {
          lat: position.coords.latitude,
          lng: position.coords.longitude
        };
        const circle = new google.maps.Circle({
          center: geolocation,
          radius: position.coords.accuracy
        });
        this.autocomplete.setBounds(circle.getBounds());
      });
    }
  }
}

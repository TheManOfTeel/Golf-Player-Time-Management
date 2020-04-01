import { Component, OnInit, Injectable } from '@angular/core';
import { UserService } from '../services/user.service';
import { AuthService } from '../services/auth.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { FirebaseUserModel } from '../authentication/user.model';
import * as firebase from 'firebase/app';
import 'firebase/database';
import 'firebase/auth';

@Component({
  selector: 'app-user',
  templateUrl: 'user.component.html',
  styleUrls: ['user.component.css']
})

@Injectable()
export class UserComponent implements OnInit {

  user: FirebaseUserModel = new FirebaseUserModel();
  profileForm: FormGroup;
  golfCourse: any;

  constructor(
    public userService: UserService,
    public authService: AuthService,
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private router: Router,
  ) {

  }

  ngOnInit(): void {
    this.route.data.subscribe(routeData => {
      /* tslint:disable:no-string-literal */
      const data = routeData['data'];
      /* tslint:enable:no-string-literal */
      if (data) {
        this.user = data;
        this.createForm(this.user.name);
      }
      this.setBannerName()
      .then(val => {
        this.golfCourse = val;
      });
    });
  }

  createForm(name) {
    this.profileForm = this.fb.group({
      name: [name, Validators.required ]
    });
  }

  logout() {
    this.authService.doLogout()
    .then(() => {
      this.router.navigate(['/login']);
    }, (error) => {
      console.log('Logout error', error);
    });
  }

  // Golf Course name on banner
  setBannerName() {
    const userId = firebase.auth().currentUser.uid;
    return firebase.database().ref('/Users/' + userId).once('value').then(function(snapshot) {
      const golfCourse = (snapshot.val() && snapshot.val().golfCourse) || 'No Associated Course';
      return golfCourse;
    });
  }
}

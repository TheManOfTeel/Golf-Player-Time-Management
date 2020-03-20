import { Component, OnInit, Output, EventEmitter, Injectable } from '@angular/core';
import { UserService } from '../services/user.service';
import { AuthService } from '../services/auth.service';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { FirebaseUserModel } from '../authentication/user.model';
import * as firebase from 'firebase';

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
    private location: Location,
    private fb: FormBuilder
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

  save(value) {
    this.userService.updateCurrentUser(value)
    .then(res => {
      console.log(res);
    }, err => console.log(err));
  }

  logout() {
    this.authService.doLogout()
    .then((res) => {
      this.location.back();
    }, (error) => {
      console.log('Logout error', error);
    });
  }

  setBannerName() {
    const userId = firebase.auth().currentUser.uid;
    return firebase.database().ref('/Users/' + userId).once('value').then(function(snapshot) {
      const golfCourse = (snapshot.val() && snapshot.val().golfCourse) || 'No Associated Course';
      return golfCourse;
    });
  }
}

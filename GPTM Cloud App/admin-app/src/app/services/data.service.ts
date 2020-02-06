import { Injectable } from '@angular/core';
import * as firebase from 'firebase';

@Injectable({
  providedIn: 'root'
})
export class DataService {

  constructor() { }

  writeUserData(userId, email, golfCourse) {
    firebase.database().ref('users/' + userId).set({
      email: email,
      isAdmin: true,
      golfCourse: golfCourse
    });
  }
}

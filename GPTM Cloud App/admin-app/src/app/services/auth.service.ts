import { Injectable } from '@angular/core';
import { AngularFireAuth } from '@angular/fire/auth';
import * as firebase from 'firebase/app';
import 'firebase/auth';

@Injectable()
export class AuthService {

  constructor(
   public afAuth: AngularFireAuth
  ) {}

  // Google integration
  // doGoogleLogin() {
  //   return new Promise<any>((resolve, reject) => {
  //     const provider = new firebase.auth.GoogleAuthProvider();
  //     provider.addScope('profile');
  //     provider.addScope('email');
  //     this.afAuth.auth
  //     .signInWithPopup(provider)
  //     .then(res => {
  //       resolve(res);
  //     }, err => {
  //       console.log(err);
  //       reject(err);
  //     });
  //   });
  // }

  // Standard Firebase auth registration
  doRegister(value) {
    return new Promise<any>((resolve, reject) => {
      firebase.auth().createUserWithEmailAndPassword(value.email, value.password)
      .then(res => {
        resolve(res);
        res.user.sendEmailVerification();
      }, err => reject(err));
    });
  }

  // Standard Firebase auth login
  doLogin(value) {
    return new Promise<any>((resolve, reject) => {
      firebase.auth().signInWithEmailAndPassword(value.email, value.password)
      .then(async res => {
        await firebase.auth().setPersistence(firebase.auth.Auth.Persistence.SESSION);
        resolve(res);
      }, err => reject(err));
    });
  }

  // Standard Firebase auth logout
  doLogout() {
    return new Promise ((resolve, reject) => {
      if (firebase.auth().currentUser) {
        this.afAuth.auth.signOut();
        resolve();
      }
      if (!firebase.auth().currentUser) {
        reject();
      }
    });
  }
}

import { Injectable } from '@angular/core';
// import 'rxjs/add/operator/toPromise';
import {} from 'rxjs';
import { AngularFireAuth } from '@angular/fire/auth';
import * as firebase from 'firebase/app';

@Injectable()
export class AuthService {

  constructor(
   public afAuth: AngularFireAuth
  ) {}

  // Facebook integration
  // doFacebookLogin() {
  //   return new Promise<any>((resolve, reject) => {
  //     const provider = new firebase.auth.FacebookAuthProvider();
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

  // Twitter integration
  // doTwitterLogin() {
  //   return new Promise<any>((resolve, reject) => {
  //     const provider = new firebase.auth.TwitterAuthProvider();
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
      }, err => reject(err));
    });
  }

  // Standard Firebase auth login
  doLogin(value) {
    return new Promise<any>((resolve, reject) => {
      firebase.auth().signInWithEmailAndPassword(value.email, value.password)
      .then(res => {
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

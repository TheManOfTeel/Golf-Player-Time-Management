import * as firebase from 'firebase/app';
import 'firebase/database';
import 'firebase/auth';

// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: true
};

const config = {
  apiKey: 'YOUR_KEY',
  authDomain: 'golf-player-time-management.firebaseapp.com',
  databaseURL: 'https://golf-player-time-management.firebaseio.com',
  projectId: 'golf-player-time-management',
  storageBucket: 'golf-player-time-management.appspot.com',
  messagingSenderId: '1004433550779',
  appId: '1:1004433550779:web:d53775264a0828871ba89e',
  measurementId: 'G-EKR6YL2PS3'
};

firebase.initializeApp(config);

// Get a reference to the database service
const database = firebase.database();

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.

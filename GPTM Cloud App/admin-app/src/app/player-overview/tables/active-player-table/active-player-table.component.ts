import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import * as firebase from 'firebase/app';
import 'firebase/database';
import 'firebase/auth';
import { AngularFireDatabase } from '@angular/fire/database';

let holeQueue;
let holePar;

@Component({
  selector: 'app-active-player-table',
  templateUrl: './active-player-table.component.html',
  styleUrls: ['./active-player-table.component.css']
})
export class ActivePlayerTableComponent implements OnInit {
  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;

  playerDisplayedColumns: string[] = ['GroupLeader', 'Location', 'TimeStarted'];
  playerDataSource: MatTableDataSource<any> = new MatTableDataSource();
  playerData: any;

  courseName: any;
  hole1Wait: number;
  info: any;

  // Read course
  getCourseName() {
    const userId = firebase.auth().currentUser.uid;
    return firebase.database().ref('/Users/' + userId).once('value').then(function(snapshot) {
      const golfCourse = (snapshot.val() && snapshot.val().golfCourse || 'No Associated Course');
      return golfCourse;
    });
  }

  // Read the number of holes
  getCourseDetails(courseName) {
    // use the golfCourse value to match it to the GolfCourse table and get the hole info
    return firebase.database().ref('/GolfCourse/' + courseName + '/Holes').once('value').then(function(snapshot) {
      // All the data is being pulled here. Assign it a value then it can be shown in the front end.
      const data = snapshot.val();
      return data;
    });
  }

  // Reset fields to 0
  clearQueue(courseName, holeNum) {
    const holeref = firebase.database().ref('Games/' + courseName);
    // Reset
    holeref.orderByChild('Location').equalTo(holeNum).on('value', function() {
      const queueRef = firebase.database().ref('GolfCourse/' + courseName + '/WaitTimes/Hole' + holeNum);
      queueRef.update({
        Queue: 0,
        WaitTime: 0
      });
    });
  }

  // Calculate wait times by reading the number of groups at each hole
  countHoleQueue(courseName, holeNum) {
    let i = 1;
    firebase.database().ref('GolfCourse/' + courseName + '/Holes/Hole' + holeNum + '/Blue_Square').once('value').then(function(snapshot) {
      holePar = snapshot.val().Par;
      const holeref = firebase.database().ref('Games/' + courseName);
      // Update on new games
      holeref.orderByChild('Location').equalTo(holeNum).on('child_added', function() {
        holeQueue = i;
        i++;
        const queueRef = firebase.database().ref('GolfCourse/' + courseName + '/WaitTimes/Hole' + holeNum);
        queueRef.update({
          Queue: holeQueue,
          WaitTime: (holePar * 3.2 * holeQueue).toFixed(1)
        });
      });
      // Update on changes to in progress games
      holeref.orderByChild('Location').equalTo(holeNum).on('child_changed', function() {
        holeQueue = i;
        i++;
        const queueRef = firebase.database().ref('GolfCourse/' + courseName + '/WaitTimes/Hole' + holeNum);
        queueRef.update({
          Queue: holeQueue,
          WaitTime: (holePar * 3.2 * holeQueue).toFixed(1)
        });
      });
      // Update on deletion
      holeref.orderByChild('Location').equalTo(holeNum).on('child_removed', function() {
        holeQueue = i;
        i++;
        const queueRef = firebase.database().ref('GolfCourse/' + courseName + '/WaitTimes/Hole' + holeNum);
        queueRef.update({
          Queue: holeQueue,
          WaitTime: (holePar * 3.2 * holeQueue).toFixed(1)
        });
      });
    });
  }

  constructor(public db: AngularFireDatabase) { }

  ngOnInit(): void {
    this.getCourseName()
    .then(val => {
      this.courseName = val;

      this.getCourseDetails(this.courseName)
      .then(data => {
        this.info = data;
        this.playerDataSource.paginator = this.paginator;
        this.playerDataSource.sort = this.sort;
        // Sort by date descending on init
        this.sort.sort({ id: 'Location', start: 'asc', disableClear: false });

        if (this.info.Hole18 != null) {
                // This is what is fed into the table
          this.db.list('Games/' + this.courseName).valueChanges().subscribe(res => {
            this.playerData = res;
            this.playerDataSource.data = this.playerData;

            // Clear previous calculations
            this.clearQueue(this.courseName, '1');
            for (let i = 2; i <= 18; i++) {
              this.clearQueue(this.courseName, i);
            }
            // Estimate wait times and set the waiting queue
            this.countHoleQueue(this.courseName, '1');
            for (let i = 2; i <= 18; i++) {
              this.countHoleQueue(this.courseName, i);
            }
          });
        }
        if (!this.info.Hole18) {
                // This is what is fed into the table
          this.db.list('Games/' + this.courseName).valueChanges().subscribe(res => {
            this.playerData = res;
            this.playerDataSource.data = this.playerData;

            // Clear previous calculations
            this.clearQueue(this.courseName, '1');
            for (let i = 2; i <= 18; i++) {
              this.clearQueue(this.courseName, i);
            }
            // Estimate wait times and set the waiting queue
            this.countHoleQueue(this.courseName, '1');
            for (let i = 2; i <= 9; i++) {
              this.countHoleQueue(this.courseName, i);
            }
          });
        }
      });
    });
  }
}

// What we want to read
export interface PlayerData {
  CurrentHole: string;
  GroupLeader: string;
  Location: string;
  TimeStarted: string;
}

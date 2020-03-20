import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import * as firebase from 'firebase';
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

  getCourseName() {
    const userId = firebase.auth().currentUser.uid;
    return firebase.database().ref('/Users/' + userId).once('value').then(function(snapshot) {
      const golfCourse = (snapshot.val() && snapshot.val().golfCourse || 'No Associated Course');
      return golfCourse;
    });
  }

  countHoleQueue(courseName, holeNum) {
    let i = 1;
    firebase.database().ref('GolfCourse/' + courseName + '/Holes/Hole' + holeNum + '/Blue_Square').once('value').then(function(snapshot) {
      holePar = snapshot.val().Par;
      const holeref = firebase.database().ref('Games/' + courseName);
      holeref.orderByChild('Location').equalTo(holeNum).on('child_added', function() {
        holeQueue = i;
        i++;
        const queueRef = firebase.database().ref('GolfCourse/' + courseName + '/WaitTimes/Hole' + holeNum);
        queueRef.update({
          Queue: holeQueue,
          WaitTime: (holePar * 3.2 * holeQueue).toFixed(0)
        });
      });
    });
  }

  constructor(public db: AngularFireDatabase) { }

  ngOnInit(): void {
    this.getCourseName()
    .then(val => {
      this.courseName = val;

      this.playerDataSource.paginator = this.paginator;
      this.playerDataSource.sort = this.sort;

      // Sort by date descending on init
      this.sort.sort({ id: 'Location', start: 'asc', disableClear: false });

      this.db.list('Games/' + this.courseName).valueChanges().subscribe(res => {
        this.playerData = res;
        this.playerDataSource.data = this.playerData;
        // Estimate wait times and set the waiting queue
        for (let i = 1; i <= 18; i++) {
          this.countHoleQueue(this.courseName, i);
        }
      });
    });
  }

}

export interface PlayerData {
  CurrentHole: string;
  GroupLeader: string;
  Location: string;
  TimeStarted: string;
}

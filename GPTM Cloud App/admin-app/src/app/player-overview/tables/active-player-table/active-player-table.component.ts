import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort, Sort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import * as firebase from 'firebase';
import { AngularFireDatabase } from '@angular/fire/database';

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

  getCourseName() {
    const userId = firebase.auth().currentUser.uid;
    return firebase.database().ref('/Users/' + userId).once('value').then(function(snapshot) {
      const golfCourse = (snapshot.val() && snapshot.val().golfCourse || 'No Associated Course');
      return golfCourse;
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
      this.sort.sort({ id: 'TimeStarted', start: 'desc', disableClear: false });

      this.db.list('Games/' + this.courseName).valueChanges().subscribe(res => {
        this.playerData = res;
        this.playerDataSource.data = this.playerData;
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

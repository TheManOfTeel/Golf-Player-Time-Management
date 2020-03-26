import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import * as firebase from 'firebase';
import { AngularFireDatabase } from '@angular/fire/database';

@Component({
  selector: 'app-requests-table',
  templateUrl: './requests-table.component.html',
  styleUrls: ['./requests-table.component.css']
})
export class RequestsTableComponent implements OnInit {
  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;

  requestsDisplayedColumns: string[] = ['User', 'Request', 'Hole', 'Time', 'Actions'];
  requestsDataSource: MatTableDataSource<any> = new MatTableDataSource();
  requestsData: any;

  courseName: any;
  index: number;

  // Read course
  getCourseName() {
    const userId = firebase.auth().currentUser.uid;
    return firebase.database().ref('/Users/' + userId).once('value').then(function(snapshot) {
      const golfCourse = (snapshot.val() && snapshot.val().golfCourse || 'No Associated Course');
      return golfCourse;
    });
  }

  // Delete the request by figuring out the unknown key
  removeRequest(i: number) {
    const ref = firebase.database().ref('Request/' + this.courseName);
    ref.once('value').then(function(snapshot) {
      // Get the key and then remove the data belonging to that key
      const key = Object.keys(snapshot.val())[i];
      ref.child(key).remove();
    });
  }

  constructor(public db: AngularFireDatabase) { }

  ngOnInit(): void {
    this.getCourseName()
    .then(val => {
      this.courseName = val;
      this.requestsDataSource.paginator = this.paginator;
      this.requestsDataSource.sort = this.sort;

      // Sort by time desc on init
      this.sort.sort({ id: 'Time', start: 'desc', disableClear: false });

      // Grab the requests data for the table
      this.db.list('Request/' + this.courseName).valueChanges().subscribe(res => {
        this.requestsData = res;
        this.requestsDataSource.data = this.requestsData;
      });
    });
  }
}

// This is what we want to show
export interface RequestsData {
  key: string;
  Hole: string;
  Location: string;
  Request: string;
  Time: string;
  User: string;
}

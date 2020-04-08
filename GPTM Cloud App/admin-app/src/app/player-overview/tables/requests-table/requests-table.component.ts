import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import * as firebase from 'firebase/app';
import 'firebase/database';
import 'firebase/auth';
import { AngularFireDatabase } from '@angular/fire/database';

@Component({
  selector: 'app-requests-table',
  templateUrl: './requests-table.component.html',
  styleUrls: ['./requests-table.component.css']
})
export class RequestsTableComponent implements OnInit {
  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;

  requestsDisplayedColumns: string[] = ['User', 'Request', 'Location', 'Time', 'Status', 'Actions'];
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

  // Change status of the request to pending
  acknowledgeRequest(i: number) {
    const ref = firebase.database().ref('Request/' + this.courseName);
    ref.once('value').then(function(snapshot) {
      // Get the key and then update the data belonging to that key
      const key = Object.keys(snapshot.val())[i];
      ref.child(key).update({
        Status: 'In Progress'
      });
    });
  }

  // Change status of the request to complete
  completeRequest(i: number) {
    const ref = firebase.database().ref('Request/' + this.courseName);
    ref.once('value').then(function(snapshot) {
      // Get the key and then update the data belonging to that key
      const key = Object.keys(snapshot.val())[i];
      ref.child(key).update({
        Status: 'Complete'
      });
    });
  }

  constructor(public db: AngularFireDatabase) { }

  ngOnInit(): void {
    this.getCourseName()
    .then(val => {
      this.courseName = val;
      this.requestsDataSource.paginator = this.paginator;

      // Commented out because of the keys are hard to track when the index is variable
      // this.requestsDataSource.sort = this.sort;
      // Sort by time desc on init
      // this.sort.sort({ id: 'Time', start: 'desc', disableClear: false });

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
  Hole: string;
  Location: string;
  Request: string;
  Status: string;
  Time: string;
  User: string;
}

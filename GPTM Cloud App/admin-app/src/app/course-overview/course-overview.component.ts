import { Component, OnInit, Input } from '@angular/core';
import * as firebase from 'firebase';

// For this component we only need to input a description, par number, and tips. The scorecard is now irrelevant, admins are not
// concerned about this.
@Component({
  selector: 'app-course-overview',
  templateUrl: './course-overview.component.html',
  styleUrls: ['./course-overview.component.css']
})
export class CourseOverviewComponent implements OnInit {
  courseName: any;
  info: any;
  isEdit = false;
  


  constructor() { }

  ngOnInit() {
    this.initData();
  }

  initData() {
    this.getCourseName()
    .then(val => {
      this.courseName = val;
      this.getCourseDetails(this.courseName)
      .then(data => {
        this.info = data;
        console.log(this.info);
      });
    });
  }

  getCourseName() {
    var userId = firebase.auth().currentUser.uid;
    return firebase.database().ref('/Users/' + userId).once('value').then(function(snapshot) {
      var golfCourse = (snapshot.val() && snapshot.val().golfCourse || 'No Associated Course');
      return golfCourse;
    });
  }

  getCourseDetails(courseName) {
    // use the golfCourse value to match it to the GolfCourse table and get the hole info
    return firebase.database().ref('/GolfCourse/' + this.courseName).once('value').then(function(snapshot) {
      // All the data is being pulled here. Assign it a value then it can be shown in the front end.
      var data = snapshot.val();
      return data;
    })
  }

  doEdit() {
    this.isEdit = true;
    return this.isEdit;
  }

  cancelEdit() {
    this.isEdit = false;
    return this.isEdit;
  }

  saveData() {
    // push new data to database
    console.log('save');
  }

}

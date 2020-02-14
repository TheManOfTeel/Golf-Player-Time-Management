import { Component, OnInit, Input } from '@angular/core';
import * as firebase from 'firebase';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

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
  child1: string;
  child2: string;
  child3: string;
  value1: string;
  value2: number;
  value3: number;
  newDataForm: FormGroup;



  constructor(
    private fb: FormBuilder
  ) {
    this.createForm();
  }

  ngOnInit() {
    this.initData();
  }

  createForm() {
    this.newDataForm = this.fb.group({
     value1: ['', Validators.required],
     value2: ['', Validators.required],
     value3: ['', Validators.required]
     });
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
    const userId = firebase.auth().currentUser.uid;
    return firebase.database().ref('/Users/' + userId).once('value').then(function(snapshot) {
      const golfCourse = (snapshot.val() && snapshot.val().golfCourse || 'No Associated Course');
      return golfCourse;
    });
  }

  getCourseDetails(courseName) {
    // use the golfCourse value to match it to the GolfCourse table and get the hole info
    return firebase.database().ref('/GolfCourse/' + this.courseName).once('value').then(function(snapshot) {
      // All the data is being pulled here. Assign it a value then it can be shown in the front end.
      const data = snapshot.val();
      return data;
    });
  }

  doEdit() {
    this.isEdit = true;
    return this.isEdit;
  }

  cancelEdit() {
    this.isEdit = false;
    return this.isEdit;
  }

  saveData(child1, child2, child3) {
    this.child1 = child1;
    this.child2 = child2;
    this.child3 = child3;
    const courseRef = firebase.database().ref('/GolfCourse/').child(this.courseName);
    // push new data to database
    console.log('save');
    console.log(this.value1);
    console.log(this.value2);
    console.log(this.value3);
    if (this.value1 != null && this.value2 != null && this.value3 != null) {
      courseRef.update({
        // Save hole description
        [child1]: this.value1,
        // Save yards to hole
        [child2]: this.value2,
        // Save swings to par
        [child3]: this.value3
      });
    }
    if (this.value1 != null && this.value2 != null) {
      courseRef.update({
        // Save hole description
        [child1]: this.value1,
        // Save yards to hole
        [child2]: this.value2
      });
    }
    if (this.value1 != null && this.value3 != null) {
      courseRef.update({
        // Save hole description
        [child1]: this.value1,
        // Save swings to par
        [child3]: this.value3
      });
    }
    if (this.value2 != null && this.value3 != null) {
      courseRef.update({
        // Save yards to hole
        [child2]: this.value2,
        // Save swings to par
        [child3]: this.value3
      });
    }
    if (this.value1 != null) {
      courseRef.update({
        // Save hole description
        [child1]: this.value1
      });
    }
    if (this.value2 != null) {
      courseRef.update({
        // Save yards to hole
        [child2]: this.value2
      });
    }
    if (this.value3 != null) {
      courseRef.update({
        // Save swings to par
        [child3]: this.value3
      });
    }
    this.initData();
    this.isEdit = false;
    this.value1 = null;
    this.value2 = null;
    this.value3 = null;
  }

}

import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import * as firebase from 'firebase';

@Component({
  selector: 'app-hole07',
  templateUrl: './hole07.component.html',
  styleUrls: ['./hole07.component.css']
})
export class Hole07Component implements OnInit {
  courseName: any;
  info: any;

  isEdit = false;
  child1: string;
  child2: string;
  child3: string;
  child4: string;
  value1: string;
  value2: number;
  value3: number;
  value4: string;

  newDataForm: FormGroup;

  constructor(
    private fb: FormBuilder
  ) { this.createForm(); }

  ngOnInit() {
    this.initData();
  }

  createForm() {
    this.newDataForm = this.fb.group({
     value1: [''],
     value2: [''],
     value3: ['']
     });
  }

  initData() {
    this.getCourseName()
    .then(val => {
      this.courseName = val;
      this.getCourseDetails(this.courseName)
      .then(data => {
        this.info = data;
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
    return firebase.database().ref('/GolfCourse/' + this.courseName + '/Holes/Hole7').once('value').then(function(snapshot) {
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

  saveData(attribute, child1, child2, child3, child4) {
    this.child1 = child1;
    this.child2 = child2;
    this.child3 = child3;
    this.child4 = child4;
    const courseRef = firebase.database().ref('/GolfCourse/' + this.courseName).child(attribute);
    // push new data to database
    if (this.value1 != null && this.value2 != null && this.value3 != null && this.value4 != null) {
      courseRef.update({
        // Save hole description
        [child1]: this.value1,
        // Save swings to par
        [child2]: this.value2,
        // Save tips
        [child3]: this.value3,
        // Save yards to hole
        [child4]: this.value4
      });
    }
    if (this.value1 != null && this.value2 != null && this.value3 != null) {
      courseRef.update({
        // Save hole description
        [child1]: this.value1,
        // Save swings to par
        [child2]: this.value2,
        // Save tips
        [child3]: this.value3,
      });
    }
    if (this.value1 != null && this.value3 != null && this.value4 != null) {
      courseRef.update({
        // Save hole description
        [child1]: this.value1,
        // Save tips
        [child3]: this.value3,
        // Save yards to hole
        [child4]: this.value4
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

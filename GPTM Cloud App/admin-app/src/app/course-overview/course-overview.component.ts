import { Component, OnInit, Input, ViewChild } from '@angular/core';
import * as firebase from 'firebase';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatSidenav } from '@angular/material/sidenav';

// For this component we only need to input a description, par number, and yards to hole. The scorecard is now irrelevant, admins are not
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
  displayContent = false;
  showHole01 = true;
  showHole02 = false;
  showHole03 = false;
  showHole04 = false;
  showHole05 = false;
  showHole06 = false;
  showHole07 = false;
  showHole08 = false;
  showHole09 = false;
  showHole10 = false;
  showHole11 = false;
  showHole12 = false;
  showHole13 = false;
  showHole14 = false;
  showHole15 = false;
  showHole16 = false;
  showHole17 = false;
  showHole18 = false;

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

  saveData(hole, child1, child2, child3) {
    this.child1 = child1;
    this.child2 = child2;
    this.child3 = child3;
    const courseRef = firebase.database().ref('/GolfCourse/' + this.courseName).child(hole);
    // push new data to database
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

  @ViewChild('sidenav') sidenav: MatSidenav;
  isExpanded = true;
  showSubmenu: boolean = false;
  isShowing = false;
  showSubSubMenu: boolean = false;

  mouseenter() {
    if (!this.isExpanded) {
      this.isShowing = true;
    }
  }

  mouseleave() {
    if (!this.isExpanded) {
      this.isShowing = false;
    }
  }

  focusHole01(show: boolean) {
    this.showHole01 = show;
    this.showHole02 = false;
    this.showHole03 = false;
    this.showHole04 = false;
    this.showHole05 = false;
    this.showHole06 = false;
    this.showHole07 = false;
    this.showHole08 = false;
    this.showHole09 = false;
    this.showHole10 = false;
    this.showHole11 = false;
    this.showHole12 = false;
    this.showHole13 = false;
    this.showHole14 = false;
    this.showHole15 = false;
    this.showHole16 = false;
    this.showHole17 = false;
    this.showHole18 = false;
  }

  focusHole02(show: boolean) {
    this.showHole01 = false;
    this.showHole02 = show;
    this.showHole03 = false;
    this.showHole04 = false;
    this.showHole05 = false;
    this.showHole06 = false;
    this.showHole07 = false;
    this.showHole08 = false;
    this.showHole09 = false;
    this.showHole10 = false;
    this.showHole11 = false;
    this.showHole12 = false;
    this.showHole13 = false;
    this.showHole14 = false;
    this.showHole15 = false;
    this.showHole16 = false;
    this.showHole17 = false;
    this.showHole18 = false;
  }

  focusHole03(show: boolean) {
    this.showHole01 = false;
    this.showHole02 = false;
    this.showHole03 = show;
    this.showHole04 = false;
    this.showHole05 = false;
    this.showHole06 = false;
    this.showHole07 = false;
    this.showHole08 = false;
    this.showHole09 = false;
    this.showHole10 = false;
    this.showHole11 = false;
    this.showHole12 = false;
    this.showHole13 = false;
    this.showHole14 = false;
    this.showHole15 = false;
    this.showHole16 = false;
    this.showHole17 = false;
    this.showHole18 = false;
  }

  focusHole04(show: boolean) {
    this.showHole01 = false;
    this.showHole02 = false;
    this.showHole03 = false;
    this.showHole04 = show;
    this.showHole05 = false;
    this.showHole06 = false;
    this.showHole07 = false;
    this.showHole08 = false;
    this.showHole09 = false;
    this.showHole10 = false;
    this.showHole11 = false;
    this.showHole12 = false;
    this.showHole13 = false;
    this.showHole14 = false;
    this.showHole15 = false;
    this.showHole16 = false;
    this.showHole17 = false;
    this.showHole18 = false;
  }

  focusHole05(show: boolean) {
    this.showHole01 = false;
    this.showHole02 = false;
    this.showHole03 = false;
    this.showHole04 = false;
    this.showHole05 = show;
    this.showHole06 = false;
    this.showHole07 = false;
    this.showHole08 = false;
    this.showHole09 = false;
    this.showHole10 = false;
    this.showHole11 = false;
    this.showHole12 = false;
    this.showHole13 = false;
    this.showHole14 = false;
    this.showHole15 = false;
    this.showHole16 = false;
    this.showHole17 = false;
    this.showHole18 = false;
  }

  focusHole06(show: boolean) {
    this.showHole01 = false;
    this.showHole02 = false;
    this.showHole03 = false;
    this.showHole04 = false;
    this.showHole05 = false;
    this.showHole06 = show;
    this.showHole07 = false;
    this.showHole08 = false;
    this.showHole09 = false;
    this.showHole10 = false;
    this.showHole11 = false;
    this.showHole12 = false;
    this.showHole13 = false;
    this.showHole14 = false;
    this.showHole15 = false;
    this.showHole16 = false;
    this.showHole17 = false;
    this.showHole18 = false;
  }

  focusHole07(show: boolean) {
    this.showHole01 = false;
    this.showHole02 = false;
    this.showHole03 = false;
    this.showHole04 = false;
    this.showHole05 = false;
    this.showHole06 = false;
    this.showHole07 = show;
    this.showHole08 = false;
    this.showHole09 = false;
    this.showHole10 = false;
    this.showHole11 = false;
    this.showHole12 = false;
    this.showHole13 = false;
    this.showHole14 = false;
    this.showHole15 = false;
    this.showHole16 = false;
    this.showHole17 = false;
    this.showHole18 = false;
  }

  focusHole08(show: boolean) {
    this.showHole01 = false;
    this.showHole02 = false;
    this.showHole03 = false;
    this.showHole04 = false;
    this.showHole05 = false;
    this.showHole06 = false;
    this.showHole07 = false;
    this.showHole08 = show;
    this.showHole09 = false;
    this.showHole10 = false;
    this.showHole11 = false;
    this.showHole12 = false;
    this.showHole13 = false;
    this.showHole14 = false;
    this.showHole15 = false;
    this.showHole16 = false;
    this.showHole17 = false;
    this.showHole18 = false;
  }

  focusHole09(show: boolean) {
    this.showHole01 = false;
    this.showHole02 = false;
    this.showHole03 = false;
    this.showHole04 = false;
    this.showHole05 = false;
    this.showHole06 = false;
    this.showHole07 = false;
    this.showHole08 = false;
    this.showHole09 = show;
    this.showHole10 = false;
    this.showHole11 = false;
    this.showHole12 = false;
    this.showHole13 = false;
    this.showHole14 = false;
    this.showHole15 = false;
    this.showHole16 = false;
    this.showHole17 = false;
    this.showHole18 = false;
  }

  focusHole10(show: boolean) {
    this.showHole01 = false;
    this.showHole02 = false;
    this.showHole03 = false;
    this.showHole04 = false;
    this.showHole05 = false;
    this.showHole06 = false;
    this.showHole07 = false;
    this.showHole08 = false;
    this.showHole09 = false;
    this.showHole10 = show;
    this.showHole11 = false;
    this.showHole12 = false;
    this.showHole13 = false;
    this.showHole14 = false;
    this.showHole15 = false;
    this.showHole16 = false;
    this.showHole17 = false;
    this.showHole18 = false;
  }

  focusHole11(show: boolean) {
    this.showHole01 = false;
    this.showHole02 = false;
    this.showHole03 = false;
    this.showHole04 = false;
    this.showHole05 = false;
    this.showHole06 = false;
    this.showHole07 = false;
    this.showHole08 = false;
    this.showHole09 = false;
    this.showHole10 = false;
    this.showHole11 = show;
    this.showHole12 = false;
    this.showHole13 = false;
    this.showHole14 = false;
    this.showHole15 = false;
    this.showHole16 = false;
    this.showHole17 = false;
    this.showHole18 = false;
  }

  focusHole12(show: boolean) {
    this.showHole01 = false;
    this.showHole02 = false;
    this.showHole03 = false;
    this.showHole04 = false;
    this.showHole05 = false;
    this.showHole06 = false;
    this.showHole07 = false;
    this.showHole08 = false;
    this.showHole09 = false;
    this.showHole10 = false;
    this.showHole11 = false;
    this.showHole12 = show;
    this.showHole13 = false;
    this.showHole14 = false;
    this.showHole15 = false;
    this.showHole16 = false;
    this.showHole17 = false;
    this.showHole18 = false;
  }

  focusHole13(show: boolean) {
    this.showHole01 = false;
    this.showHole02 = false;
    this.showHole03 = false;
    this.showHole04 = false;
    this.showHole05 = false;
    this.showHole06 = false;
    this.showHole07 = false;
    this.showHole08 = false;
    this.showHole09 = false;
    this.showHole10 = false;
    this.showHole11 = false;
    this.showHole12 = false;
    this.showHole13 = show;
    this.showHole14 = false;
    this.showHole15 = false;
    this.showHole16 = false;
    this.showHole17 = false;
    this.showHole18 = false;
  }

  focusHole14(show: boolean) {
    this.showHole01 = false;
    this.showHole02 = false;
    this.showHole03 = false;
    this.showHole04 = false;
    this.showHole05 = false;
    this.showHole06 = false;
    this.showHole07 = false;
    this.showHole08 = false;
    this.showHole09 = false;
    this.showHole10 = false;
    this.showHole11 = false;
    this.showHole12 = false;
    this.showHole13 = false;
    this.showHole14 = show;
    this.showHole15 = false;
    this.showHole16 = false;
    this.showHole17 = false;
    this.showHole18 = false;
  }

  focusHole15(show: boolean) {
    this.showHole01 = false;
    this.showHole02 = false;
    this.showHole03 = false;
    this.showHole04 = false;
    this.showHole05 = false;
    this.showHole06 = false;
    this.showHole07 = false;
    this.showHole08 = false;
    this.showHole09 = false;
    this.showHole10 = false;
    this.showHole11 = false;
    this.showHole12 = false;
    this.showHole13 = false;
    this.showHole14 = false;
    this.showHole15 = show;
    this.showHole16 = false;
    this.showHole17 = false;
    this.showHole18 = false;
  }

  focusHole16(show: boolean) {
    this.showHole01 = false;
    this.showHole02 = false;
    this.showHole03 = false;
    this.showHole04 = false;
    this.showHole05 = false;
    this.showHole06 = false;
    this.showHole07 = false;
    this.showHole08 = false;
    this.showHole09 = false;
    this.showHole10 = false;
    this.showHole11 = false;
    this.showHole12 = false;
    this.showHole13 = false;
    this.showHole14 = false;
    this.showHole15 = false;
    this.showHole16 = show;
    this.showHole17 = false;
    this.showHole18 = false;
  }

  focusHole17(show: boolean) {
    this.showHole01 = false;
    this.showHole02 = false;
    this.showHole03 = false;
    this.showHole04 = false;
    this.showHole05 = false;
    this.showHole06 = false;
    this.showHole07 = false;
    this.showHole08 = false;
    this.showHole09 = false;
    this.showHole10 = false;
    this.showHole11 = false;
    this.showHole12 = false;
    this.showHole13 = false;
    this.showHole14 = false;
    this.showHole15 = false;
    this.showHole16 = false;
    this.showHole17 = show;
    this.showHole18 = false;
  }

  focusHole18(show: boolean) {
    this.showHole01 = false;
    this.showHole02 = false;
    this.showHole03 = false;
    this.showHole04 = false;
    this.showHole05 = false;
    this.showHole06 = false;
    this.showHole07 = false;
    this.showHole08 = false;
    this.showHole09 = false;
    this.showHole10 = false;
    this.showHole11 = false;
    this.showHole12 = false;
    this.showHole13 = false;
    this.showHole14 = false;
    this.showHole15 = false;
    this.showHole16 = false;
    this.showHole17 = false;
    this.showHole18 = show;
  }

}

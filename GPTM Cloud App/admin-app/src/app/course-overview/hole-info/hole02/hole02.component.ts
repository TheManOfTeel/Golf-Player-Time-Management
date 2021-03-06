import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import * as firebase from 'firebase/app';
import 'firebase/database';
import 'firebase/auth';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { CourseMapComponent } from 'src/app/course-map/course-map.component';

@Component({
  selector: 'app-hole02',
  templateUrl: './hole02.component.html',
  styleUrls: ['./hole02.component.css']
})
export class Hole02Component implements OnInit {
  courseName: any;
  info: any;

  isEdit1 = false;
  isEdit2 = false;
  isEdit3 = false;
  isEdit4 = false;
  isEdit5 = false;

  mapComplete = false;

  generalDescription = 'loading...';
  generalTips = 'loading...';

  blueDescription = 'loading...';
  bluePar = 'loading...';
  blueTips = 'loading...';
  blueYards = 'loading...';

  redDescription = 'loading...';
  redPar = 'loading...';
  redTips = 'loading...';
  redYards = 'loading...';

  pinkDescription = 'loading...';
  pinkPar = 'loading...';
  pinkTips = 'loading...';
  pinkYards = 'loading...';

  yellowDescription = 'loading...';
  yellowPar = 'loading...';
  yellowTips = 'loading...';
  yellowYards = 'loading...';

  coordinates = [];

  tile1Form: FormGroup;
  tile2Form: FormGroup;
  tile3Form: FormGroup;
  tile4Form: FormGroup;
  tile5Form: FormGroup;

  constructor(
    private fb: FormBuilder,
    public dialog: MatDialog
  ) { this.createForm(); }

  ngOnInit() {
    this.initData();
    this.resetEditStat();
  }

  createForm() {
    this.tile1Form = this.fb.group({
      generalDescription: [''],
      generalTips: [''],
    });
    this.tile2Form = this.fb.group({
      blueDescription: [''],
      bluePar: ['', Validators.min(0)],
      blueTips: [''],
      blueYards: ['', Validators.min(0)],
    });
    this.tile3Form = this.fb.group({
      redDescription: [''],
      redPar: ['', Validators.min(0)],
      redTips: [''],
      redYards: ['', Validators.min(0)],
    });
    this.tile4Form = this.fb.group({
      pinkDescription: [''],
      pinkPar: ['', Validators.min(0)],
      pinkTips: [''],
      pinkYards: ['', Validators.min(0)],
    });
    this.tile5Form = this.fb.group({
      yellowDescription: [''],
      yellowPar: ['', Validators.min(0)],
      yellowTips: [''],
      yellowYards: ['', Validators.min(0)],
    });
  }

  initData() {
    this.getCourseName()
    .then(val => {
      this.courseName = val;
      this.getCourseDetails()
      .then(data => {
        this.info = data;
        this.generalDescription = data.Description;
        this.generalTips = data.Tips;

        this.blueDescription = data.Blue_Square.Description;
        this.bluePar = data.Blue_Square.Par;
        this.blueTips = data.Blue_Square.Tips;
        this.blueYards = data.Blue_Square.Yards;

        this.redDescription = data.Red_Circle.Description;
        this.redPar = data.Red_Circle.Par;
        this.redTips = data.Red_Circle.Tips;
        this.redYards = data.Red_Circle.Yards;

        this.pinkDescription = data.Pink_Diamond.Description;
        this.pinkPar = data.Pink_Diamond.Par;
        this.pinkTips = data.Pink_Diamond.Tips;
        this.pinkYards = data.Pink_Diamond.Yards;

        this.yellowDescription = data.Yellow_Triangle.Description;
        this.yellowPar = data.Yellow_Triangle.Par;
        this.yellowTips = data.Yellow_Triangle.Tips;
        this.yellowYards = data.Yellow_Triangle.Yards;

        if (data.Geofence) {
          this.mapComplete = true;
        }
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

  getCourseDetails() {
    // use the golfCourse value to match it to the GolfCourse table and get the hole info
    return firebase.database().ref('/GolfCourse/' + this.courseName + '/Holes/Hole2').once('value').then(function(snapshot) {
      // All the data is being pulled here. Assign it a value then it can be shown in the front end.
      const data = snapshot.val();
      return data;
    });
  }

  doEdit1() {
    this.isEdit1 = true;
    this.isEdit2 = false;
    this.isEdit3 = false;
    this.isEdit4 = false;
    this.isEdit5 = false;
  }

  cancelEdit1() {
    this.isEdit1 = false;
    this.initData();
    return this.isEdit1;
  }

  doEdit2() {
    this.isEdit1 = false;
    this.isEdit2 = true;
    this.isEdit3 = false;
    this.isEdit4 = false;
    this.isEdit5 = false;
  }

  cancelEdit2() {
    this.isEdit2 = false;
    this.initData();
    return this.isEdit2;
  }

  doEdit3() {
    this.isEdit1 = false;
    this.isEdit2 = false;
    this.isEdit3 = true;
    this.isEdit4 = false;
    this.isEdit5 = false;
  }

  cancelEdit3() {
    this.isEdit3 = false;
    this.initData();
    return this.isEdit3;
  }

  doEdit4() {
    this.isEdit1 = false;
    this.isEdit2 = false;
    this.isEdit3 = false;
    this.isEdit4 = true;
    this.isEdit5 = false;
  }

  cancelEdit4() {
    this.isEdit4 = false;
    this.initData();
    return this.isEdit4;
  }

  doEdit5() {
    this.isEdit1 = false;
    this.isEdit2 = false;
    this.isEdit3 = false;
    this.isEdit4 = false;
    this.isEdit5 = true;
  }

  cancelEdit5() {
    this.isEdit5 = false;
    this.initData();
    return this.isEdit5;
  }

  resetEditStat() {
    this.isEdit1 = false;
    this.isEdit2 = false;
    this.isEdit3 = false;
    this.isEdit4 = false;
    this.isEdit5 = false;
  }

  saveGeneral() {
    const courseRef = firebase.database().ref('/GolfCourse/' + this.courseName + '/Holes/Hole2');
    courseRef.update({
      Description: this.generalDescription,
      Tips: this.generalTips
    });
    this.initData();
    this.isEdit1 = false;
  }

  saveBlue() {
    const courseRef = firebase.database().ref('/GolfCourse/' + this.courseName + '/Holes/Hole2/Blue_Square');
    // push new data to database
    courseRef.update({
      // Save hole description
      Description: this.blueDescription,
      // Save swings to par
      Par: this.bluePar,
      // Save tips
      Tips: this.blueTips,
      // Save yards to hole
      Yards: this.blueYards
    });
    this.initData();
    this.isEdit2 = false;
  }

  saveRed() {
    const courseRef = firebase.database().ref('/GolfCourse/' + this.courseName + '/Holes/Hole2/Red_Circle');
    // push new data to database
    courseRef.update({
      // Save hole description
      Description: this.redDescription,
      // Save swings to par
      Par: this.redPar,
      // Save tips
      Tips: this.redTips,
      // Save yards to hole
      Yards: this.redYards
    });
    this.initData();
    this.isEdit3 = false;
  }

  savePink() {
    const courseRef = firebase.database().ref('/GolfCourse/' + this.courseName + '/Holes/Hole2/Pink_Diamond');
    // push new data to database
    courseRef.update({
      // Save hole description
      Description: this.pinkDescription,
      // Save swings to par
      Par: this.pinkPar,
      // Save tips
      Tips: this.pinkTips,
      // Save yards to hole
      Yards: this.pinkYards
    });
    this.initData();
    this.isEdit4 = false;
  }

  saveYellow() {
    const courseRef = firebase.database().ref('/GolfCourse/' + this.courseName + '/Holes/Hole2/Yellow_Triangle');
    // push new data to database
    courseRef.update({
      // Save hole description
      Description: this.yellowDescription,
      // Save swings to par
      Par: this.yellowPar,
      // Save tips
      Tips: this.yellowTips,
      // Save yards to hole
      Yards: this.yellowYards
    });
    this.initData();
    this.isEdit5 = false;
  }

  // Pop-up using the geofencing component
  mapPopUp() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    const dialogRef = this.dialog.open(CourseMapComponent, {
      width: '1000px',
      height: '660px',
      data: {coordinates: this.coordinates}
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.coordinates = result[0];

        const courseRef = firebase.database().ref('/GolfCourse/' + this.courseName + '/Holes/Hole2');
        // push new data to database
        courseRef.update({
          Geofence: this.coordinates,
        });
        this.initData();
      }

      if (!result) {
        this.initData();
      }
    });
  }
}

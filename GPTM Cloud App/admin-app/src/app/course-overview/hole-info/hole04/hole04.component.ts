import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import * as firebase from 'firebase';

@Component({
  selector: 'app-hole04',
  templateUrl: './hole04.component.html',
  styleUrls: ['./hole04.component.css']
})
export class Hole04Component implements OnInit {
  courseName: any;
  info: any;

  isEdit1 = false;
  isEdit2 = false;
  isEdit3 = false;
  isEdit4 = false;
  isEdit5 = false;

  generalDescription: string;
  generalTips: string;

  blueDescription: string;
  bluePar: string;
  blueTips: string;
  blueYards: string;

  redDescription: string;
  redPar: string;
  redTips: string;
  redYards: string;

  pinkDescription: string;
  pinkPar: string;
  pinkTips: string;
  pinkYards: string;

  yellowDescription: string;
  yellowPar: string;
  yellowTips: string;
  yellowYards: string;

  tile1Form: FormGroup;
  tile2Form: FormGroup;
  tile3Form: FormGroup;
  tile4Form: FormGroup;
  tile5Form: FormGroup;

  constructor(
    private fb: FormBuilder
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
      bluePar: [''],
      blueTips: [''],
      blueYards: [''],
    });
    this.tile3Form = this.fb.group({
      redDescription: [''],
      redPar: [''],
      redTips: [''],
      redYards: [''],
    });
    this.tile4Form = this.fb.group({
      pinkDescription: [''],
      pinkPar: [''],
      pinkTips: [''],
      pinkYards: [''],
    });
    this.tile5Form = this.fb.group({
      yellowDescription: [''],
      yellowPar: [''],
      yellowTips: [''],
      yellowYards: [''],
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
    return firebase.database().ref('/GolfCourse/' + this.courseName + '/Holes/Hole4').once('value').then(function(snapshot) {
      // All the data is being pulled here. Assign it a value then it can be shown in the front end.
      const data = snapshot.val();
      return data;
    });
  }

  doEdit1() {
    this.isEdit1 = true;
    return this.isEdit1;
  }

  cancelEdit1() {
    this.isEdit1 = false;
    return this.isEdit1;
  }

  doEdit2() {
    this.isEdit2 = true;
    return this.isEdit2;
  }

  cancelEdit2() {
    this.isEdit2 = false;
    return this.isEdit2;
  }

  doEdit3() {
    this.isEdit3 = true;
    return this.isEdit3;
  }

  cancelEdit3() {
    this.isEdit3 = false;
    return this.isEdit3;
  }

  doEdit4() {
    this.isEdit4 = true;
    return this.isEdit4;
  }

  cancelEdit4() {
    this.isEdit4 = false;
    return this.isEdit4;
  }

  doEdit5() {
    this.isEdit5 = true;
    return this.isEdit5;
  }

  cancelEdit5() {
    this.isEdit5 = false;
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
    const courseRef = firebase.database().ref('/GolfCourse/' + this.courseName + '/Holes/Hole4');
    courseRef.update({
      Description: this.generalDescription,
      Tips: this.generalTips
    });
    this.initData();
    this.isEdit1 = false;
  }

  saveBlue() {
    const courseRef = firebase.database().ref('/GolfCourse/' + this.courseName + '/Holes/Hole4/Blue_Square');
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
    const courseRef = firebase.database().ref('/GolfCourse/' + this.courseName + '/Holes/Hole4/Red_Circle');
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
    const courseRef = firebase.database().ref('/GolfCourse/' + this.courseName + '/Holes/Hole4/Pink_Diamond');
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
    const courseRef = firebase.database().ref('/GolfCourse/' + this.courseName + '/Holes/Hole4/Yellow_Triangle');
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
}

import { Component, OnInit } from '@angular/core';
import * as firebase from 'firebase';

// For this component we only need to input a description, par number, and tips. The scorecard is now irrelevant, admins are not
// concerned about this.
@Component({
  selector: 'app-course-overview',
  templateUrl: './course-overview.component.html',
  styleUrls: ['./course-overview.component.css']
})
export class CourseOverviewComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

  writeUserData(hole1, hole1par, hole2, hole2par, hole3, hole3par, hole4, hole4par, hole5, hole5par,
     hole6, hole6par, hole7, hole7par, hole8, hole8par, hole9, hole9par, hole10, hole10par,
    hole11, hole11par, hole12, hole12par, hole13, hole13par, hole14, hole14par, hole15, hole15par, hole16, hole16par,
    hole17, hole17par, hole18, hole18par,) {
    let userId = firebase.auth().currentUser.uid;
    firebase.database().ref('GolfCourse/' + userId).set({
      hole_1_desc: hole1,
      hole_1_par: hole1par,
      hole_2_desc: hole2,
      hole_2_par: hole2par,
      hole_3_desc: hole3,
      hole_3_par: hole3par,
      hole_4_desc: hole4,
      hole_4_par: hole4par,
      hole_5_desc: hole5,
      hole_5_par: hole5par,
      hole_6_desc: hole6,
      hole_6_par: hole6par,
      hole_7_desc: hole7,
      hole_7_par: hole7par,
      hole_8_desc: hole8,
      hole_8_par: hole8par,
      hole_9_desc: hole9,
      hole_9_par: hole9par,
      hole_10_des: hole10,
      hole_10_par: hole10par,
      hole_11_desc: hole11,
      hole_11_par: hole11par,
      hole_12_desc: hole12,
      hole_12_par: hole12par,
      hole_13_desc: hole13,
      hole_13_par: hole13par,
      hole_14_desc: hole14,
      hole_14_par: hole14par,
      hole_15_desc: hole15,
      hole_15_par: hole15par,
      hole_16_desc: hole16,
      hole_16_par: hole16par,
      hole_17_desc: hole17,
      hole_17_par: hole17par,
      hole_18_desc: hole18,
      hole_18_par: hole18par,
    });
  }

}

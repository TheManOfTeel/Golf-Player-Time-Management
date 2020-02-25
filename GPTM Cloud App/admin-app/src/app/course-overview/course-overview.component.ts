import { Component, OnInit, ViewChild } from '@angular/core';
import * as firebase from 'firebase';
import { MatSidenav } from '@angular/material/sidenav';

// For this component we only need to input a description, par number, and yards to hole. The scorecard is now irrelevant, admins are not
// concerned about this.
@Component({
  selector: 'app-course-overview',
  templateUrl: './course-overview.component.html',
  styleUrls: ['./course-overview.component.css']
})
export class CourseOverviewComponent implements OnInit {
  // Navbar properties
  @ViewChild('sidenav') sidenav: MatSidenav;
  isExpanded = true;
  showSubmenu = false;
  isShowing = false;
  showSubSubMenu = false;

  courseName: any;
  info: any;

  hole1 = false;
  hole2 = false;
  hole3 = false;
  hole4 = false;
  hole5 = false;
  hole6 = false;
  hole7 = false;
  hole8 = false;
  hole9 = false;
  hole10 = false;
  hole11 = false;
  hole12 = false;
  hole13 = false;
  hole14 = false;
  hole15 = false;
  hole16 = false;
  hole17 = false;
  hole18 = false;

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

  constructor() {}

  // Navbar properties
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

  ngOnInit() {
    this.initData();
  }

  // Figure out what needs to be shown in the navbar
  initData() {
    this.getCourseName()
    .then(val => {
      this.courseName = val;
      this.getCourseDetails()
      .then(data => {
        this.info = data;
        if (this.info.Hole1 != null) {
          this.hole1 = true;
        }
        if (this.info.Hole2 != null) {
          this.hole2 = true;
        }
        if (this.info.Hole3 != null) {
          this.hole3 = true;
        }
        if (this.info.Hole4 != null) {
          this.hole4 = true;
        }
        if (this.info.Hole5 != null) {
          this.hole5 = true;
        }
        if (this.info.Hole6 != null) {
          this.hole6 = true;
        }
        if (this.info.Hole7 != null) {
          this.hole7 = true;
        }
        if (this.info.Hole8 != null) {
          this.hole8 = true;
        }
        if (this.info.Hole9 != null) {
          this.hole9 = true;
        }
        if (this.info.Hole10 != null) {
          this.hole10 = true;
        }
        if (this.info.Hole11 != null) {
          this.hole11 = true;
        }
        if (this.info.Hole12 != null) {
          this.hole12 = true;
        }
        if (this.info.Hole13 != null) {
          this.hole13 = true;
        }
        if (this.info.Hole14 != null) {
          this.hole14 = true;
        }
        if (this.info.Hole15 != null) {
          this.hole15 = true;
        }
        if (this.info.Hole16 != null) {
          this.hole16 = true;
        }
        if (this.info.Hole17 != null) {
          this.hole17 = true;
        }
        if (this.info.Hole18 != null) {
          this.hole18 = true;
        }
      });
    });
  }

  // Determine the name of the course associated with the current user
  getCourseName() {
    const userId = firebase.auth().currentUser.uid;
    return firebase.database().ref('/Users/' + userId).once('value').then(function(snapshot) {
      const golfCourse = (snapshot.val() && snapshot.val().golfCourse || 'No Associated Course');
      return golfCourse;
    });
  }

  // Read the number of holes
  getCourseDetails() {
    // use the golfCourse value to match it to the GolfCourse table and get the hole info
    return firebase.database().ref('/GolfCourse/' + this.courseName + '/Holes').once('value').then(function(snapshot) {
      // All the data is being pulled here. Assign it a value then it can be shown in the front end.
      const data = snapshot.val();
      return data;
    });
  }

  // Switch to appropriate page on click
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

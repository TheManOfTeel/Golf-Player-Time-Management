import { Component, OnInit, ViewChild, HostListener } from '@angular/core';
import * as firebase from 'firebase/app';
import 'firebase/database';
import 'firebase/auth';
import { MatSidenav } from '@angular/material/sidenav';
import { BehaviorSubject } from 'rxjs/internal/BehaviorSubject';
import { Observable } from 'rxjs/internal/Observable';

@Component({
  selector: 'app-course-overview',
  templateUrl: './course-overview.component.html',
  styleUrls: ['./course-overview.component.css']
})
export class CourseOverviewComponent implements OnInit {
  // Navbar properties
  showToggle: string;
  mode: string;
  openSidenav: boolean;
  private screenWidth$ = new BehaviorSubject<number>(window.innerWidth);
  showIconMenu: boolean;

  courseName: any;
  info: any;

  // Check variable to see if there are more than 9 holes
  hole18 = false;

  // Nav content show
  showHoleWait = true;
  showHole01 = false;
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
  @ViewChild('sidenav') matSidenav: MatSidenav;

  // Get window size
  @HostListener('window:resize', ['$event'])

  onResize(event) {
    this.screenWidth$.next(event.target.innerWidth);
  }

  getScreenWidth(): Observable<number> {
    return this.screenWidth$.asObservable();
  }

  // Toggle sidenav and change the button icon
  toggleNav() {
    this.matSidenav.toggle();
    this.showIconMenu = !this.showIconMenu;
  }

  ngOnInit() {
    // Using the screen width determine whether the sidenav should be visible or not
    this.getScreenWidth().subscribe(width => {
      if (width <= 850) {
        this.showToggle = 'show';
        this.openSidenav = false;
        this.showIconMenu = true;
     }
     else if (width > 850) {
        this.showToggle = 'hide';
        this.openSidenav = true;
        this.showIconMenu = false;
     }
    });

    // See how many holes it needs to display in the navlist
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
  focusShowWait(show: boolean) {
    this.showHoleWait = show;
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
    this.showHole18 = false;
  }

  focusHole01(show: boolean) {
    this.showHoleWait = false;
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
    this.showHoleWait = false;
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
    this.showHoleWait = false;
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
    this.showHoleWait = false;
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
    this.showHoleWait = false;
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
    this.showHoleWait = false;
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
    this.showHoleWait = false;
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
    this.showHoleWait = false;
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
    this.showHoleWait = false;
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
    this.showHoleWait = false;
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
    this.showHoleWait = false;
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
    this.showHoleWait = false;
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
    this.showHoleWait = false;
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
    this.showHoleWait = false;
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
    this.showHoleWait = false;
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
    this.showHoleWait = false;
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
    this.showHoleWait = false;
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
    this.showHoleWait = false;
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

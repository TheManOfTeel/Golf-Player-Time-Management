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

}

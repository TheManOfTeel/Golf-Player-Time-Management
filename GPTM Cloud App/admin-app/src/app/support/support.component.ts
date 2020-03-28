import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-support',
  templateUrl: './support.component.html',
  styleUrls: ['./support.component.css']
})
export class SupportComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

  // Open new window with endpoint set to Google Play Store
  appDownload() {
    window.open(
      'https://play.google.com/store/apps', '_blank');
  }

  // Open new window and use current user's email to open up a blank draft email to the support account
  mailTo() {
    window.open(
      'mailto:glfmngmnt@gmail.com', '_blank');
  }

}

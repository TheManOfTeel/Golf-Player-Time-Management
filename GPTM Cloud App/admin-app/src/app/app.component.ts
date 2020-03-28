import { Component, ViewEncapsulation } from '@angular/core';
import 'firebase/firestore';



@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class AppComponent {
  title = 'admin-app';
  constructor() { }
}

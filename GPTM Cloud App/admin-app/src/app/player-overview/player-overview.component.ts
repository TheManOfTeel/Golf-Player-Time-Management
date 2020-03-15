import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { AngularFireDatabase } from '@angular/fire/database';
import 'firebase/database';
import 'firebase/firestore';
import * as firebase from 'firebase';

@Component({
  selector: 'app-player-overview',
  templateUrl: './player-overview.component.html',
  styleUrls: ['./player-overview.component.css']
})

export class PlayerOverviewComponent implements OnInit {

  constructor() {}

  ngOnInit() {}
}

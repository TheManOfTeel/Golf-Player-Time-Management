import { Component, OnInit } from '@angular/core';
import { Observable, Subscription, BehaviorSubject } from 'rxjs';
import { switchMap } from 'rxjs/operators';
import { AngularFireDatabase, AngularFireAction } from '@angular/fire/database';
import 'firebase/database';
import { AngularFirestore } from '@angular/fire/firestore';
import 'firebase/firestore';
import * as firebase from 'firebase';

@Component({
  selector: 'app-player-overview',
  templateUrl: './player-overview.component.html',
  styleUrls: ['./player-overview.component.css']
})
export class PlayerOverviewComponent implements OnInit {

courseName: any;

 items: Observable<any[]>;

 getCourseName() {
  const userId = firebase.auth().currentUser.uid;
  return firebase.database().ref('/Users/' + userId).once('value').then(function(snapshot) {
    const golfCourse = (snapshot.val() && snapshot.val().golfCourse || 'No Associated Course');
    return golfCourse;
  });
}
//items$: Observable<AngularFireAction<firebase.database.DataSnapshot>[]>;
//size$: BehaviorSubject<string|null>;

  constructor(public db: AngularFireDatabase) { 
   
   this.items = db.list('Request/Kensington Metropark Golf Course').valueChanges();

   /*
   this.items$ = this.size$.pipe(
    switchMap(size => 
      db.list('Request', ref =>
        size ? ref.orderByChild(this.getCourseName().toString()).equalTo(size) : ref
      ).snapshotChanges()
    )
  );
  */
  }

  ngOnInit() {
  

  }

  

}

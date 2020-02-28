import { Component, AfterViewInit, ViewChild, ElementRef, OnInit, NgZone } from '@angular/core';
import * as firebase from 'firebase';
import { MapsAPILoader } from '@agm/core';

@Component({
  selector: 'app-course-map',
  templateUrl: './course-map.component.html',
  styleUrls: ['./course-map.component.css']
})
export class CourseMapComponent implements OnInit {
  latitude: number;
  longitude: number;
  zoom: number;
  address: string;
  private geoCoder;
  courseName: string;

  @ViewChild('search')
  public searchElementRef: ElementRef;


  constructor(
    private mapsAPILoader: MapsAPILoader,
    private ngZone: NgZone
  ) { }

  ngOnInit() {
    // Use the current course to get lat/long
    this.getCourseName()
    .then(val => {
      this.courseName = val;
      this.getCourseData(this.courseName)
      .then(data => {
        this.mapsAPILoader.load().then(() => {
          this.geoCoder = new google.maps.Geocoder();
          this.latitude = data.latitude;
          this.longitude = data.longitude;
          this.zoom = 12;
        })
      })
    });
  }

  // Get Current Location Coordinates
  private setCurrentLocation() {
    if ('geolocation' in navigator) {
      navigator.geolocation.getCurrentPosition((position) => {
        this.latitude = position.coords.latitude;
        this.longitude = position.coords.longitude;
        this.zoom = 8;
        this.getAddress(this.latitude, this.longitude);
      });
    }
  }

  getCourseName() {
    const userId = firebase.auth().currentUser.uid;
    return firebase.database().ref('/Users/' + userId).once('value').then(function(snapshot) {
      const golfCourse = (snapshot.val() && snapshot.val().golfCourse);
      return golfCourse;
    });
  }

  getCourseData(golfCourse) {
    // use the golfCourse value to match it to the GolfCourse table and get the hole info
    return firebase.database().ref('/GolfCourse/' + this.courseName).once('value').then(function(snapshot) {
      const data = snapshot.val();
      return data;
    });
  }

  markerDragEnd($event: any) {
    console.log($event);
    this.latitude = $event.coords.lat;
    this.longitude = $event.coords.lng;
    this.getAddress(this.latitude, this.longitude);
  }

  // In case we wish to implement address/place search
  getAddress(latitude, longitude) {
    this.geoCoder.geocode({ location: { lat: latitude, lng: longitude } }, (results, status) => {
      // console.log(results);
      // console.log(status);
      if (status === 'OK') {
        if (results[0]) {
          this.zoom = 12;
          this.address = results[0].formatted_address;
        } else {
          window.alert('No results found');
        }
      } else {
        window.alert('Geocoder failed due to: ' + status);
      }
    });
  }

  // getPlayerLocation() {
  //   // Pull in uploaded coordinates
  //   return firebase.database().ref('/MyLocation/').once('value').then(function(snapshot) {
  //     // All the data is being pulled here. Assign it a value then it can be shown in the front end.
  //     const data = snapshot.val();
  //     console.log(data);
  //     return data;
  //   });
  // }

}

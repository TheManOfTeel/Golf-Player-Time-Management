import { Component, OnInit, Inject } from '@angular/core';
import * as firebase from 'firebase';
import { MapsAPILoader } from '@agm/core';
import { FormBuilder } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

declare const google: any;
let myPolygon;
let coordinateData = [];

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
  courseName: string;

  map: any;
  holePoly: any;

  // Google maps action controls
  zoomControl: boolean;
  mapTypeControl: boolean;
  scaleControl: boolean;
  streetViewControl: boolean;
  rotateControl: boolean;
  fullscreenControl: boolean;
  mapTypeId: string;
  drawingManager: google.maps.drawing.DrawingManager;

  constructor(
    private mapsAPILoader: MapsAPILoader,
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<CourseMapComponent>,
    @Inject(MAT_DIALOG_DATA) data
  ) {
    coordinateData = data.coordinateData;
  }

  ngOnInit() {
    // Use the current course to get lat/long
    this.getCourseName()
    .then(val => {
      this.courseName = val;
      this.getCourseData()
      .then(data => {
        this.map = this.mapsAPILoader.load().then(() => {
          this.latitude = data.latitude;
          this.longitude = data.longitude;
          this.zoom = 14;
          this.fullscreenControl = true;
          this.mapTypeId = 'hybrid';
        });
      });
    });
  }

  getCourseName() {
    const userId = firebase.auth().currentUser.uid;
    return firebase.database().ref('/Users/' + userId).once('value').then(function(snapshot) {
      const golfCourse = (snapshot.val() && snapshot.val().golfCourse);
      return golfCourse;
    });
  }

  getCourseData() {
    // use the golfCourse value to match it to the GolfCourse table and get the hole info
    return firebase.database().ref('/GolfCourse/' + this.courseName).once('value').then(function(snapshot) {
      const data = snapshot.val();
      return data;
    });
  }

  onMapReady(map) {
    this.initDrawingManager(map);
  }

  initDrawingManager(map: any) {
    const options = {
      drawingControl: true,
      drawingControlOptions: {
      drawingModes: ['polygon']
      },
      polygonOptions: {
        draggable: false,
        editable: false
      },
      drawingMode: google.maps.drawing.OverlayType.POLYGON
    };

    const drawingManager = new google.maps.drawing.DrawingManager(options);
    drawingManager.setMap(map);
    google.maps.event.addListener(drawingManager, 'polygoncomplete', function(polygon) {
      myPolygon = polygon;
      this.holePoly = polygon.getPath().getArray();
      const path = polygon.getPath();
      const coordinates = [];

      for (let i = 0 ; i < path.length ; i++) {
        coordinates.push({
          lat: path.getAt(i).lat(),
          lng: path.getAt(i).lng()
        });
      }
      coordinateData = coordinates;

      // To disable after 1 shape is drawn
      drawingManager.setDrawingMode(null);

      // To hide controls
      // drawingManager.setOptions({
      //   drawingControl: false,
      // });
    });
  }

  savePoly() {
    console.log(coordinateData);
    this.dialogRef.close(coordinateData);
  }

  close() {
    this.dialogRef.close();
  }

  resetMap() {
    myPolygon.setMap(null);
  }

  // Marker action, will probably set to disbale but I'll keep this around just in case
  // markerDragEnd($event: any) {
  //   console.log($event);
  //   this.latitude = $event.coords.lat;
  //   this.longitude = $event.coords.lng;
  //   this.getAddress(this.latitude, this.longitude);
  // }

  // For the marker
  // getAddress(latitude, longitude) {
  //   this.geoCoder.geocode({ location: { lat: latitude, lng: longitude } }, (results, status) => {
  //     // console.log(results);
  //     // console.log(status);
  //     if (status === 'OK') {
  //       if (results[0]) {
  //         this.zoom = 12;
  //         this.address = results[0].formatted_address;
  //       } else {
  //         window.alert('No results found');
  //       }
  //     } else {
  //       window.alert('Geocoder failed due to: ' + status);
  //     }
  //   });
  // }

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

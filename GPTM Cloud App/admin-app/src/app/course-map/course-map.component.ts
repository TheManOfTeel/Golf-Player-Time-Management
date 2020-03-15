import { Component, OnInit, Inject } from '@angular/core';
import * as firebase from 'firebase';
import { MapsAPILoader } from '@agm/core';
import { FormBuilder } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

declare const google: any;
let myPolygon;
let coordinateData = [];
let markerData = [];
let geoFence = [];

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
  location: Location;

  map: any;

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
    geoFence = data.geoFence;
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
          this.zoom = 15;
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
        editable: false,
        clickable: false,
        fillColor: 'green',
        strokeColor: 'yellow'
      },
      drawingMode: google.maps.drawing.OverlayType.POLYGON
    };

    const drawingManager = new google.maps.drawing.DrawingManager(options);
    drawingManager.setDrawingMode(null);

    // Place Marker
    let marker;
    google.maps.event.addListener(map, 'click', function(event) {
      placeMarker(event.latLng);
      const lat = marker.getPosition().lat();
      const lng = marker.getPosition().lng();
      const holeLocation = [];
      /* tslint:disable:object-literal-shorthand */
      holeLocation.push({
        lat: lat,
        lng: lng
      });
      markerData = holeLocation[0];
    });

    // Marker image
    const image = 'https://img.icons8.com/color/48/000000/map-pin.png';
    function placeMarker(location) {
      if (marker == null) {
        marker = new google.maps.Marker({
            position: location,
            map: map,
            animation: google.maps.Animation.DROP,
            icon: image
        });
      }
      /* tslint:enable:object-literal-shorthand */
      if (marker != null) {
        marker.setPosition(location);
      }
    }

    // Marker drop animation
    function toggleBounce() {
      if (marker.getAnimation() !== null) {
        marker.setAnimation(null);
      } else {
        marker.setAnimation(google.maps.Animation.BOUNCE);
      }
    }

    // Draw Polygon
    drawingManager.setMap(map);
    google.maps.event.addListener(drawingManager, 'polygoncomplete', function(polygon) {
      myPolygon = polygon;
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
      drawingManager.setOptions({
        drawingControl: false,
      });
    });

    google.maps.event.addDomListener(document.getElementById('delete-button'), 'click', function() {
      // Bring back controls to allow the user to try again
      drawingManager.setOptions({
        drawingControl: true,
      });
    });
  }

  savePoly() {
    const geoFence = [];
    geoFence.push({
      Hole: markerData,
      CourseOutline: coordinateData
    });
    this.dialogRef.close(geoFence);
  }

  close() {
    this.dialogRef.close();
  }

  resetMap() {
    myPolygon.setMap(null);
  }
}

import { Component, AfterViewInit, ViewChild, ElementRef } from '@angular/core';
import * as firebase from 'firebase';

@Component({
  selector: 'app-course-map',
  templateUrl: './course-map.component.html',
  styleUrls: ['./course-map.component.css']
})
export class CourseMapComponent implements AfterViewInit {

  @ViewChild('mapContainer', {static: false}) gmap: ElementRef;
  map: google.maps.Map;
  lat = 37.4219983;
  lng = -122.0840567;
  playerCoordinates: any;

  coordinates = new google.maps.LatLng(this.lat, this.lng);

  mapOptions: google.maps.MapOptions = {
    center: this.coordinates,
    zoom: 8,
  };

  marker = new google.maps.Marker({
    position: this.coordinates,
    map: this.map,
  });

  constructor() { }

  ngAfterViewInit() {
    this.getPlayerLocation();
    this.mapInitializer();
    // this.initMap();
  }

  mapInitializer() {
    this.getPlayerLocation()
    .then(res => {
      this.playerCoordinates = res;
      console.log(this.playerCoordinates);
    });
    this.map = new google.maps.Map(this.gmap.nativeElement,
    this.mapOptions);
    this.marker.setMap(this.map);
  }

  getPlayerLocation() {
    // Pull in uploaded coordinates
    return firebase.database().ref('/MyLocation/').once('value').then(function(snapshot) {
      // All the data is being pulled here. Assign it a value then it can be shown in the front end.
      const data = snapshot.val();
      // console.log(data.You.l);
      return data;
    });
  }

  // map: google.maps.Map<HTMLElement>;
  // infoWindow: google.maps.InfoWindow;

  // initMap() {
  //   this.map = new google.maps.Map(document.getElementById('map'), {
  //     center: {lat: -34.397, lng: 150.644},
  //     zoom: 6
  //   });
  //   this.infoWindow = new google.maps.InfoWindow;

  //   // Try HTML5 geolocation.
  //   if (navigator.geolocation) {
  //     navigator.geolocation.getCurrentPosition(function(position) {
  //       var pos = {
  //         lat: position.coords.latitude,
  //         lng: position.coords.longitude
  //       };

  //       this.infoWindow.setPosition(pos);
  //       this.infoWindow.setContent('Location found.');
  //       this.infoWindow.open(this.map);
  //       this.map.setCenter(pos);
  //     }, function() {
  //       this.handleLocationError(true, this.infoWindow, this.map.getCenter());
  //     });
  //   } else {
  //     // Browser doesn't support Geolocation
  //     this.handleLocationError(false, this.infoWindow, this.map.getCenter());
  //   }
  // }

  // handleLocationError(browserHasGeolocation: boolean,
  // infoWindow: { setPosition: (arg0: any) => void; setContent: (arg0: string) => void; open: (arg0: any) => void; }, pos: any) {
  //   infoWindow.setPosition(pos);
  //   infoWindow.setContent(browserHasGeolocation ?
  //                         'Error: The Geolocation service failed.' :
  //                         'Error: Your browser doesn\'t support geolocation.');
  //   infoWindow.open(this.map);
  // }

}

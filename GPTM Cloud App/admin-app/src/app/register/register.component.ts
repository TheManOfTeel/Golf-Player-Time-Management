import { Component, ViewChild, ElementRef, NgZone, OnInit } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { Router, Params } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import * as firebase from 'firebase';
import { MapsAPILoader } from '@agm/core';

interface numberOfHoles {
  value: number;
  viewValue: number;
}

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  registerForm: FormGroup;
  courseSelectForm: FormGroup;
  errorMessage = '';
  successMessage = '';
  golfCourse = '';
  userId: any;
  email: '';
  password: '';
  password2: '';
  course: any;
  alreadyExists: string;
  latitude: number;
  longitude: number;
  zoom: number;
  address: string;
  private geoCoder;
  componentForm = {
    golfCourse: 'short_name',
  };
  i: number;
  selectedNumber: number;
  numbers: numberOfHoles[] = [
    {value: 1, viewValue: 1},
    {value: 2, viewValue: 2},
    {value: 3, viewValue: 3},
    {value: 4, viewValue: 4},
    {value: 5, viewValue: 5},
    {value: 6, viewValue: 6},
    {value: 7, viewValue: 7},
    {value: 8, viewValue: 8},
    {value: 9, viewValue: 9},
    {value: 10, viewValue: 10},
    {value: 11, viewValue: 11},
    {value: 12, viewValue: 12},
    {value: 13, viewValue: 13},
    {value: 14, viewValue: 14},
    {value: 15, viewValue: 15},
    {value: 16, viewValue: 16},
    {value: 17, viewValue: 17},
    {value: 18, viewValue: 18},
  ];
  placeSearch: any;
  autocomplete: any;

  @ViewChild('search')
  public searchElementRef: ElementRef;

  constructor(
    public authService: AuthService,
    private router: Router,
    private fb: FormBuilder,
    private mapsAPILoader: MapsAPILoader,
    private ngZone: NgZone
  ) {
    this.createForm();
  }

  createForm() {
    this.courseSelectForm = this.fb.group({
      golfCourse: ['', Validators.required],
      numberOfHoles: ['', Validators.required]
    }),
    this.registerForm = this.fb.group({
     email: ['', Validators.email],
     password: ['', Validators.minLength(6)],
     password2: ['', Validators.minLength(6)]
     });
  }

  tryGoogleLogin() {
    this.authService.doGoogleLogin()
    .then( res => {
      this.router.navigate(['/dashboard']);
    }, err => console.log(err)
    );
  }

  tryRegister(value) {
    this.checkIfExists(this.golfCourse)
    .then(data => {
      this.course = data;
      this.validateCourse(data, value);
    });
  }

  writeHoleData(golfCourse) {
    firebase.database().ref('GolfCourse/' + this.golfCourse).set({
      golfCourse: this.golfCourse,
    });
    for(this.i = 1; this.i <= this.selectedNumber; this.i++) {
      firebase.database().ref('GolfCourse/' + this.golfCourse + 'Holes' + '/Hole' + this.i).set({
        Description: 'No description set',
        Tips: 'No tips set',
      });
      firebase.database().ref('GolfCourse/' + this.golfCourse + 'Holes' + '/Hole' + this.i + 'Red_Circle').set({
        Description: "Men's professional tee.",
        Tips: 'No description set',
        Yards: 'No distance set',
        Par: 'No par set',
      });
      firebase.database().ref('GolfCourse/' + this.golfCourse + 'Holes' + '/Hole' + this.i + 'Blue_Square').set({
        Description: "Men's average tee.",
        Tips: 'No description set',
        Yards: 'No distance set',
        Par: 'No par set',
      });
      firebase.database().ref('GolfCourse/' + this.golfCourse + 'Holes' + '/Hole' + this.i + 'Yellow_Triangle').set({
        Description: "Women's professional tee.",
        Tips: 'No description set',
        Yards: 'No distance set',
        Par: 'No par set',
      });
      firebase.database().ref('GolfCourse/' + this.golfCourse + 'Holes' + '/Hole' + this.i + 'Pink_Diamond').set({
        Description: "Women's average tee.",
        Tips: 'No description set',
        Yards: 'No distance set',
        Par: 'No par set',
      });
    };
 }

  checkIfExists(golfCourse) {
  return firebase.database().ref('GolfCourse/' + this.golfCourse).once('value').then(function(snapshot) {
    const courseName = snapshot.val();
    return courseName;
    });
  }

  validateCourse(course, value) {
    if ((this.course === null) && (this.password = this.password2)) {
      this.authService.doRegister(value)
      .then(res => {
        this.errorMessage = null;
        this.alreadyExists = null;
        this.successMessage = 'Your account has been created';
        this.writeHoleData(this.golfCourse);
        firebase.database().ref('/Users/' + res.user.uid).set({
          email: res.user.email,
          password: this.password,
          isAdmin: true,
          golfCourse: this.golfCourse
        });
      }, err => {
          console.log(err);
          this.alreadyExists = null;
          this.errorMessage = err.message;
          this.successMessage = '';
        });
    }
    if (this.course != null) {
      this.alreadyExists = 'Already Exists';
      this.errorMessage = null;
    }
  }

  ngOnInit() {
    //load Places Autocomplete
    this.mapsAPILoader.load().then(() => {
      this.setCurrentLocation();
      this.geoCoder = new google.maps.Geocoder;
 
      let autocomplete = new google.maps.places.Autocomplete(this.searchElementRef.nativeElement, {
        types: ["establishment"]
      });
      autocomplete.addListener("place_changed", () => {
        this.ngZone.run(() => {
          //get the place result
          let place: google.maps.places.PlaceResult = autocomplete.getPlace();

          for (var component in this.componentForm) {
            (<HTMLInputElement>document.getElementById(component)).value = '';
            (<HTMLInputElement>document.getElementById(component)).disabled = false;
          }
          (<HTMLInputElement>document.getElementById("golfCourse")).value = place.name;
          console.log(place.name);
          (<HTMLInputElement>document.getElementById("golfCourse")).disabled = false;
          // Get each component of the address from the place details
          // and fill the corresponding field on the form.
          for (var i = 0; i < place.address_components.length; i++) {
            var addressType = place.address_components[i].types[0];
            if (this.componentForm[addressType]) {
              var val = place.address_components[i][this.componentForm[addressType]];
              (<HTMLInputElement>document.getElementById(addressType)).value = val;
            }
          }
 
          //verify result
          if (place.geometry === undefined || place.geometry === null) {
            return;
          }
 
          //set latitude, longitude and zoom
          this.latitude = place.geometry.location.lat();
          this.longitude = place.geometry.location.lng();
          this.zoom = 12;
        });
      });
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
 
  // In case we wish to implement address/place search
  getAddress(latitude, longitude) {
    this.geoCoder.geocode({ 'location': { lat: latitude, lng: longitude } }, (results, status) => {
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
}

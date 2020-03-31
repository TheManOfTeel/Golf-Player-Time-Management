import { Component, ViewChild, ElementRef, NgZone, OnInit } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import * as firebase from 'firebase/app';
import 'firebase/database';
import 'firebase/auth';
import { MapsAPILoader } from '@agm/core';


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
  geoLocation: any;
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

  // For the radio buttons
  holes: number[] = [9, 18];

  // For the dropdown list
  // numbers: NumberOfHoles[] = [
    // {value: 1, viewValue: 1},
    // {value: 2, viewValue: 2},
    // {value: 3, viewValue: 3},
    // {value: 4, viewValue: 4},
    // {value: 5, viewValue: 5},
    // {value: 6, viewValue: 6},
    // {value: 7, viewValue: 7},
    // {value: 8, viewValue: 8},
    // {value: 9, viewValue: 9},
    // {value: 10, viewValue: 10},
    // {value: 11, viewValue: 11},
    // {value: 12, viewValue: 12},
    // {value: 13, viewValue: 13},
    // {value: 14, viewValue: 14},
    // {value: 15, viewValue: 15},
    // {value: 16, viewValue: 16},
    // {value: 17, viewValue: 17},
  //   {value: 18, viewValue: 18},
  // ];
  placeSearch: any;
  autocomplete: any;
  verified: boolean;
  courseData: any;
  valid = '';
  invalid = '';
  noMatch = '';
  notACourse = '';
  isLoading = false;
  hide1 = true;
  hide2 = true;

  @ViewChild('search')
  public searchElementRef: ElementRef;

  constructor(
    public authService: AuthService,
    private fb: FormBuilder,
    private mapsAPILoader: MapsAPILoader,
    private ngZone: NgZone
  ) {
    this.createForm();
  }

  // Set validators
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

  ngOnInit() {
    // Load Places Autocomplete
    this.mapsAPILoader.load().then(() => {
      this.setCurrentLocation();
      this.geoCoder = new google.maps.Geocoder();

      const autocomplete = new google.maps.places.Autocomplete(this.searchElementRef.nativeElement, {
        types: ['establishment'],
      });
      autocomplete.addListener('place_changed', () => {
        this.ngZone.run(() => {
          // Get the place result
          const place: google.maps.places.PlaceResult = autocomplete.getPlace();
          if (place != null) {
            /* tslint:disable:forin */
            for (const component in this.componentForm) {
              (document.getElementById(component) as HTMLInputElement).value = '';
              (document.getElementById(component) as HTMLInputElement).disabled = false;
            }
            /*tslint:enable:forin */
          }
          (document.getElementById('golfCourse') as HTMLInputElement).value = place.name;
          (document.getElementById('golfCourse') as HTMLInputElement).disabled = false;
          this.course = place.name;

          // Get each component of the address from the place details
          // and fill the corresponding field on the form.
          /* tslint:disable:prefer-for-of */
          for (let i = 0; i < place.address_components.length; i++) {
            let golfCourse = place.address_components[i].types[0];
            if (this.componentForm[golfCourse] && this.componentForm[golfCourse]) {
              golfCourse = place.address_components[i][this.componentForm[golfCourse]];
            }
          }
          /* tslint:enable:prefer-for-of */

          // Verify result
          if (place.geometry === undefined || place.geometry === null) {
            return;
          }

          // Set latitude, longitude and zoom
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

  getAddress(latitude, longitude) {
    this.geoCoder.geocode({ location: { lat: latitude, lng: longitude } }, (results, status) => {
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

  // In case Google sign in is decided to be included
  // tryGoogleLogin() {
  //   this.authService.doGoogleLogin()
  //   .then( () => {
  //     this.router.navigate(['/dashboard']);
  //   }, err => console.log(err)
  //   );
  // }

  // Use firebase register mixed with custom parameter checks
  tryRegister(value) {
    this.isLoading = true;
    this.checkIfExists()
    .then(data => {
      this.courseData = data;
      this.validateCourse(value);
    });
  }

 // See if the course is in use already
  checkIfExists() {
  return firebase.database().ref('GolfCourse/' + this.course).once('value').then(function(snapshot) {
    const courseName = snapshot.val();
    return courseName;
    });
  }

  // Generate error messages
  validateCourse(value) {
    if (this.course.includes('Golf') && (this.courseData === null) && (this.password === this.password2)) {
      this.authService.doRegister(value)
      .then(res => {
        this.errorMessage = null;
        this.alreadyExists = null;
        this.noMatch = null;
        this.successMessage = 'Verification email has been sent';
        firebase.database().ref('/Users/' + res.user.uid).set({
          email: res.user.email,
          password: this.password,
          isAdmin: true,
          golfCourse: this.course,
          numberOfHoles: this.selectedNumber,
          lat: this.latitude,
          long: this.longitude
        });
        this.isLoading = false;
      }, err => {
          this.alreadyExists = null;
          this.noMatch = null;
          this.errorMessage = err.message;
          this.successMessage = null;
          this.isLoading = false;
        });
    }
    /* tslint:disable:quotemark */
    if (this.password !== this.password2) {
      this.noMatch = "Passwords don't match";
      this.errorMessage = null;
      this.alreadyExists = null;
      this.invalid = null;
      this.isLoading = false;
    }
    if (this.courseData != null) {
      this.alreadyExists = 'Course is already registered';
      this.errorMessage = null;
      this.noMatch = null;
      this.invalid = null;
      this.isLoading = false;
    }
    if (!this.course.includes('Golf')) {
      this.notACourse = 'Not a golf course';
      this.alreadyExists = null;
      this.errorMessage = null;
      this.noMatch = null;
      this.isLoading = false;
    }
    if (this.courseData != null && !this.course.includes('Golf')) {
      this.alreadyExists = 'Course is already registered';
      this.noMatch = null;
      this.notACourse = 'Not a golf course';
      this.errorMessage = null;
      this.isLoading = false;
    }
    if (this.courseData != null && this.password !== this.password2) {
      this.alreadyExists = 'Course is already registered';
      this.noMatch = "Passwords don't match";
      this.invalid = null;
      this.errorMessage = null;
      this.isLoading = false;
    }
    if (!this.course.includes('Golf') && this.password !== this.password2) {
      this.alreadyExists = null;
      this.noMatch = "Passwords don't match";
      this.notACourse = 'Not a golf course';
      this.errorMessage = null;
      this.isLoading = false;
    }
    if (this.courseData != null && !this.course.includes('Golf') && this.password !== this.password2) {
      this.alreadyExists = 'Course is already registered';
      this.noMatch = "Passwords don't match";
      this.notACourse = 'Not a golf course';
      this.errorMessage = null;
      this.isLoading = false;
    }
    /* tslint:enable:quotemark */
  }

  // Try to limit to only golf courses since places autocomplete has no easy way of doing this
  verifyGolfCourse() {
    if (this.course != null && this.course.includes('Golf')) {
      this.checkIfExists()
      .then(data => {
        this.courseData = data;
        if (this.courseData === null) {
          this.valid = 'Course not taken';
          this.invalid = null;
          this.notACourse = null;
        }
        if (this.courseData != null) {
          this.valid = null;
          this.invalid = 'Course taken';
          this.notACourse = null;
        }
      });
    }
    if (this.course === '' || !this.course.includes('Golf')) {
      this.valid = null;
      this.invalid = null;
      this.notACourse = 'Not a golf course';
    }
  }
}

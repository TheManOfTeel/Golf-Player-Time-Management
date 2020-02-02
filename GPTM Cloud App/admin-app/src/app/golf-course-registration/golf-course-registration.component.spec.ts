import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GolfCourseRegistrationComponent } from './golf-course-registration.component';

describe('GolfCourseRegistrationComponent', () => {
  let component: GolfCourseRegistrationComponent;
  let fixture: ComponentFixture<GolfCourseRegistrationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GolfCourseRegistrationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GolfCourseRegistrationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WaitTimeComponent } from './wait-time.component';

describe('WaitTimeComponent', () => {
  let component: WaitTimeComponent;
  let fixture: ComponentFixture<WaitTimeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WaitTimeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WaitTimeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Hole11Component } from './hole11.component';

describe('Hole11Component', () => {
  let component: Hole11Component;
  let fixture: ComponentFixture<Hole11Component>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Hole11Component ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Hole11Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

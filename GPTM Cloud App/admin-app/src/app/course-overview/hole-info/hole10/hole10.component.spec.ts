import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Hole10Component } from './hole10.component';

describe('Hole10Component', () => {
  let component: Hole10Component;
  let fixture: ComponentFixture<Hole10Component>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Hole10Component ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Hole10Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Hole04Component } from './hole04.component';

describe('Hole04Component', () => {
  let component: Hole04Component;
  let fixture: ComponentFixture<Hole04Component>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Hole04Component ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Hole04Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

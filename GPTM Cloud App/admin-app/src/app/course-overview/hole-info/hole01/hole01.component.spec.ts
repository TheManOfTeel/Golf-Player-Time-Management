import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Hole01Component } from './hole01.component';

describe('Hole01Component', () => {
  let component: Hole01Component;
  let fixture: ComponentFixture<Hole01Component>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Hole01Component ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Hole01Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

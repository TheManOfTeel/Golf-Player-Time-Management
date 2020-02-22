import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Hole07Component } from './hole07.component';

describe('Hole07Component', () => {
  let component: Hole07Component;
  let fixture: ComponentFixture<Hole07Component>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Hole07Component ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Hole07Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

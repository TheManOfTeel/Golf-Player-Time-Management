import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Hole14Component } from './hole14.component';

describe('Hole14Component', () => {
  let component: Hole14Component;
  let fixture: ComponentFixture<Hole14Component>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Hole14Component ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Hole14Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

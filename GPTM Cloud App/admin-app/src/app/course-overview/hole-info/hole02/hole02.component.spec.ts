import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Hole02Component } from './hole02.component';

describe('Hole02Component', () => {
  let component: Hole02Component;
  let fixture: ComponentFixture<Hole02Component>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Hole02Component ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Hole02Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

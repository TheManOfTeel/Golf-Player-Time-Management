import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Hole09Component } from './hole09.component';

describe('Hole09Component', () => {
  let component: Hole09Component;
  let fixture: ComponentFixture<Hole09Component>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Hole09Component ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Hole09Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

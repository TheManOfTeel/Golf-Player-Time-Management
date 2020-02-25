import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Hole12Component } from './hole12.component';

describe('Hole12Component', () => {
  let component: Hole12Component;
  let fixture: ComponentFixture<Hole12Component>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Hole12Component ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Hole12Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

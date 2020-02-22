import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Hole03Component } from './hole03.component';

describe('Hole03Component', () => {
  let component: Hole03Component;
  let fixture: ComponentFixture<Hole03Component>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Hole03Component ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Hole03Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

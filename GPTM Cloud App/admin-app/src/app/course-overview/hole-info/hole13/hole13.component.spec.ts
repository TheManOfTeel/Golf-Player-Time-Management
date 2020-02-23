import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Hole13Component } from './hole13.component';

describe('Hole13Component', () => {
  let component: Hole13Component;
  let fixture: ComponentFixture<Hole13Component>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Hole13Component ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Hole13Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

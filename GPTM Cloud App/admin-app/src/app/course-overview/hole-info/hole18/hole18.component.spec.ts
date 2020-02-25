import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Hole18Component } from './hole18.component';

describe('Hole18Component', () => {
  let component: Hole18Component;
  let fixture: ComponentFixture<Hole18Component>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Hole18Component ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Hole18Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

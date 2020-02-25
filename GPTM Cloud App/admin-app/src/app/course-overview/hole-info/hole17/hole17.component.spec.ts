import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Hole17Component } from './hole17.component';

describe('Hole17Component', () => {
  let component: Hole17Component;
  let fixture: ComponentFixture<Hole17Component>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Hole17Component ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Hole17Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

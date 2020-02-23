import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Hole16Component } from './hole16.component';

describe('Hole16Component', () => {
  let component: Hole16Component;
  let fixture: ComponentFixture<Hole16Component>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Hole16Component ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Hole16Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

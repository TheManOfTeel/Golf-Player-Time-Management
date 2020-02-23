import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Hole15Component } from './hole15.component';

describe('Hole15Component', () => {
  let component: Hole15Component;
  let fixture: ComponentFixture<Hole15Component>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Hole15Component ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Hole15Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

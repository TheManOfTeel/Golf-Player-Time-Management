import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Hole05Component } from './hole05.component';

describe('Hole05Component', () => {
  let component: Hole05Component;
  let fixture: ComponentFixture<Hole05Component>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Hole05Component ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Hole05Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

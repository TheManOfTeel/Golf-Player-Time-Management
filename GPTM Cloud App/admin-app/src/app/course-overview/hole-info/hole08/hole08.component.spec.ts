import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Hole08Component } from './hole08.component';

describe('Hole08Component', () => {
  let component: Hole08Component;
  let fixture: ComponentFixture<Hole08Component>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Hole08Component ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Hole08Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

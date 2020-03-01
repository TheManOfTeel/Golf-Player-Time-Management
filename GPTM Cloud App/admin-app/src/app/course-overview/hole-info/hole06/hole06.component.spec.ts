import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Hole06Component } from './hole06.component';

describe('Hole06Component', () => {
  let component: Hole06Component;
  let fixture: ComponentFixture<Hole06Component>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Hole06Component ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Hole06Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

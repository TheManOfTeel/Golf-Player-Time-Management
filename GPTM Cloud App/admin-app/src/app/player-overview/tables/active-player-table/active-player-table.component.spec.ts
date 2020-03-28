import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ActivePlayerTableComponent } from './active-player-table.component';

describe('ActivePlayerTableComponent', () => {
  let component: ActivePlayerTableComponent;
  let fixture: ComponentFixture<ActivePlayerTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ActivePlayerTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ActivePlayerTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

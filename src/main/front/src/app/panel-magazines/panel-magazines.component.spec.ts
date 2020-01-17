import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {PanelMagazinesComponent} from './panel-magazines.component';

describe('PanelMagazinesComponent', () => {
  let component: PanelMagazinesComponent;
  let fixture: ComponentFixture<PanelMagazinesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [PanelMagazinesComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PanelMagazinesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

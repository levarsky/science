import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {AdminPanelMagazineComponent} from './admin-panel-magazine.component';

describe('AdminPanelMagazineComponent', () => {
  let component: AdminPanelMagazineComponent;
  let fixture: ComponentFixture<AdminPanelMagazineComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [AdminPanelMagazineComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminPanelMagazineComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

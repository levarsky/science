import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {MagazineUsersComponent} from './magazine-users.component';

describe('MagazineUsersComponent', () => {
  let component: MagazineUsersComponent;
  let fixture: ComponentFixture<MagazineUsersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [MagazineUsersComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MagazineUsersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminUpdateManagerComponent } from './admin-update-manager.component';

describe('AdminUpdateManagerComponent', () => {
  let component: AdminUpdateManagerComponent;
  let fixture: ComponentFixture<AdminUpdateManagerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminUpdateManagerComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AdminUpdateManagerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

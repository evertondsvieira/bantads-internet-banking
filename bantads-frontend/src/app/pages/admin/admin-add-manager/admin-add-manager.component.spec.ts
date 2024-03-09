import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminAddManagerComponent } from './admin-add-manager.component';

describe('AdminAddManagerComponent', () => {
  let component: AdminAddManagerComponent;
  let fixture: ComponentFixture<AdminAddManagerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminAddManagerComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AdminAddManagerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminRemoveManagerComponent } from './admin-remove-manager.component';

describe('AdminRemoveManagerComponent', () => {
  let component: AdminRemoveManagerComponent;
  let fixture: ComponentFixture<AdminRemoveManagerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminRemoveManagerComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AdminRemoveManagerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

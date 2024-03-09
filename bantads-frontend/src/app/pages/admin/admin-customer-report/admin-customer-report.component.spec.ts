import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminCustomerReportComponent } from './admin-customer-report.component';

describe('AdminCustomerReportComponent', () => {
  let component: AdminCustomerReportComponent;
  let fixture: ComponentFixture<AdminCustomerReportComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminCustomerReportComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AdminCustomerReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

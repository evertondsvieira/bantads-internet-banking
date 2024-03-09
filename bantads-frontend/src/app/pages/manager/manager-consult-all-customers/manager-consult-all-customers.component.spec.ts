import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManagerConsultAllCustomersComponent } from './manager-consult-all-customers.component';

describe('ManagerConsultAllCustomersComponent', () => {
  let component: ManagerConsultAllCustomersComponent;
  let fixture: ComponentFixture<ManagerConsultAllCustomersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ManagerConsultAllCustomersComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ManagerConsultAllCustomersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

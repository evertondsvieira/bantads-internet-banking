import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManagerConsultCustomersComponent } from './manager-consult-customers.component';

describe('ManagerConsultCustomersComponent', () => {
  let component: ManagerConsultCustomersComponent;
  let fixture: ComponentFixture<ManagerConsultCustomersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ManagerConsultCustomersComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ManagerConsultCustomersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManagerConsultTopComponent } from './manager-consult-top.component';

describe('ManagerConsultTopComponent', () => {
  let component: ManagerConsultTopComponent;
  let fixture: ComponentFixture<ManagerConsultTopComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ManagerConsultTopComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ManagerConsultTopComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

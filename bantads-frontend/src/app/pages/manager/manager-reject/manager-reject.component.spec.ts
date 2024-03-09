import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManagerRejectComponent } from './manager-reject.component';

describe('ManagerRejectComponent', () => {
  let component: ManagerRejectComponent;
  let fixture: ComponentFixture<ManagerRejectComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ManagerRejectComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ManagerRejectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

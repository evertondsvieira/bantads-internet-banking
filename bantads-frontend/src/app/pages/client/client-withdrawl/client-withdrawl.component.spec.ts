import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClientWithdrawlComponent } from './client-withdrawl.component';

describe('ClientWithdrawlComponent', () => {
  let component: ClientWithdrawlComponent;
  let fixture: ComponentFixture<ClientWithdrawlComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ClientWithdrawlComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ClientWithdrawlComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

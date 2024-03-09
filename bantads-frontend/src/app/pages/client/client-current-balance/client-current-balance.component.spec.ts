import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClientCurrentBalanceComponent } from './client-current-balance.component';

describe('ClientCurrentBalanceComponent', () => {
  let component: ClientCurrentBalanceComponent;
  let fixture: ComponentFixture<ClientCurrentBalanceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ClientCurrentBalanceComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ClientCurrentBalanceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

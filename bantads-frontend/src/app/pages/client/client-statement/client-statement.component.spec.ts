import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClientStatementComponent } from './client-statement.component';

describe('ClientStatementComponent', () => {
  let component: ClientStatementComponent;
  let fixture: ComponentFixture<ClientStatementComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ClientStatementComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ClientStatementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

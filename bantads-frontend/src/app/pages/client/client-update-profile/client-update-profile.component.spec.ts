import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClientUpdateProfileComponent } from './client-update-profile.component';

describe('ClientUpdateProfileComponent', () => {
  let component: ClientUpdateProfileComponent;
  let fixture: ComponentFixture<ClientUpdateProfileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ClientUpdateProfileComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ClientUpdateProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

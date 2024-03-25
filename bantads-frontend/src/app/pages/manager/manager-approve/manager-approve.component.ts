import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-manager-approve',
  templateUrl: './manager-approve.component.html',
  styleUrl: './manager-approve.component.css'
})
export class ManagerApproveComponent {
  constructor(public activeModal: NgbActiveModal) {
    
  }
}

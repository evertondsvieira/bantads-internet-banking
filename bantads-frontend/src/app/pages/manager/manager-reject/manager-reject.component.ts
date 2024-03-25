import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
@Component({
  selector: 'app-manager-reject',
  templateUrl: './manager-reject.component.html',
  styleUrl: './manager-reject.component.css'
})
export class ManagerRejectComponent {
  constructor(public activeModal: NgbActiveModal) {
  }
}

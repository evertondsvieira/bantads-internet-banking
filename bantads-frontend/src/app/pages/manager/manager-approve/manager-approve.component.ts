import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Client } from '../../../models/client.model';
import { ClientService } from '../../../services/client/client.service';
import { Situation } from '../../../models/enum/situation.enum';

@Component({
  selector: 'app-manager-approve',
  templateUrl: './manager-approve.component.html',
  styleUrl: './manager-approve.component.css'
})
export class ManagerApproveComponent implements OnInit {
  constructor(public activeModal: NgbActiveModal,
             private clientService: ClientService) {
    
  }
  @Input()
  public client!: Client;
  
  ngOnInit(): void {
  }
  
  approveClient(client: Client): void{
    client.situation = Situation.APPROVED;
    this.clientService.updateClient(client).subscribe( client =>
      this.activeModal.dismiss(client.situation == Situation.APPROVED)
    )
  }
}

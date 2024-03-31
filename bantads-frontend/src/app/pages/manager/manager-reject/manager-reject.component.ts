import { Component, Input, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Client } from '../../../models/client.model';
import { ClientService } from '../../../services/client/client.service';
import { Situation } from '../../../models/enum/situation.enum';
@Component({
  selector: 'app-manager-reject',
  templateUrl: './manager-reject.component.html',
  styleUrl: './manager-reject.component.css'
})
export class ManagerRejectComponent implements OnInit{
  constructor(
    public activeModal: NgbActiveModal,
    public clientService: ClientService
    ) {
  }

  @Input()
  public client!: Client;

  ngOnInit(): void {
  }

  rejectClient(client: Client): void{
    client.situation = Situation.REJECTED;
    this.clientService.updateClient(client).subscribe((client) =>
      this.activeModal.dismiss(client.situation == Situation.REJECTED))
  }
}

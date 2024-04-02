import { Component } from '@angular/core';
import { Client } from '../../../models/client.model';
import { Transaction } from '../../../models/transaction.model';
import { ClientService } from '../../../services/client/client.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ModalTransactionComponent } from '../../../components/modal/modal-transaction/modal-transaction.component';

@Component({
  selector: 'app-client-transfer',
  templateUrl: './client-transfer.component.html',
  styleUrl: './client-transfer.component.css'
})
export class ClientTransferComponent {
  accountCPF!: string;
  clientData!: Client;
  clientsData!: Client[];
  transaction!: Transaction;
  searchDone!: boolean;
  
  constructor (
    private clientService: ClientService,
    private modalService: NgbModal
  ){}

  ngOnInit(): void {
    this.searchDone = false;
  }

  searchAccount() {
    this.searchDone = true;
    this.clientService.getClientByCPF(this.accountCPF).subscribe(
      (clientsData: Client[]) => {
        this.clientData = clientsData[0];
        this.clientsData = clientsData;
      }
    )
  }

  openModal(transaction: Transaction){
    const modalRef = this.modalService.open(ModalTransactionComponent);
    modalRef.componentInstance.transaction = this.transaction;
  }
}

import { Component } from '@angular/core';
import { Client } from '../../../models/client.model';
import { Transaction } from '../../../models/transaction.model';
import { ClientService } from '../../../services/client/client.service';

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
    private clientService: ClientService
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

  confirmTransfer(transaction: Transaction){

  }
}

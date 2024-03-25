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
  accountNumber!: string;
  clientData!: Client;
  validAccountNumber!: boolean;
  transaction!: Transaction;
  
  constructor (
    private clientService: ClientService
  ){}

  ngOnInit(): void {
    this.validAccountNumber = false;
  }

  searchAccount() {
    if (this.clientService.searchById(this.accountNumber).subscribe(
      (clientData: Client) => {
        this.clientData = clientData;
      }
    )) {
      this.validAccountNumber = true;
    }
    else {
      this.validAccountNumber = false;
    }
  }

  confirmTransfer(transaction: Transaction){

  }
}

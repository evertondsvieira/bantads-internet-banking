import { Component } from '@angular/core';
import { Client } from '../../../models/client.model';
import { Transaction } from '../../../models/transaction.model';
import { ClientService } from '../../../services/client/client.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ModalTransactionComponent } from '../../../components/modal/modal-transaction/modal-transaction.component';
import { AccountService } from '../../../services/account/account.service';
import { Account } from '../../../models/account.model';

@Component({
  selector: 'app-client-transfer',
  templateUrl: './client-transfer.component.html',
  styleUrl: './client-transfer.component.css'
})
export class ClientTransferComponent {
  accountData!: Account;
  accountCPF!: string;
  clientData!: Client;
  clientsData!: Client[];
  transaction: Transaction = new Transaction(new Date(), "Transfer");
  searchDone!: boolean;
  
  constructor (
    private accountService: AccountService,
    private clientService: ClientService,
    private modalService: NgbModal
  ){}

  ngOnInit(): void {
    this.getAccount();
    this.searchDone = false;
  }

  getAccount() {
    this.accountService.getAccountByCPF(/*this.user.cpf*/'11111111111').subscribe(
      (accountsData: Account[]) => {
        this.accountData = accountsData[0];
        this.transaction.originAccount = accountsData[0].accountId;
      }
    )
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

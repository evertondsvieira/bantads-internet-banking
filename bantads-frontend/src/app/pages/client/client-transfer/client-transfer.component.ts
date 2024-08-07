import { Component, OnInit } from '@angular/core';
import { Client } from '../../../models/client.model';
import { Transaction } from '../../../models/transaction.model';
import { ClientService } from '../../../services/client/client.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ModalTransactionComponent } from '../../../components/modal/modal-transaction/modal-transaction.component';
import { AccountService } from '../../../services/account/account.service';
import { Account } from '../../../models/account.model';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-client-transfer',
  templateUrl: './client-transfer.component.html',
  styleUrl: './client-transfer.component.css'
})
export class ClientTransferComponent implements OnInit {
  accountData!: Account
  transaction: Transaction = new Transaction("TRANSFER");
  userId: number = 0;
  
  constructor (
    private accountService: AccountService,
    private modalService: NgbModal,
    private route: ActivatedRoute
  ){}

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const idParam = params.get("id")

      if (idParam && !isNaN(+idParam)) {
        this.userId = +idParam
        this.loadAccount()
      } else {
        console.log("Id do cliente é inválido")
      }
    })
  }

  loadAccount(): void {
    this.getAccount();
  }

  getAccount(): void {
    if (this.userId) {
      this.accountService.getAccountByUserId(this.userId).subscribe({
        next: (account: Account) => {
          this.accountData = account
          this.transaction = new Transaction("TRANSFER", undefined, this.accountData.id, this.transaction.destinationAccountId)
        },
        error: (error) => {
          console.error('Erro ao carregar cliente', error)
        }
      })
    } else {
      console.error('ID do cliente não fornecido')
    }
  }

  openModal(transaction: Transaction){
    this.transaction.destinationAccountId = Number(this.transaction.destinationAccountId);

    const modalRef = this.modalService.open(ModalTransactionComponent);
    modalRef.componentInstance.transaction = this.transaction;
  }
}

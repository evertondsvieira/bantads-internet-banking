import { Component, OnInit } from '@angular/core';
import { Account } from '../../../models/account.model';
import { AccountService } from '../../../services/account/account.service';
import { Transaction } from '../../../models/transaction.model';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ModalTransactionComponent } from '../../../components/modal/modal-transaction/modal-transaction.component';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-client-withdrawl',
  templateUrl: './client-withdrawl.component.html',
  styleUrl: './client-withdrawl.component.css'
})
export class ClientWithdrawlComponent implements OnInit {
  accountData!: Account;
  transaction: Transaction = new Transaction("WITHDRAWL");
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
          this.transaction = new Transaction("WITHDRAWL", undefined, this.accountData.id, this.accountData.id)
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
    const modalRef = this.modalService.open(ModalTransactionComponent);
    modalRef.componentInstance.transaction = this.transaction;
  }
}

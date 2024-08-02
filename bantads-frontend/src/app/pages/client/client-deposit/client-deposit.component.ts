import { Component, OnInit } from '@angular/core';
import { Account } from '../../../models/account.model';
import { AccountService } from '../../../services/account/account.service';
import { Transaction } from '../../../models/transaction.model';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ModalTransactionComponent } from '../../../components/modal/modal-transaction/modal-transaction.component';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-client-deposit',
  templateUrl: './client-deposit.component.html',
  styleUrls: ['./client-deposit.component.css']
})
export class ClientDepositComponent implements OnInit {
  accountData!: Account;
  transaction: Transaction = new Transaction("DEPOSIT");
  userId: number = 0;

  constructor(
    private accountService: AccountService,
    private modalService: NgbModal,
    private route: ActivatedRoute
  ) {}

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
      this.accountService.getAccountById(this.userId).subscribe({
        next: (account: Account) => {
          this.accountData = account
          this.transaction = new Transaction("DEPOSIT", undefined, this.accountData.id, this.accountData.id)
        },
        error: (error) => {
          console.error('Erro ao carregar cliente', error)
        }
      })
    } else {
      console.error('ID do cliente não fornecido')
    }
  }

  openModal(transaction: Transaction): void {
    const modalRef = this.modalService.open(ModalTransactionComponent);
    modalRef.componentInstance.transaction = this.transaction;
  }
}

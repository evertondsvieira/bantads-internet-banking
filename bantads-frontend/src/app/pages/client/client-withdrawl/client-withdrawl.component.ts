import { Component } from '@angular/core';
import { Account } from '../../../models/account.model';
import { AccountService } from '../../../services/account/account.service';
import { Transaction } from '../../../models/transaction.model';
import { User } from '../../../models/user.model';
import { AuthService } from '../../../services/auth/auth.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ModalTransactionComponent } from '../../../components/modal/modal-transaction/modal-transaction.component';

@Component({
  selector: 'app-client-withdrawl',
  templateUrl: './client-withdrawl.component.html',
  styleUrl: './client-withdrawl.component.css'
})
export class ClientWithdrawlComponent {
  accountData!: Account;
  transaction: Transaction = new Transaction(new Date(), "Withdrawl");
  user!: User
  
  constructor (
    private accountService: AccountService,
    private modalService: NgbModal,
    private authService: AuthService
  ){}

  ngOnInit(): void {
    this.getAccount();
    this.user = this.authService.loggedUser;
  }

  getAccount() {
    this.accountService.getAccountByCPF(/*this.user.cpf*/'11111111111').subscribe(
      (accountsData: Account[]) => {
        this.accountData = accountsData[0];
        this.transaction.originAccount = accountsData[0].accountId;
      }
    )
  }

  openModal(transaction: Transaction){
    const modalRef = this.modalService.open(ModalTransactionComponent);
    modalRef.componentInstance.transaction = this.transaction;
  }
}

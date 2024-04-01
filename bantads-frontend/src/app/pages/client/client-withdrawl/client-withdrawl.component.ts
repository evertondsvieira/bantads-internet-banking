import { Component } from '@angular/core';
import { Account } from '../../../models/account.model';
import { Transaction } from '../../../models/transaction.model';
import { AccountService } from '../../../services/account/account.service';
import { User } from '../../../models/user.model';
import { AuthService } from '../../../services/auth/auth.service';

@Component({
  selector: 'app-client-withdrawl',
  templateUrl: './client-withdrawl.component.html',
  styleUrl: './client-withdrawl.component.css'
})
export class ClientWithdrawlComponent {
  accountData!: Account;
  transaction!: Transaction;
  user!: User
  
  constructor (
    private accountService: AccountService,
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
      }
    )
  }

  confirmWithdrawl(transaction: Transaction){

  }
}

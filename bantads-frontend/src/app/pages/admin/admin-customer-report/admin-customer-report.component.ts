import { Component, OnInit } from '@angular/core';
import { User } from '../../../models/user.model';
import { AuthService } from '../../../services/auth/auth.service';
import { AccountService } from '../../../services/account/account.service';
import { CommonModule } from '@angular/common';
import { RealPipePipe } from '../../../modules/shared/pipes';
import { Account } from '../../../models/account.model';

@Component({
  selector: 'app-admin-customer-report',
  templateUrl: './admin-customer-report.component.html',
  styleUrl: './admin-customer-report.component.css',
})
export class AdminCustomerReportComponent implements OnInit {
  private _user: User = this.authService.loggedUser;
  public accounts!: Account[];

  constructor(
    private authService: AuthService,
    private accountService: AccountService
  ){
  }

  ngOnInit(): void {
    this.user = this.authService.loggedUser;
    this.getAccountsInfo();
  }

  public get user(): User {
    return this._user;
  }
  public set user(user: User) {
    this._user = user;
  }

  getAccountsInfo(): void {
    this.accountService.getAccounts().subscribe(accounts => {
      this.accounts = accounts.sort((a, b) => {
        if (a.client.name < b.client.name) {
          return -1;
        } else if (a.client.name > b.client.name) {
          return 1;
        } else {
          return 0;
        }
      });
    });
  }
}

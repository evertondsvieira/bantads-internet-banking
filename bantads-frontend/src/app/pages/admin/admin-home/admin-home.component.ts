import { Component, OnInit } from '@angular/core';
import { User } from '../../../models/user.model';
import { AccountReport } from '../../../models/account-report.model';
import { AccountService } from '../../../services/account/account.service';
import { AuthService } from '../../../services/auth/auth.service';

@Component({
  selector: 'app-admin-home',
  templateUrl: './admin-home.component.html',
  styleUrl: './admin-home.component.css'
})

export class AdminHomeComponent implements OnInit {
  private _user: User = this.authService.loggedUser;
  public reports!: AccountReport[];

  constructor(
    private authService: AuthService,
    private accountService: AccountService
  ) {}

  ngOnInit(): void {
    this.user = this.authService.loggedUser;
    this.getReports();
  }

  public get user(): User {
    return this._user;
  }
  public set user(user: User) {
    this._user = user;
  }  

  public getReports() {
    this.accountService.getAccountsReport().subscribe((reports) => {
      this.reports = reports;
    });
  }

}

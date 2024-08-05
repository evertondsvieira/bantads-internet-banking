import { Component, OnInit } from '@angular/core';
import { AccountService } from '../../../services/account/account.service';
import { Account } from '../../../models/account.model';
import { ClientService } from '../../../services/client/client.service';

@Component({
  selector: 'app-manager-consult-top',
  templateUrl: './manager-consult-top.component.html',
  styleUrl: './manager-consult-top.component.css'
})
export class ManagerConsultTopComponent implements OnInit {
  accounts: Account[] = [];
  sortedAccounts: Account[] = [];

  constructor(
    private accountService: AccountService,
    private clientService: ClientService
  ) {}

  ngOnInit(): void {
    this.accountService.getAccounts().subscribe({
      next: (accounts) => {
        if (accounts.length > 0) {
          this.accounts = accounts;
          this.sortedAccounts = this.accounts
            .sort((a, b) => b.balance - a.balance) 
            .slice(0, 3);
          this.sortedAccounts.forEach(acc => {
              this.clientService.getClientByCPF(acc.client.cpf).subscribe(
                (clientList) => acc.client = clientList[0]
              )
           });
        }
      },
      error: (error) => {
        console.error('Erro ao consultar contas:', error);
      }
    })
  }
}

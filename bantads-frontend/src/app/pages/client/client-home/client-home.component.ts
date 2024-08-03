import { Component, OnInit } from '@angular/core';
import { Account } from '../../../models/account.model';
import { AccountService } from '../../../services/account/account.service';
import { ActivatedRoute } from '@angular/router';

interface IOperation {
  title: string;
  icon: string;
  link: string;
}

@Component({
  selector: 'app-client-home',
  templateUrl: './client-home.component.html',
  styleUrl: './client-home.component.css'
})
export class ClientHomeComponent implements OnInit {
  accountData!: Account
  saldo: number = -20

  userId = localStorage.getItem('userId')

  operations: IOperation[] = [
    { title: 'Depositar', icon: 'attach_money', link: `deposit/${this.userId}` },
    { title: 'Sacar', icon: 'money_off', link: `withdrawl/${this.userId}` },
    { title: 'Transferir', icon: 'compare_arrows', link: `transfer/${this.userId}` },
    { title: 'Extrato', icon: 'description', link: `statement/${this.userId}` },
  ]

  initializeOperations(): void {
    if (this.userId) {
      this.operations = [
        { title: 'Depositar', icon: 'attach_money', link: `deposit/${this.userId}` },
        { title: 'Sacar', icon: 'money_off', link: `withdrawl/${this.userId}` },
        { title: 'Transferir', icon: 'compare_arrows', link: `transfer/${this.userId}` },
        { title: 'Extrato', icon: 'description', link: 'statement' },
      ];
    }
  }

  constructor(
    private accountService: AccountService,
  ) {}

  ngOnInit(): void {
    this.getAccount()
  }

  getAccount(): void {
    if (this.userId) {
      this.accountService.getAccountById(Number(this.userId)).subscribe({
        next: (account: Account) => {
          this.accountData = account
        },
        error: (error) => {
          console.error('Erro ao carregar cliente', error)
        }
      })
    } else {
      console.error('ID do cliente não fornecido')
    }
  }

  getBalanceColor(value: number) {
    return value < 0 ? 'text-danger' : 'text-dark';
  }
}

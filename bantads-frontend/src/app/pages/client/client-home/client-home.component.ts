import { Component, OnInit } from '@angular/core';
import { Account } from '../../../models/account.model';
import { AccountService } from '../../../services/account/account.service';
import { UserServiceId } from '../../../services/auth/userId.service';

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
  accountData!: Account;
  userId: string | null = null;
  operations: IOperation[] = [];

  constructor(
    private accountService: AccountService,
    private userService: UserServiceId
  ) {}

  initializeOperations(): void {
    if (this.userId) {
      this.operations = [
        { title: 'Depositar', icon: 'attach_money', link: `deposit/${this.userId}` },
        { title: 'Sacar', icon: 'money_off', link: `withdraw/${this.userId}` },
        { title: 'Transferir', icon: 'compare_arrows', link: `transfer/${this.userId}` },
        { title: 'Extrato', icon: 'description', link: `statement/${this.userId}` },
      ];
    } else {
      console.error('ID do cliente não fornecido');
    }
  }

  getAccount(): void {
    if (this.userId) {
      this.accountService.getAccountById(Number(this.userId)).subscribe({
        next: (account: Account) => {
          this.accountData = account;
        },
        error: (error) => {
          console.error('Erro ao carregar cliente', error);
        }
      });
    } else {
      console.error('ID do cliente não fornecido');
    }
  }

  getBalanceColor(value: number) {
    return value < 0 ? 'text-danger' : 'text-dark';
  }

  ngOnInit(): void {
    this.userService.getUserId().subscribe(id => {
      this.userId = id;
      console.log('User ID no ngOnInit:', this.userId);

      if (!this.userId) {
        console.error('ID do cliente não encontrado no UserService');
        return;
      }

      this.initializeOperations();
      this.getAccount();
    });
  }
}

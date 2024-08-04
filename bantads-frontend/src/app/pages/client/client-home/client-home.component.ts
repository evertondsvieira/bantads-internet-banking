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
        { title: 'Sacar', icon: 'money_off', link: `withdrawl/${this.userId}` },
        { title: 'Transferir', icon: 'compare_arrows', link: `transfer/${this.userId}` },
        { title: 'Extrato', icon: 'description', link: `statement/${this.userId}` },
      ];
    } else {
      console.error('ID do cliente não fornecido');
    }
  }

  getAccount(): void {
    if (this.userId) {
      this.accountService.getAccountByUserId(Number(this.userId)).subscribe({
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
  
      if (this.userId) {
        this.initializeOperations();
        this.getAccount();
      } else {
        setTimeout(() => {
          this.userService.getUserId().subscribe(id => {
            this.userId = id;
            if (this.userId) {
              this.initializeOperations();
              this.getAccount();
            } else {
              console.error('ID do cliente ainda não disponível');
              window.location.reload();
            }
          });
        }, 3000)
      }
    }, error => {
      console.error('Erro ao obter ID do usuário', error);
      window.location.reload();
    });
  }  
}

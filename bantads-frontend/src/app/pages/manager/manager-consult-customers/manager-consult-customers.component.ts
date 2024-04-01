import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Client } from '../../../models/client.model';
import { Account } from '../../../models/account.model';
import { ClientService } from '../../../services/client/client.service';
import { User } from '../../../models/user.model';
import { AuthService } from '../../../services/auth/auth.service';
import { AccountService } from '../../../services/account/account.service';

@Component({
  selector: 'app-manager-consult-customers',
  templateUrl: './manager-consult-customers.component.html',
  styleUrl: './manager-consult-customers.component.css'
})
export class ManagerConsultCustomersComponent {
  cpf: string = '';
  public clients!: Client[];
  private _client!: Client;
  public accounts!: Account[];
  private _account!: Account;
  mensagemErro: string | undefined;


  constructor(
    private accountService: AccountService,
    private clientService: ClientService,
    private router: Router
  ) {}

  public get client(): Client {
    return this._client;
  }
  public set client(value: Client) {
    this._client = value;
  }

  public get account(): Account {
    return this._account;
  }
  public set account (value: Account) {
    this._account = value;
  }
//retomar
  public consultClient(cpf: string) {
    this.clientService.getClientByCPF(cpf).subscribe(
      clients => {
        if (clients) {
          this.client = clients[0];
          this.mensagemErro = undefined;
          // Se o cliente foi encontrado, busca a conta associada
          this.accountService.getAccountByCPF(this.client.cpf).subscribe(
            account => {
              this.account = this.accounts[0];
            },
            error => {
              console.error('Erro ao consultar conta:', error);
            }
          );
        } else {
          this.mensagemErro = 'Cliente não encontrado.';
        }
      },
      error => {
        console.error('Erro ao consultar cliente:', error);
        this.mensagemErro = 'Ocorreu um erro ao consultar o cliente.';
      }
    );
  }
}
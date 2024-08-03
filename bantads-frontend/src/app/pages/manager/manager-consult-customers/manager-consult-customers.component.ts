import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { Client } from '../../../models/client.model';
import { Account } from '../../../models/account.model';
import { ClientService } from '../../../services/client/client.service';
import { AccountService } from '../../../services/account/account.service';

@Component({
  selector: 'app-manager-consult-customers',
  templateUrl: './manager-consult-customers.component.html',
  styleUrl: './manager-consult-customers.component.css',
})
export class ManagerConsultCustomersComponent implements OnInit {
  cpf: string = '';
  clientData: Client | null = null
  accountData: Account | null = null
  mensagemErro?: string
  mask: string = ''

  constructor(
    private accountService: AccountService,
    private clientService: ClientService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe((params: Params) => {
      this.cpf = params['cpf'];
      if (this.cpf) {
        this.consultClient(this.cpf);
        this.loadAccount(this.cpf);
      }
    });
  }

  consultClient(cpf: string): void {
    this.clientService.getClientByCPF(cpf).subscribe({
      next: (clientData: Client[]) => {
        if (clientData.length > 0) {
          this.clientData = clientData[0]
        } else {
          this.mensagemErro = 'Cliente não encontrado.'
          this.clientData = null
        }
      },
      error: (error) => {
        console.error('Erro ao consultar cliente:', error)
        this.mensagemErro = 'Ocorreu um erro ao consultar o cliente.'
        this.clientData = null
      },
    });
  }

  loadAccount(cpf: string): void {
    this.accountService.getAccountByCPF(cpf).subscribe({
      next: (accountData: Account[]) => {
        if (accountData.length > 0) {
          this.accountData = accountData[0]
        } else {
          this.mensagemErro = 'Conta não encontrada.'
          this.accountData = null
        }
      },
      error: (error) => {
        console.error('Erro ao consultar conta:', error)
        this.mensagemErro = 'Ocorreu um erro ao consultar a conta.'
        this.accountData = null
      },
    });
  }

  onInputChange(value: string): void {
    this.mask = /^\d+$/.test(value) ? '000.000.000-00' : ''
  }
}

import { AccountService } from './../../../services/account/account.service';
import { Component, OnInit } from '@angular/core';
import { Client } from '../../../models/client.model';
import { Address } from '../../../models/address.model';
import { ClientService } from '../../../services/client/client.service';
import { ActivatedRoute } from '@angular/router';
import { Situation } from '../../../models/enum/situation.enum';
import { Account } from '../../../models/account.model';

@Component({
  selector: 'app-client-update-profile',
  templateUrl: './client-update-profile.component.html',
  styleUrl: './client-update-profile.component.css',
})
export class ClientUpdateProfileComponent implements OnInit {
  address: Address = new Address('', '', 0, '', '', '', '').toAddressObject();
  client: Client = new Client(0, '', '', '', this.address, '', 0, Situation.PENDING).toClientObject();

  clientData!: Client;
  addressData!: Address;
  accountData!: Account;

  valueVisible: boolean = false;
  saldo: number = 0;
  clientId: number = 0;

  constructor(
    private clientService: ClientService,
    private accountService: AccountService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
      const idParam = params.get('id');

      if (idParam && !isNaN(+idParam)) {
        this.clientId = +idParam;
        this.loadClient();
        this.loadAccount()
      } else {
        console.log('Id do cliente é inválido');
      }
    });
  }

  loadClient(): void {
    if (this.clientId) {
      this.clientService.getClientById(this.clientId).subscribe({
        next: (client: Client) => {
          this.client = client;
          this.address = client.address;
          this.clientData = this.client;
          this.addressData = this.address;
          console.log('Cliente carregado com sucesso', this.client);
        },
        error: (error) => {
          console.error('Erro ao carregar cliente', error);
        },
      });
    } else {
      console.error('ID do cliente não fornecido');
    }
  }

  loadAccount(): void {
    if (this.clientId) {
      this.accountService.getAccountById(this.clientId).subscribe({
        next: (account: Account) => {
          this.accountData = account;
          console.log('Conta carregada com sucesso', this.accountData)
        },
        error: (error) => {
          console.error('Erro ao carregar cliente', error);
        },
      });
    } else {
      console.error('ID do cliente não fornecido');
    }
  }

  onSubmit(): void {
    if (this.client.id) {
      this.clientService.updateClient(this.client).subscribe({
        next: (updatedClient: Client) => {
          console.log('Cliente atualizado com sucesso!', updatedClient);
        },
        error: (error) => {
          console.error('Erro ao atualizar cliente', error);
        },
      });
    } else {
      console.error('Cliente não encontrado ou não definido');
    }
    console.log(this.client.id);
  }

  toggleValue(): void {
    this.valueVisible = !this.valueVisible;
  }
}

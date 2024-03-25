import { Component } from '@angular/core';
import { Client } from '../../../models/client.model';
import { Transaction } from '../../../models/transaction.model';
import { ClientService } from '../../../services/client/client.service';
import { User } from '../../../models/user.model';
import { AuthService } from '../../../services/auth/auth.service';

@Component({
  selector: 'app-client-deposit',
  templateUrl: './client-deposit.component.html',
  styleUrl: './client-deposit.component.css'
})
export class ClientDepositComponent {
  accountNumber!: string;
  clientData!: Client;
  transaction!: Transaction;
  user!: User
  
  constructor (
    private clientService: ClientService,
    private authService: AuthService
  ){}

  ngOnInit(): void {
    this.getAccount();
    this.user = this.authService.loggedUser
  }

  getAccount() {
    this.clientService.searchByCpf(/*this.user.cpf*/'11111111111').subscribe(
      (clientsData: Client[]) => {
        this.clientData = clientsData[0];
      }
    )
  }

  confirmDeposit(transaction: Transaction){

  }
}
import { Component, OnInit } from '@angular/core';
import { User } from '../../../models/user.model';
import { AuthService } from '../../../services/auth/auth.service';
import { Client } from '../../../models/client.model';
import { ClientService } from '../../../services/client/client.service';
import { CommonModule } from '@angular/common';
import { RealPipePipe } from '../../../modules/shared/pipes';

@Component({
  selector: 'app-admin-customer-report',
  templateUrl: './admin-customer-report.component.html',
  styleUrl: './admin-customer-report.component.css',
})
export class AdminCustomerReportComponent implements OnInit {
  private _user: User = this.authService.loggedUser;
  private _clients!: Client[];

  constructor(
    private authService: AuthService,
    private clientService: ClientService
  ){
  }

  ngOnInit(): void {
    this.user = this.authService.loggedUser;
    this.getClientsOrderedByName();
  }

  public get user(): User {
    return this._user;
  }
  public set user(user: User) {
    this._user = user;
  }
   
  public get clients(): Client[] {
    return this._clients;
  }
  public set clients(value: Client[]) {
    this._clients = value;
  }

  getClientsOrderedByName(): void {
    this.clientService.getClientsOrderedByName().subscribe(clients => {
      this.clients = clients;
    });
  }

  getManagerName(accountId: string): string {
    // Implementar lógica mais tarde
    return 'Nome do Gerente';
  }
}

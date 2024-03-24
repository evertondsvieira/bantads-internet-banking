import { Component, OnInit } from '@angular/core';
import { Client } from '../../../models/client.model';
import { ClientService } from '../../../services/client/client.service';
import { User } from '../../../models/user.model';
import { AuthService } from '../../../services/auth/auth.service';

@Component({
  selector: 'app-manager-consult-all-customers',
  templateUrl: './manager-consult-all-customers.component.html',
  styleUrl: './manager-consult-all-customers.component.css',
})
export class ManagerConsultAllCustomersComponent implements OnInit {
  private _user: User = this.authService.loggedUser;
  private _clients!: Client[];
  public searchQuery: string = '';

  constructor(
    private authService: AuthService,
    private clientService: ClientService
  ) {}

  ngOnInit(): void {
    this._user = this.authService.loggedUser;
    this.getClientsOrderedByName();
  }

  public get clients(): Client[] {
    return this._clients;
  }
  public set clients(value: Client[]) {
    this._clients = value;
  }

  public getUser(): User {
    return this._user;
  }
  public set user(user: User) {
    this._user = user;
  }

  public getClientsOrderedByName() {
    this.clientService.getClientsOrderedByName().subscribe((clients) => {
      this._clients = clients;
    });
  }

  public searchClients() {
    if (this.searchQuery.trim() !== '') {
      this.clientService
        .searchClients(this.searchQuery)
        .subscribe((clients) => {
          this._clients = clients;
        });
    } else {
      this.getClientsOrderedByName();
    }
  }
}

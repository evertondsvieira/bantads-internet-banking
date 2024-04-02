import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Client } from '../../../models/client.model';
import { ClientService } from '../../../services/client/client.service';
import { User } from '../../../models/user.model';
import { AuthService } from '../../../services/auth/auth.service';
import { Adress } from '../../../models/adress.model';

@Component({
  selector: 'app-manager-consult-all-customers',
  templateUrl: './manager-consult-all-customers.component.html',
  styleUrl: './manager-consult-all-customers.component.css',
})
export class ManagerConsultAllCustomersComponent implements OnInit {
  private _lista!: Client[];
  public searchQuery: string = '';

  constructor(
    private clientService: ClientService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.getClientsOrderedByName();
  }

  public get lista(): Client[] {
    return this._lista;
  }

  public set lista(value: Client[]) {
    this._lista = value;
  }
  public getClientsOrderedByName() {
    this.clientService.getClientsOrderedByName().subscribe(
      lista => {
      this._lista = lista;
    });
  }

  public searchClients() {
    if (this.searchQuery.trim() !== '') {
      this.clientService
        .searchClients(this.searchQuery)
        .subscribe(
          lista => {
          this._lista = lista;
        });
    } else {
      this.getClientsOrderedByName();
    }
  }

  public viewMoreDetails(clientCPF: string) {
    this.router.navigate(['manager/consult/customers', clientCPF]);
  }
}

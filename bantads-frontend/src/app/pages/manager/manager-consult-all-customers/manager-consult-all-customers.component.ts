import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Client } from '../../../models/client.model';
import { ClientService } from '../../../services/client/client.service';
import { User } from '../../../models/user.model';
import { AuthService } from '../../../services/auth/auth.service';
import { RealPipePipe, CpfPipe } from '../../../modules/shared/pipes';

@Component({
  selector: 'app-manager-consult-all-customers',
  templateUrl: './manager-consult-all-customers.component.html',
  styleUrl: './manager-consult-all-customers.component.css',
})
export class ManagerConsultAllCustomersComponent implements OnInit {
  private _lista!: Client[];
  public searchQuery: string = '';
  public mask: string = '';

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

  public onInputChange(value: string): void {
  // Verifica se a entrada contém apenas números
    if (/^\d+$/.test(value)) {
      this.mask = '000.000.000-00'; // Máscara de CPF
    } else {
      this.mask = ''; // Sem máscara para texto
    }
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

  public viewMoreDetails(clientCpf: string) {
    this.router.navigate(['manager/consult/customers', clientCpf]);
  }
}

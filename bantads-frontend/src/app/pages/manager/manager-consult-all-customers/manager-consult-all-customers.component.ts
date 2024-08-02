import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Client } from '../../../models/client.model';
import { ClientService } from '../../../services/client/client.service';

@Component({
  selector: 'app-manager-consult-all-customers',
  templateUrl: './manager-consult-all-customers.component.html',
  styleUrls: ['./manager-consult-all-customers.component.css'],
})
export class ManagerConsultAllCustomersComponent implements OnInit {
  clientData: Client[] = [];

  public mask: string = '';
  public searchQuery: string = '';

  constructor(
    private clientService: ClientService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.getClientsOrderedByName();
  }

  public onInputChange(value: string): void {
    if (/^\d+$/.test(value)) {
      this.mask = '000.000.000-00';
    } else {
      this.mask = '';
    }
  }

  public getClientsOrderedByName(): void {
    this.clientService.getClient().subscribe(
      data => this.clientData = data
    );
  }

  public searchClients(): void {
    if (this.searchQuery.trim() !== '') {
      if (/^\d{11}$/.test(this.searchQuery)) {
        this.clientService.getClientByCPF(this.searchQuery).subscribe(
          data => this.clientData = data
        );
      } else {
        this.clientService.getClientByName(this.searchQuery).subscribe(
          data => this.clientData = data
        );
      }
    } 
  
    this.getClientsOrderedByName();
  }

  public viewMoreDetails(clientCpf: string): void {
    this.router.navigate(['manager/consult/customers', clientCpf]);
  }
}

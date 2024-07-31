import { Component, OnInit } from '@angular/core';
import { Client } from '../../../models/client.model';
import { Address } from '../../../models/address.model';
import { ClientService } from '../../../services/client/client.service';
import { ActivatedRoute } from '@angular/router';
import { Situation } from '../../../models/enum/situation.enum';

@Component({
  selector: 'app-client-update-profile',
  templateUrl: './client-update-profile.component.html',
  styleUrl: './client-update-profile.component.css'
})
export class ClientUpdateProfileComponent implements OnInit { 
  address: Address = new Address('', '', 0, '', '', '', '');
  client: Client = new Client(0, '', '', '', this.address, '', 0, Situation.PENDING);

  clientData: any;
  addressData: any;

  valueVisible: boolean = false;
  saldo: number = 0;
  clientId: number = 0;

  constructor(
    private clientService: ClientService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const idParam = params.get("id")

      if (idParam && !isNaN(+idParam)) {
        this.clientId = +idParam
        this.loadClient()
      } else {
        console.log("Id do cliente é inválido")
      }
    })
  }

  loadClient(): void {
    if (this.clientId) {
      this.clientService.getClientById(this.clientId.toString()).subscribe({
        next: (client: Client) => {
          this.client = client
          this.address = client.address
          this.clientData = this.client.toClientObject()
          this.addressData = this.address.toAddressObject()
          console.log('Cliente carregado com sucesso', this.client)
        },
        error: (error) => {
          console.error('Erro ao carregar cliente', error)
        }
      })
    } else {
      console.error('ID do cliente não fornecido')
    }
  }

  onSubmit(): void {
    if (this.client.id) {
      this.clientService.updateClient(this.client.toClientObject()).subscribe({
        next: (updatedClient: Client) => {
          console.log('Cliente atualizado com sucesso!', updatedClient)
        },
        error: (error) => {
          console.error('Erro ao atualizar cliente', error)
        }
      });
    } else {
      console.error('Cliente não encontrado ou não definido')
    }
  }

  toggleValue(): void {
    this.valueVisible = !this.valueVisible;
  }
}

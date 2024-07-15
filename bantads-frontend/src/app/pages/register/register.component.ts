import { Component, ViewChild, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { CurrencyPipe } from "@angular/common";
import { NumericoDirective } from '../../modules/shared/directives/numerico.directive';
import { AuthService } from '../../services/auth/auth.service';
import { Client } from '../../models/client.model';
import { ClientService } from '../../services/client/client.service';
import { Address } from '../../models/address.model';

interface IStatusMessage {
  status: boolean; // true - ok | false - erro
  message: string;
  style: string;
  icon: string
} 

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.css',
  viewProviders: [NumericoDirective, CurrencyPipe]
})
export class RegisterComponent implements OnInit{
  @ViewChild('registerForm') registerForm!: NgForm;
  public client!: Client;
  public address: Address = new Address();
  public statusMessage: IStatusMessage = {"status": false, "message": "", "style": "", "icon": ""};

  constructor(
    private clientService: ClientService,
    private router: Router,
    private currencyPipe: CurrencyPipe,
    private authService: AuthService ,
  ) {
  }

  ngOnInit(): void {
  }

  addClient(client: Client): void{
    const date = new Date();
    client.id = date.getTime().toString();
    this.client.setAddress(this.address);
    this.clientService.addClient(client).subscribe((client) =>
      this.statusMessage = {"status": true, "message": "Cadastro Realizado com sucesso", "style": "alert alert-success", "icon": "bi bi-check-circle"}
    );
  }

  newRegister(): void {
    this.statusMessage = {"status": false, "message": "", "style": "", "icon": ""};
    this.client = new Client(this.address,"","","","",0,0,"",0);
  }


    
}


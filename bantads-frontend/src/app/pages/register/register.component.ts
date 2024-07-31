import { Component } from '@angular/core';
import { CurrencyPipe } from '@angular/common';
import { NumericoDirective } from '../../modules/shared/directives/numerico.directive';
import { Client } from '../../models/client.model';
import { Address } from '../../models/address.model';
import { Situation } from '../../models/enum/situation.enum';
import { Router } from '@angular/router';
import { UserService } from '../../services/user/user.service';

interface IStatusMessage {
  status: boolean;
  message: string;
  style: string;
  icon: string;
}

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.css',
  viewProviders: [NumericoDirective, CurrencyPipe],
})
export class RegisterComponent {
  address: Address = new Address('', '', 0, '', '', '', '').toAddressObject()
  client: Client = new Client(0, '', '', '', this.address, '', 0, Situation.PENDING).toClientObject()
  
  public statusMessage: IStatusMessage = {
    status: false,
    message: '',
    style: '',
    icon: '',
  };

  constructor(private userService: UserService, private router: Router) {}

  addClient(): void {
    const clientData = this.client

    this.userService.register(clientData).subscribe({
      next: (response) => {
        console.log("Cliente criado com sucesso!", response)
        this.statusMessage = {
          status: true,
          message: 'Cliente criado com sucesso!',
          style: 'alert-success',
          icon: 'bi-check-circle',
        }
        this.router.navigate(['/login'])
      },
      error: (error) => {
        console.error(error)
        this.statusMessage = {
          status: false,
          message: 'Erro ao criar cliente.',
          style: 'alert-danger',
          icon: 'bi-x-circle',
        };
      },
    });
  }
}

import { Component } from '@angular/core';

interface ITopClients {
  id: number
  name: string
  city: string
  state: string
  balance: number
}

@Component({
  selector: 'app-manager-consult-top',
  templateUrl: './manager-consult-top.component.html',
  styleUrl: './manager-consult-top.component.css'
})

export class ManagerConsultTopComponent {
  clients: ITopClients[] = [
    { id: 1, name: 'Everton', city: 'Curitiba', state: 'Paraná', balance: 50 },
    { id: 2, name: 'Lucas', city: 'Curitiba', state: 'Paraná', balance: 1000 },
    { id: 3, name: 'Caroline', city: 'Curitiba', state: 'Paraná', balance: 2000 },
    { id: 4, name: 'Hissatomi', city: 'Curitiba', state: 'Paraná', balance: 800 },
  ]
  
  sortedClients: ITopClients[] = this.clients.sort((a, b) => b.balance - a.balance).slice(0, 3)
}

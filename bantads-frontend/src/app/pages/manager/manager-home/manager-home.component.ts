import { Component, OnInit } from '@angular/core';
import { User } from '../../../models/user.model';
import { AuthService } from '../../../services/auth/auth.service';
import { Client } from '../../../models/client.model';
import { ClientService } from '../../../services/client/client.service';

interface IAcao {
  title: string;
  icon: string;
  buttonStyle: string
}

@Component({
  selector: 'app-manager-home',
  templateUrl: './manager-home.component.html',
  styleUrl: './manager-home.component.css'
})
export class ManagerHomeComponent implements OnInit{
  private _user: User = this.authService.loggedUser;
  private _clients!: Client[];

  constructor(
    private authService: AuthService,
    private clientService: ClientService
  ){
  }

  ngOnInit(): void {
    this.user = this.authService.loggedUser;
    this.getClientsWithOpenSituation();
  }

  public acoes: IAcao[] = [
    { title: "aprovar", icon: "bi-check-lg", buttonStyle: "btn-success" },
    { title: "rejeitar", icon: "bi-x", buttonStyle: "btn-danger" }
  ];

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

  public getClientsWithOpenSituation(){
    this.clientService.getClientsWithOpenSituation().subscribe(
      clients => {
        this.clients = clients;
      }
    );
  }
}

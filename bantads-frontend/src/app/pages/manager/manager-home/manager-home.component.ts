import { Component, OnInit } from '@angular/core';
import { User } from '../../../models/user.model';
import { AuthService } from '../../../services/auth/auth.service';
import { Client } from '../../../models/client.model';
import { ClientService } from '../../../services/client/client.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ManagerApproveComponent } from '../manager-approve/manager-approve.component';
import { ManagerRejectComponent } from '../manager-reject/manager-reject.component';

interface IAcao {
  title: string;
  icon: string;
  buttonStyle: string;
  component: Object;
}

@Component({
  selector: 'app-manager-home',
  templateUrl: './manager-home.component.html',
  styleUrl: './manager-home.component.css'
})

export class ManagerHomeComponent implements OnInit{
  private _user: User = this.authService.loggedUser;
  private _clients: Client[] = [];

  constructor(
    private authService: AuthService,
    private clientService: ClientService,
    private _modalService: NgbModal
  ){
  }

  ngOnInit(): void {
    this.user = this.authService.loggedUser;
    this.getClientsWithOpenSituation();
  }

  public acoes: IAcao[] = [
    { title: "aprovar", icon: "bi-check-lg", buttonStyle: "btn-success", component: ManagerApproveComponent},
    { title: "rejeitar", icon: "bi-x", buttonStyle: "btn-danger", component: ManagerRejectComponent}
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

  public get modalService(): NgbModal {
    return this._modalService;
  }
  public set modalService(value: NgbModal) {
    this._modalService = value;
  }

  public getClientsWithOpenSituation(){
    this.clientService.getClientsWithOpenSituation().subscribe(
      clients => {
        this.clients = clients;
      }
    );
  }

  public openModal(modalService: NgbModal, client: Client, component: Component): Boolean {
    const modalRef = modalService.open(component);
    modalRef.componentInstance.client = client;
    modalRef.dismissed.subscribe(result =>{
      if(result){
        this.clients = this.clients.filter((cli) => {
          return cli.id != client.id;
        });
      }
    })
    return true;
  }

  public openModalManagerReject(modalService: NgbModal, client: Client): Boolean {
    const modalRef = modalService.open(ManagerRejectComponent);
    return true;
  }
}

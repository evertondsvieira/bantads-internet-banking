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
  buttonStyle: string,
  manageModal: (modalService: NgbModal) => Boolean
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
    private clientService: ClientService,
    private _modalService: NgbModal
  ){
  }

  ngOnInit(): void {
    this.user = this.authService.loggedUser;
    this.getClientsWithOpenSituation();
  }

  public acoes: IAcao[] = [
    { title: "aprovar", icon: "bi-check-lg", buttonStyle: "btn-success", manageModal: this.openModalManagerApprove },
    { title: "rejeitar", icon: "bi-x", buttonStyle: "btn-danger", manageModal: this.openModalManagerReject }
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

  public openModalManagerApprove(modalService: NgbModal): Boolean {
    const modalRef = modalService.open(ManagerApproveComponent)
    return true;
  }

  public openModalManagerReject(modalService: NgbModal): Boolean {
    const modalRef = modalService.open(ManagerRejectComponent)
    return true;
  }
}

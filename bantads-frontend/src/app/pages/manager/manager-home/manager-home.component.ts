import { Component, OnInit } from '@angular/core';
import { User } from '../../../models/user.model';
import { AuthService } from '../../../services/auth/auth.service';
import { Client } from '../../../models/client.model';
import { ClientService } from '../../../services/client/client.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ManagerApproveComponent } from '../manager-approve/manager-approve.component';
import { ManagerRejectComponent } from '../manager-reject/manager-reject.component';

interface IAction {
  title: string;
  icon: string;
  buttonStyle: string;
  component: any;
}

@Component({
  selector: 'app-manager-home',
  templateUrl: './manager-home.component.html',
  styleUrls: ['./manager-home.component.css']
})
export class ManagerHomeComponent implements OnInit {
  public user!: User;
  public clients: Client[] = []

  public acoes: IAction[] = [
    { title: 'aprovar', icon: 'bi-check-lg', buttonStyle: 'btn-success', component: ManagerApproveComponent },
    { title: 'rejeitar', icon: 'bi-x', buttonStyle: 'btn-danger', component: ManagerRejectComponent }
  ];

  constructor(
    private authService: AuthService,
    private clientService: ClientService,
    private modalService: NgbModal
  ) {}

  ngOnInit(): void {
    this.user = this.authService.loggedUser;
    this.getClientsWithOpenSituation();
  }

  private getClientsWithOpenSituation(): void {
    this.clientService.getClientsWithOpenSituation('PENDING').subscribe(
      (clients) => {
        this.clients = clients;
      }
    );
  }

  public openModal(client: Client, component: any): void {
    const modalRef = this.modalService.open(component)
    modalRef.componentInstance.client = client
    modalRef.dismissed.subscribe((result) => {
      if (result) {
        this.clients = this.clients.filter((cli) => cli.id !== client.id);
      }
    })
  }
}

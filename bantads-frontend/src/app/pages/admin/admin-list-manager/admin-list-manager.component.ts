import { Component, OnInit } from '@angular/core';
import { User } from '../../../models/user.model';
import { Manager } from '../../../models/manager.model';
import { AuthService } from '../../../services/auth/auth.service';
import { ManagerService } from '../../../services/manager/manager.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AdminUpdateManagerComponent } from '../admin-update-manager/admin-update-manager.component';
import { AdminRemoveManagerComponent } from '../admin-remove-manager/admin-remove-manager.component';

interface IAcao {
  title: string;
  icon: string;
  buttonStyle: string,
  manageModal: (modalService: NgbModal) => Boolean
}

@Component({
  selector: 'app-admin-list-manager',
  templateUrl: './admin-list-manager.component.html',
  styleUrl: './admin-list-manager.component.css'
})

export class AdminListManagerComponent implements OnInit{

  private _user: User = this.authService.loggedUser;
  private _managers!: Manager[];
  public searchQuery: string = '';

  constructor(
    private authService: AuthService,
    private managerService: ManagerService,
    private _modalService: NgbModal
  ) {}

  ngOnInit(): void {
    this._user = this.authService.loggedUser;
    this.getManagersOrderedByName();
  }

  public acoes: IAcao[] = [
    { title: "editar", icon: "bi-pencil-square", buttonStyle: "btn-primary", manageModal: this.openModalManagerUpdate },
    { title: "remover", icon: "bi-trash-fill", buttonStyle: "btn-danger", manageModal: this.openModalManagerRemove }
  ];

  public get managers(): Manager[] {
    return this._managers;
  }
  public set managers(value: Manager[]) {
    this._managers = value;
  }

  public get user(): User {
    return this._user;
  }
  public set user(user: User) {
    this._user = user;
  }

  public get modalService(): NgbModal {
    return this._modalService;
  }
  public set modalService(value: NgbModal) {
    this._modalService = value;
  }

  public getManagersOrderedByName() {
    this.managerService.getManagersOrderedByName().subscribe((managers) => {
      this._managers = managers;
    });
  }

  public openModalManagerUpdate(modalService: NgbModal): Boolean {
    const modalRef = modalService.open(AdminUpdateManagerComponent)
    return true;
  }

  public openModalManagerRemove(modalService: NgbModal): Boolean {
    const modalRef = modalService.open(AdminRemoveManagerComponent)
    return true;
  }
}
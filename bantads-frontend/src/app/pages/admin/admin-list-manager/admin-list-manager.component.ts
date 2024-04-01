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
  component: Object
}
interface IModalResponse{
  updatedManager: Manager,
  changed: boolean,
  deleted: boolean
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
  public notFilteredManagers: Manager[] = [];

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
    { title: "editar", icon: "bi-pencil-square", buttonStyle: "btn-primary", component: AdminUpdateManagerComponent},
    { title: "remover", icon: "bi-trash-fill", buttonStyle: "btn-danger", component: AdminRemoveManagerComponent}
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
      this.managers = managers;
      this.notFilteredManagers = managers;
    });
  }

  public openModal(modalService: NgbModal, manager: Manager, component: Component): Boolean {
    const modalRef = modalService.open(component, {size: 'xl'});
    modalRef.componentInstance.manager = {...manager};
    modalRef.dismissed.subscribe((res) => {
      let modalRes: IModalResponse = <IModalResponse> res;
      console.log(modalRes);
      console.log(modalRes.updatedManager);
      
      if(modalRes.changed){
        let updateIndex = this.managers.findIndex(manager => manager.id == modalRes.updatedManager.id)
        this.managers[updateIndex] = modalRes.updatedManager;
      } 
      else if (modalRes.deleted){
        let deletedManagerId = manager.id; 
        let deletedManagerIndex: number = this.managers.findIndex(manager => manager.id == deletedManagerId)
        this.managers.splice(deletedManagerIndex, 1);
      }
    });
    return true;
  }

  public filterManager(filterValue: string): void {
    if(filterValue.length > 0) {
      this.managers = this.managers.filter((manager) => manager.cpf?.replace(/(\.)(\-)/, '').match(filterValue) || 
                                                        manager.name?.toLowerCase().match(filterValue.toLowerCase()));
    } else {
      this.managers = this.notFilteredManagers;
    }
  }
}
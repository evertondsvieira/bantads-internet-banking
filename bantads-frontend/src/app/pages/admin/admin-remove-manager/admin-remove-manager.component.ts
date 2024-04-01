import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { Manager } from '../../../models/manager.model';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ManagerService } from '../../../services/manager/manager.service';
import { NgForm } from '@angular/forms';

interface IModalResponse{
  updatedManager: Manager,
  changed: boolean,
  deleted: boolean
}

@Component({
  selector: 'app-admin-remove-manager',
  templateUrl: './admin-remove-manager.component.html',
  styleUrl: './admin-remove-manager.component.css'
})
export class AdminRemoveManagerComponent implements OnInit{
  constructor(public activeModal: NgbActiveModal,
              private managerService: ManagerService) {
  }

  @Input() public manager!: Manager;

  ngOnInit(): void {      
  }
  public deleteManager(manager: Manager): void {
    this.managerService.deleteManager(manager).subscribe((manager)=> {
      this.activeModal.dismiss(<IModalResponse>{"updatedManager": {...manager}, "changed": false, "deleted": true});
    });
  }
}

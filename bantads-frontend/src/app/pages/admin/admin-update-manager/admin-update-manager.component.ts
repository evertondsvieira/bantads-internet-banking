import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ManagerService } from '../../../services/manager/manager.service';
import { Manager } from '../../../models/manager.model';
import { NgForm } from '@angular/forms';

interface IModalResponse{
  updatedManager: Manager,
  changed: boolean,
  deleted: boolean
}

@Component({
  selector: 'app-admin-update-manager',
  templateUrl: './admin-update-manager.component.html',
  styleUrl: './admin-update-manager.component.css'
})
export class AdminUpdateManagerComponent implements OnInit{
  constructor(public activeModal: NgbActiveModal,
              private managerService: ManagerService) {
  }

  @Input() public manager!: Manager;
  @ViewChild('updateManager') formManager!: NgForm;

  ngOnInit(): void {      
  }
  public updateManager(manager: Manager): void {
    this.managerService.updateManager(manager).subscribe((manager)=> {
      this.activeModal.dismiss(<IModalResponse>{"updatedManager": {...manager}, "changed": true, "deleted": false});
    });
  }
}

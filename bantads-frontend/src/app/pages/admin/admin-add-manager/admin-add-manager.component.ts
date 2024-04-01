import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Manager } from '../../../models/manager.model';
import { ManagerService } from '../../../services/manager/manager.service';

interface IStatusMessage {
  status: boolean; // true - ok | false - erro
  message: string;
  style: string;
  icon: string
} 

@Component({
  selector: 'app-admin-add-manager',
  templateUrl: './admin-add-manager.component.html',
  styleUrl: './admin-add-manager.component.css'
})
export class AdminAddManagerComponent implements OnInit {
  constructor(
    private managerService: ManagerService
  ) {
  }

  @ViewChild('managerForm') managerForm!: NgForm;
  public manager: Manager = new Manager();
  public statusMessage: IStatusMessage = {"status": false, "message": "", "style": "", "icon": ""};

  ngOnInit(): void {
  }

  addManager(manager: Manager): void{
    const date = new Date();
    manager.id = date.getTime().toString();
    this.managerService.addManager(manager).subscribe((manager) =>
      this.statusMessage = {"status": true, "message": "Cadastro Realizado com sucesso", "style": "alert alert-success", "icon": "bi bi-check-circle"}
    );
  }

  newRegister(): void {
    this.statusMessage = {"status": false, "message": "", "style": "", "icon": ""};
    this.manager = new Manager();
  }
}

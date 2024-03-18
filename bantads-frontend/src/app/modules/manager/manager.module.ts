import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ManagerHomeComponent } from '../../pages/manager/manager-home/manager-home.component';
import { FormsModule } from '@angular/forms';
import { SharedModule } from '../shared';
import { ManagerApproveComponent } from '../../pages/manager/manager-approve/manager-approve.component';
import { ManagerRejectComponent } from '../../pages/manager/manager-reject/manager-reject.component';



@NgModule({
  declarations: [
    ManagerHomeComponent,
    ManagerApproveComponent,
    ManagerRejectComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    SharedModule
  ]
})
export class ManagerModule { }

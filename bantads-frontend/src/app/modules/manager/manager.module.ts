import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { SharedModule } from '../shared';
import { CommonModule } from '@angular/common';

import { ManagerHomeComponent } from '../../pages/manager/manager-home/manager-home.component';
import { ManagerApproveComponent } from '../../pages/manager/manager-approve/manager-approve.component';
import { ManagerRejectComponent } from '../../pages/manager/manager-reject/manager-reject.component';
import { ManagerConsultAllCustomersComponent } from '../../pages/manager/manager-consult-all-customers/manager-consult-all-customers.component';
import { ManagerConsultCustomersComponent } from '../../pages/manager/manager-consult-customers/manager-consult-customers.component';
import { ManagerConsultTopComponent } from '../../pages/manager/manager-consult-top/manager-consult-top.component';



@NgModule({
  declarations: [
    ManagerHomeComponent,
    ManagerApproveComponent,
    ManagerRejectComponent,
    ManagerConsultCustomersComponent,
    ManagerConsultAllCustomersComponent,
    ManagerConsultTopComponent,
  ],
  imports: [
    CommonModule,
    FormsModule,
    SharedModule
  ]
})
export class ManagerModule { }

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminAddManagerComponent } from '../../pages/admin/admin-add-manager/admin-add-manager.component';
import { AdminCustomerReportComponent } from '../../pages/admin/admin-customer-report/admin-customer-report.component';
import { AdminHomeComponent } from '../../pages/admin/admin-home/admin-home.component';
import { AdminListManagerComponent } from '../../pages/admin/admin-list-manager/admin-list-manager.component';
import { AdminRemoveManagerComponent } from '../../pages/admin/admin-remove-manager/admin-remove-manager.component';
import { AdminUpdateManagerComponent } from '../../pages/admin/admin-update-manager/admin-update-manager.component';
import { MatIconModule } from '@angular/material/icon';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { SharedModule } from '../shared';



@NgModule({
  declarations: [
    AdminAddManagerComponent,
    AdminCustomerReportComponent,
    AdminHomeComponent,
    AdminListManagerComponent,
    AdminRemoveManagerComponent,
    AdminUpdateManagerComponent
  ],
  imports: [
    CommonModule,
    MatIconModule,
    RouterModule,
    FormsModule,
    SharedModule
  ]
})
export class AdminModule { }

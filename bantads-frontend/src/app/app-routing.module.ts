import { RouterModule, Routes } from '@angular/router';
import { ClientHomeComponent } from './pages/client/client-home/client-home.component';
import { ClientCurrentBalanceComponent } from './pages/client/client-current-balance/client-current-balance.component';
import { ClientDepositComponent } from './pages/client/client-deposit/client-deposit.component';
import { ClientStatementComponent } from './pages/client/client-statement/client-statement.component';
import { ClientTransferComponent } from './pages/client/client-transfer/client-transfer.component';
import { ClientUpdateProfileComponent } from './pages/client/client-update-profile/client-update-profile.component';
import { ManagerHomeComponent } from './pages/manager/manager-home/manager-home.component';
import { ManagerConsultAllCustomersComponent } from './pages/manager/manager-consult-all-customers/manager-consult-all-customers.component';
import { ManagerConsultCustomersComponent } from './pages/manager/manager-consult-customers/manager-consult-customers.component';
import { ManagerConsultTopComponent } from './pages/manager/manager-consult-top/manager-consult-top.component';
import { ManagerApproveComponent } from './pages/manager/manager-approve/manager-approve.component';
import { ManagerRejectComponent } from './pages/manager/manager-reject/manager-reject.component';
import { AdminHomeComponent } from './pages/admin/admin-home/admin-home.component';
import { AdminListManagerComponent } from './pages/admin/admin-list-manager/admin-list-manager.component';
import { AdminCustomerReportComponent } from './pages/admin/admin-customer-report/admin-customer-report.component';
import { AdminAddManagerComponent } from './pages/admin/admin-add-manager/admin-add-manager.component';
import { AdminRemoveManagerComponent } from './pages/admin/admin-remove-manager/admin-remove-manager.component';
import { AdminUpdateManagerComponent } from './pages/admin/admin-update-manager/admin-update-manager.component';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { ClientWithdrawlComponent } from './pages/client/client-withdrawl/client-withdrawl.component';
import { NgModule } from '@angular/core';

export const clientRoutes: Routes = [
  { path: "home", component: ClientHomeComponent },
  { path: "current/balance", component: ClientCurrentBalanceComponent },
  { path: "deposit", component: ClientDepositComponent },
  { path: "statement", component: ClientStatementComponent },
  { path: "transfer", component: ClientTransferComponent },
  { path: "update/profile", component: ClientUpdateProfileComponent },
  { path: "withdrawl", component: ClientWithdrawlComponent },
]

export const managerRoutes: Routes = [
  { path: "manager/home", component: ManagerHomeComponent },
  { path: "manager/consult/all", component: ManagerConsultAllCustomersComponent },
  { path: "manager/consult/customers", component: ManagerConsultCustomersComponent },
  { path: "manager/consult/top", component: ManagerConsultTopComponent },
  { path: "manager/approve", component: ManagerApproveComponent },
  { path: "manager/reject", component: ManagerRejectComponent }, 
]

export const adminRoutes: Routes = [
  { path: "admin/home", component: AdminHomeComponent },
  { path: "admin/customer/report", component: AdminCustomerReportComponent },
  { path: "admin/add/manager", component: AdminAddManagerComponent },
  { path: "admin/remove/manager", component: AdminRemoveManagerComponent },
  { path: "admin/list/manager", component: AdminListManagerComponent },
  { path: "admin/update/manager", component: AdminUpdateManagerComponent },
]

export const routes: Routes = [
  { path: "", redirectTo: "login", pathMatch: "full" },
  { path: "login", component: LoginComponent },
  { path: "register", component: RegisterComponent },
  { path: "", children: [
    ...clientRoutes,
    ...managerRoutes,
    ...adminRoutes,
    { path: "**", redirectTo: "/login" }
  ]},
]

NgModule({
  imports: [
    RouterModule.forRoot(routes, {
      scrollOffset: [0, 0],
    }),
  ],
  exports: [RouterModule],
})
export class AppRoutingModule {}

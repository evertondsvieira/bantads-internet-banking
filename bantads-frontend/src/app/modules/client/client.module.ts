import { NgModule } from '@angular/core';
import { SharedModule } from '../shared/shared.module';

import { ClientHomeComponent } from '../../pages/client/client-home/client-home.component';
import { ClientCurrentBalanceComponent } from '../../pages/client/client-current-balance/client-current-balance.component';
import { ClientDepositComponent } from '../../pages/client/client-deposit/client-deposit.component';
import { ClientStatementComponent } from '../../pages/client/client-statement/client-statement.component';
import { ClientTransferComponent } from '../../pages/client/client-transfer/client-transfer.component';
import { ClientUpdateProfileComponent } from '../../pages/client/client-update-profile/client-update-profile.component';
import { ClientWithdrawlComponent } from '../../pages/client/client-withdrawl/client-withdrawl.component';

import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { MatIconModule } from '@angular/material/icon';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    ClientHomeComponent,
    ClientCurrentBalanceComponent,
    ClientDepositComponent,
    ClientStatementComponent,
    ClientTransferComponent,
    ClientUpdateProfileComponent,
    ClientWithdrawlComponent
  ],
  imports: [
    MatIconModule,
    CommonModule,
    RouterModule,
    FormsModule,
    SharedModule
  ],
  exports: [],
})
export class ClientModule { }

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ModalTransactionComponent } from './modal-transaction/modal-transaction.component';
import { SharedModule } from '../../modules/shared';



@NgModule({
  declarations: [
    ModalTransactionComponent
  ],
  imports: [
    CommonModule,
    SharedModule
  ]
})
export class ModalModule { }

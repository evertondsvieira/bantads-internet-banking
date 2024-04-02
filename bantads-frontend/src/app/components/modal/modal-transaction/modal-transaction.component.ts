import { Component, Input } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Transaction } from '../../../models/transaction.model';
import { TransactionService } from '../../../services/transaction/transaction.service';

@Component({
  selector: 'app-modal-transaction',
  templateUrl: './modal-transaction.component.html',
  styleUrl: './modal-transaction.component.css'
})

export class ModalTransactionComponent {
  @Input() transaction!: Transaction;

  constructor(
    public activeModal: NgbActiveModal,
    private transactionService: TransactionService
  ) {}

  ngOnInit(): void{
  }

  createTransaction(transaction: Transaction): void {
    this.transactionService.createTransaction(transaction).subscribe(
      transaction => {
        this.activeModal.close();
      }
    );
  }
}
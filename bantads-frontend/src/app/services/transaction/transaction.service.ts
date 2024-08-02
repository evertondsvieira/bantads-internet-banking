import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Transaction } from '../../models/transaction.model';

@Injectable({
  providedIn: 'root'
})
export class TransactionService {
  private BASE_URL: string = 'http://localhost:3000/transaction';

  constructor(private http: HttpClient) {}

  public createTransaction(transaction: Transaction): Observable<Transaction> {
    return this.http.post<Transaction>(this.BASE_URL, transaction);
  }

  public getTransactions(): Observable<Transaction[]> {
    return this.http.get<Transaction[]>(this.BASE_URL)
  }
}

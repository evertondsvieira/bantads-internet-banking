import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Transaction } from '../../models/transaction.model';

@Injectable({
  providedIn: 'root'
})
export class TransactionService {
  private BASE_URL: string = 'http://localhost:3000/transaction';
  private httpOptions = {
    headers: new HttpHeaders({
      'content-type': 'application/json',
    }),
  };

  constructor(private http: HttpClient) {}

  public createTransaction(transaction: Transaction): Observable<Transaction> {
    return this.http.post<Transaction>(
      this.BASE_URL,
      JSON.stringify(transaction),
      this.httpOptions
    );
  }
}

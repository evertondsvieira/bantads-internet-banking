import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Account } from '../../models/account.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AccountService {
  private BASE_URL: string = 'http://localhost:3000/';

  constructor(private http: HttpClient) {}

  public getAccounts(): Observable<Account[]> {
    return this.http.get<Account[]>(this.BASE_URL + 'account')
  }

  public getAccountById(id: number): Observable<Account> {
    return this.http.get<Account>(this.BASE_URL + `account/${id}`);
  }

  public getAccountByCPF(query: string): Observable<Account[]> {
    return this.http.get<Account[]>(this.BASE_URL + `account?client_cpf=${query}`)
  }

  public getAccountByUserId(userId: number): Observable<Account> {
    return this.http.get<Account>(this.BASE_URL + `account/by-user/${userId}`);
  }

  public updateAccount(account: Account): Observable<Account> {
    return this.http.put<Account>(this.BASE_URL + `account/${account.id}`, account)
  }

  getTransactions(id: number, start: string, end: string): Observable<any[]> {
    let params = new HttpParams()
      .set('start', start)
      .set('end', end)
    return this.http.get<any[]>(this.BASE_URL + `transaction/account/${id}`, { params });
  }
}

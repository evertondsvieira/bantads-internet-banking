import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Account } from '../../models/account.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AccountService {
  private BASE_URL: string = 'http://localhost:3000/';
  private httpOptions = {
    headers: new HttpHeaders({
      'content-type': 'application/json',
    }),
  };

  constructor(private http: HttpClient) {}

  public getAccountById(query: string): Observable<Account> {
    return this.http.get<Account>(
      this.BASE_URL + `/${query}`,
      this.httpOptions);
  }
  public getAccountByCPF(query: string): Observable<Account[]> {
    return this.http.get<Account[]>(
      this.BASE_URL + `?cpf=${query}`,
      this.httpOptions);
  }
  public updateAccount(account: Account): Observable<Account> {
    return this.http.put<Account>(
      this.BASE_URL + `/${account.id}`,
      JSON.stringify(account),
      this.httpOptions
    );
  }


}

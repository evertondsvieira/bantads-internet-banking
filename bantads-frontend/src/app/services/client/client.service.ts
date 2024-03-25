import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Client } from '../../models/client.model';
import { Account } from '../../models/account.model';
import { Observable } from 'rxjs';
@Injectable({
  providedIn: 'root',
})
export class ClientService {
  private BASE_URL: string = 'http://localhost:3000/client';
  private httpOptions = {
    headers: new HttpHeaders({
      'content-type': 'application/json',
    }),
  };

  constructor(private http: HttpClient) {}

  public getClientsWithOpenSituation(): Observable<Client[]> {
    return this.http.get<Client[]>(
      this.BASE_URL + '?situation=OPEN',
      this.httpOptions
    );
  }
  public getClientsOrderedByName(): Observable<Client[]> {
    return this.http.get<Client[]>(
      this.BASE_URL + '?_sort=name',
      this.httpOptions
    );
  }

  public searchClients(query: string): Observable<Client[]> {
    return this.http.get<Client[]>(`${this.BASE_URL}?q=${query}`, this.httpOptions);
  }

  public getClientByCPF(cpf: string): Observable<Client> {
    return this.http.get<Client>(
      `${this.BASE_URL}?cpf=${cpf}`,
      this.httpOptions
    );
  }
  
  public searchById(query: string): Observable<Client> {
    return this.http.get<Client>(
      this.BASE_URL + `/${query}`,
      this.httpOptions);
  }

  public searchByCpf(query: string): Observable<Client[]> {
    return this.http.get<Client[]>(
      this.BASE_URL + `?cpf=${query}`,
      this.httpOptions);
  }
  
  public getAccountByCPF(cpf: string): Observable<Account> {
    return this.http.get<Account>(
      `http://localhost:3000/account?cpf=${cpf}`,
      this.httpOptions
    );
  }
}

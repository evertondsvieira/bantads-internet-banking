import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Client } from '../../models/client.model';
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
  public getClientByCPF(query: string): Observable<Client[]> {
    return this.http.get<Client[]>(
      this.BASE_URL + `?cpf=${query}`,
      this.httpOptions);
  }
  public updateClient(client: Client): Observable<Client> {
    return this.http.put<Client>(
      this.BASE_URL + `/${client.id}`,
      JSON.stringify(client),
      this.httpOptions
    );
  }
  public getClientById(query: string): Observable<Client> {
    return this.http.get<Client>(
      this.BASE_URL + `/${query}`,
      this.httpOptions);
  }
}

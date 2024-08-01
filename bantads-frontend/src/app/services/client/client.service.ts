import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Client } from '../../models/client.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ClientService {
  private BASE_URL: string = 'http://localhost:3000/client';

  constructor(private http: HttpClient) {}

  public getClientsWithOpenSituation(): Observable<Client[]> {
    return this.http.get<Client[]>(`${this.BASE_URL}?situation=OPEN`);
  }

  public getClientsOrderedByName(): Observable<Client[]> {
    return this.http.get<Client[]>(`${this.BASE_URL}?_sort=name`);
  }

  public searchClients(query: string): Observable<Client[]> {
    if (isNaN(Number(query))) {
      return this.http.get<Client[]>(`${this.BASE_URL}?name=${query}`);
    }
    return this.http.get<Client[]>(`${this.BASE_URL}?cpf=${query}`);
  }

  public getClientByCPF(query: string): Observable<Client[]> {
    return this.http.get<Client[]>(`${this.BASE_URL}?cpf=${query}`);
  }

  public updateClient(client: Client): Observable<Client> {
    const { id, cpf, ...clientToUpdate } = client;
    return this.http.put<Client>(`${this.BASE_URL}/${client.id}`, clientToUpdate);
  }

  public getClientById(id: Client['id']): Observable<Client> {
    return this.http.get<Client>(`${this.BASE_URL}/${id}`);
  }

  public addClient(client: Client): Observable<Client> {
    return this.http.post<Client>(this.BASE_URL, client);
  }
}

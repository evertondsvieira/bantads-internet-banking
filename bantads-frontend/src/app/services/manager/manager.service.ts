import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Manager } from '../../models/manager.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})

export class ManagerService {
  private BASE_URL: string = 'http://localhost:3000/manager';
  private httpOptions = {
    headers: new HttpHeaders({
      'content-type': 'application/json',
    }),
  };

  constructor(private http: HttpClient) {}

  public getManagersOrderedByName(): Observable<Manager[]> {
    return this.http.get<Manager[]>(
      this.BASE_URL,
      this.httpOptions
    );
  }

  public addManager(manager: Manager): Observable<Manager>{
    return this.http.post<Manager>(
      this.BASE_URL,
      JSON.stringify(manager),
      this.httpOptions
    )
  }

  public updateManager(manager: Manager): Observable<Manager>{
    return this.http.put<Manager>(
      this.BASE_URL + `/${manager.email}`,
      JSON.stringify(manager),
      this.httpOptions
    )
  }

  public deleteManager(manager: Manager): Observable<Manager>{
    return this.http.delete<Manager>(
      this.BASE_URL + `/${manager.email}`,
      this.httpOptions
    )
  }
}

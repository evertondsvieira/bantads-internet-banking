import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Auth } from '../../models/auth.model';
import { User } from '../../models/user.model';

@Injectable({
  providedIn: 'root'
})

export class UserService {
  private BASE_URL: string = "http://localhost:3000";
  private httpOptions = {
    headers: new HttpHeaders({
      'content-type': 'application/json'
    })
  };

  constructor(private http: HttpClient) { }

  public(): Observable<User[]>{
    return this.http.get<User[]>(this.BASE_URL,
                                          this.httpOptions);
  }
  
  public buscarPorId(id: number): Observable<User>{
    return this.http.get<User>(this.BASE_URL + id,
                                        this.httpOptions);
  }

  public inserir(user: User){
    return this.http.post<User>(this.BASE_URL+"/cadastro",
                                   JSON.stringify(user),
                                   this.httpOptions)
  }

  public remover(id: number): Observable<User>{
    return this.http.delete<User>(this.BASE_URL + `/${id}`,
                                           this.httpOptions)
  }

  public alterar(user: User): Observable<User>{
    return this.http.put<User>(this.BASE_URL + `/${user.id}`,
                                        JSON.stringify(user),
                                        this.httpOptions)
  }

  // Função de login REAL
  // public login(auth: Auth): Observable<User>{
  //   return this.http.post<User>(`${this.BASE_URL}/auth`,
  //                                 JSON.stringify(auth),
  //                                 this.httpOptions);
  // }

  // remover
  // Função login fake
  public login(auth: Auth): Observable<User>{
    return this.http.get<User>(`${this.BASE_URL}/user?email=${auth.email}`);
  }
}

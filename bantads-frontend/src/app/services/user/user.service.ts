import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
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

  public getUsers(): Observable<User[]>{
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

  public login(auth: Auth): Observable<User> {
    return this.http.post<any>(`${this.BASE_URL}/login`, JSON.stringify(auth), this.httpOptions)
      .pipe(
        map(response => {
          const userData = response.data.user;
          const token = response.token; // Extrai o token da resposta
  
          // Armazena o token em localStorage
          localStorage.setItem('authToken', token);
  
          // Cria e retorna o objeto User
          return new User(
            undefined,
            undefined,
            userData.login,
            undefined,
            userData.role
          );
        }),
        catchError(error => {
          if (error.error && error.error.message) {
            return throwError(error.error.message);
          }
          return throwError('Erro ao fazer login. Tente novamente mais tarde.');
        })
      );
  }

  public logout(): void {
    this.http.post<any>(`${this.BASE_URL}/logout`, {}, this.httpOptions)
      .pipe(
        map(response => {
          // Remove o token do localStorage ao fazer logout
          localStorage.removeItem('authToken');
          console.log('Logout realizado com sucesso:', response);
        }),
      ).subscribe();
  }
}
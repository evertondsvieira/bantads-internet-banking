import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpResponse } from '@angular/common/http';
import { Observable, of, BehaviorSubject } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';
import { jwtDecode } from 'jwt-decode';
import { HttpClient } from '@angular/common/http';
import { UserServiceId } from './userId.service';
import { Client } from '../../models/client.model';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  private tokenSubject = new BehaviorSubject<string | null>(null)

  constructor(private http: HttpClient, private userService: UserServiceId) {
    const token = localStorage.getItem('authToken')
    this.tokenSubject.next(token)
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const authToken = this.tokenSubject.value

    if (authToken) {
      console.log('Token encontrado:', authToken)

      const authReq = req.clone({
        setHeaders: {
          Authorization: `Bearer ${authToken}`
        }
      })

      return next.handle(authReq).pipe(
        tap(event => {
          if (event instanceof HttpResponse && event.body && event.body.token) {
            const token = event.body.token
            const decodedToken: any = jwtDecode(token)

            localStorage.setItem('authToken', token)
            this.tokenSubject.next(token)

            const email = decodedToken.login
            this.getUserByEmail(email).subscribe(user => {
              this.userService.setUserId(user.id)
            })
          }
        }),
        catchError(error => {
          console.error('Erro ao processar a requisição:', error)
          return of(error)
        })
      )
    } else {
      console.log('Nenhum token encontrado.')
      return next.handle(req)
    }
  }

  private getUserByEmail(email: Client['email']): Observable<any> {
    const url = `http://localhost:3000/client/email/${email}`
    return this.http.get<any>(url)
  }
}

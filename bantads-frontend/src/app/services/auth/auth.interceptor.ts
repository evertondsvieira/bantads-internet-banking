import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpResponse } from '@angular/common/http';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';
import { jwtDecode } from 'jwt-decode';
import { HttpClient } from '@angular/common/http';
import { UserServiceId } from './userId.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  private tokenSubject = new BehaviorSubject<string | null>(null);

  constructor(private http: HttpClient, private userServiceId: UserServiceId) {
    const token = localStorage.getItem('authToken');
    this.tokenSubject.next(token);
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const authToken = this.tokenSubject.value;

    if (authToken) {
      const authReq = req.clone({
        setHeaders: {
          Authorization: `Bearer ${authToken}`
        }
      });

      return next.handle(authReq).pipe(
        tap(event => {
          if (event instanceof HttpResponse && event.body && event.body.token) {
            const token = event.body.token;
            const decodedToken: any = jwtDecode(token);

            localStorage.setItem('authToken', token);
            this.tokenSubject.next(token);

            const email = decodedToken.login;
            this.http.get<any>(`http://localhost:3000/client/email/${email}`).subscribe(user => {
              localStorage.setItem('userId', user.id);
              this.userServiceId.setUserId(user.id);
            });
          }
        }),
        catchError(error => {
          console.error('Erro ao processar a requisição:', error);
          return of(error);
        })
      );
    } else {
      return next.handle(req);
    }
  }
}

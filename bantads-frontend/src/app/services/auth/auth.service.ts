import { Injectable } from '@angular/core';
import { Observable, filter, map, of} from 'rxjs';
import { Auth } from '../../models/auth.model';
import { User } from '../../models/user.model';
import { UserService } from '../user/user.service';
import { HttpClient } from '@angular/common/http';

const LS_CHAVE: string = "loggedUser";

@Injectable({
  providedIn: 'root'
})

export class AuthService {
  private user?: User;
  constructor(
    private http: HttpClient,
    private userService: UserService) {
  }
  
  public get loggedUser() : User {
    let user = sessionStorage[LS_CHAVE];
    return (user ? JSON.parse(user) : null);
  }
  
  public set loggedUser(user : User) {
    sessionStorage[LS_CHAVE] = JSON.stringify(user);
  }

  public logout(){
    this.userService.logout();
    delete sessionStorage[LS_CHAVE];
  }
  
  public login(auth: Auth): Observable<User | undefined>{
    let observableUser = this.userService.login(auth);
    return observableUser;
  }

  getUserByEmail(email: string): Observable<User> {
    return this.http.get<User>(`http://localhost:3000/client/email/${email}`);
  }
}
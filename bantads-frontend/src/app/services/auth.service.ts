import { Injectable } from '@angular/core';
import { Observable, filter, map, of } from 'rxjs';
import { Auth } from '../models/auth.model';
import { User } from '../models/user.model';
import { UserService } from './user.service';
import { DOCUMENT } from '@angular/common';
import { Inject } from '@angular/core';

const LS_CHAVE: string = "loggedUser";

@Injectable({
  providedIn: 'root'
})

export class AuthService {
  private user?: User;
  constructor(private userService: UserService, 
              @Inject(DOCUMENT) private document: Document) {
    const sessionStorage = document.defaultView?.sessionStorage;
  }
  public get loggedUser() : User {
    let user = sessionStorage[LS_CHAVE];
    return (user ? JSON.parse(user) : null);
  }
  
  public set loggedUser(user : User) {
    sessionStorage[LS_CHAVE] = JSON.stringify(user);
  }

  public logout(){
    delete sessionStorage[LS_CHAVE];
  }
  
  public login(auth: Auth): Observable<User | undefined>{
    let observableUser = this.userService.login(auth);
    return observableUser;
  }
  
}

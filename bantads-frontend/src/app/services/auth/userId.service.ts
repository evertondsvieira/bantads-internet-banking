import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserServiceId {
  private userIdSubject = new BehaviorSubject<string | null>(null);

  constructor() {
    const storedUserId = localStorage.getItem('userId');
    this.userIdSubject.next(storedUserId);
  }

  getUserId(): Observable<string | null> {
    return this.userIdSubject.asObservable();
  }

  setUserId(userId: string | null): void {
    this.userIdSubject.next(userId);
    if (userId) {
      localStorage.setItem('userId', userId);
    } else {
      localStorage.removeItem('userId');
    }
  }
}

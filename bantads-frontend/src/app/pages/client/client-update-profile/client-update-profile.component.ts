import { Component } from '@angular/core';
import { User } from '../../../models/user.model';

@Component({
  selector: 'app-client-update-profile',
  templateUrl: './client-update-profile.component.html',
  styleUrl: './client-update-profile.component.css'
})
export class ClientUpdateProfileComponent {
  public user: User = new User(1, 'Jane Doe', 'jane@example.com', '12345678909', 'cliente');
  public saldo: number = 10000;
  public valueVisible: boolean = false;

  toggleValue(): void{
    this.valueVisible = !this.valueVisible;
  }
}

import { Component } from '@angular/core';
import { User } from '../../../models/user.model';
import { Manager } from '../../../models/manager.model';
import { AuthService } from '../../../services/auth/auth.service';
import { ManagerService } from '../../../services/manager/manager.service';

interface managerData {
  name: string;
  quantity: string;
  positiveBalance: string;
  negativeBalance: string;
}

@Component({
  selector: 'app-admin-home',
  templateUrl: './admin-home.component.html',
  styleUrl: './admin-home.component.css'
})

export class AdminHomeComponent {

  managerData: managerData[] = [
    {
      name: "Petyr Baelish",
      quantity: "10",
      positiveBalance: "1000",
      negativeBalance: "500"
    },
    {
      name: "Jon Snow",
      quantity: "5",
      positiveBalance: "800",
      negativeBalance: "200"
    },
    {
      name: "Daenerys Targaryen",
      quantity: "7",
      positiveBalance: "1200",
      negativeBalance: "300"
    },
    {
      name: "Tyrion Lannister",
      quantity: "3",
      positiveBalance: "600",
      negativeBalance: "100"
    }
  ];

  private _user: User = this.authService.loggedUser;
  private _managers!: Manager[];

  constructor(
    private authService: AuthService,
    private managerService: ManagerService
  ) {}
  public get managers(): Manager[] {
    return this._managers;
  }
  public set managers(value: Manager[]) {
    this._managers = value;
  }

  public get user(): User {
    return this._user;
  }
  public set user(user: User) {
    this._user = user;
  }

  ngOnInit(): void {
    this._user = this.authService.loggedUser;
    this.getManagersOrderedByName();
    
  }

  public getManagersOrderedByName() {
    this.managerService.getManagersOrderedByName().subscribe((managers) => {
      this.managers = managers;
    });
  }

}

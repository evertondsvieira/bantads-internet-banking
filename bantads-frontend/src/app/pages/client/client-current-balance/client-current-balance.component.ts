import { Component } from '@angular/core';

@Component({
  selector: 'app-client-current-balance',
  templateUrl: './client-current-balance.component.html',
  styleUrl: './client-current-balance.component.css'
})
export class ClientCurrentBalanceComponent {
  public saldo: number = 10000;
  public valueVisible: boolean = false;

  toggleValue(): void{
    this.valueVisible = !this.valueVisible;
  }

}

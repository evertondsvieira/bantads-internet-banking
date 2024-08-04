import { Component, OnInit } from '@angular/core';
import { AccountService } from '../../../services/account/account.service';
import { ActivatedRoute } from '@angular/router';
import { Account } from '../../../models/account.model';

@Component({
  selector: 'app-client-current-balance',
  templateUrl: './client-current-balance.component.html',
  styleUrl: './client-current-balance.component.css',
})
export class ClientCurrentBalanceComponent implements OnInit {
  public accountData!: Account;
  public valueVisible: boolean = false;
  
  public userId: number = 0

  constructor(
    private accountService: AccountService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const idParam = params.get("id")

      if (idParam && !isNaN(+idParam)) {
        this.userId = +idParam
        this.loadAccount()
      } else {
        console.log("Id do cliente é inválido")
      }
    })
  }

  loadAccount(): void {
    this.getAccount();
  }

  getAccount(): void {
    if (this.userId) {
      this.accountService.getAccountByUserId(this.userId).subscribe({
        next: (account: Account) => {
          this.accountData = account
        },
        error: (error) => {
          console.error('Erro ao carregar cliente', error)
        }
      })
    } else {
      console.error('ID do cliente não fornecido')
    }
  }

  toggleValue(): void {
    this.valueVisible = !this.valueVisible;
  }
}

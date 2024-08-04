import { ActivatedRoute } from '@angular/router';
import { Account } from '../../../models/account.model';
import { AccountService } from './../../../services/account/account.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-client-statement',
  templateUrl: './client-statement.component.html',
  styleUrl: './client-statement.component.css'
})
export class ClientStatementComponent implements OnInit {
  userId: number = 0
  accountData!: Account

  start: string = new Date().toISOString().split('T')[0]
  end: string = new Date().toISOString().split('T')[0]
  currentDate: Date = new Date()

  statements: any[] = []

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
        this.loadStatements()
      } else {
        console.log("Id do cliente é inválido")
      }
    })
  }

  loadAccount(): void {
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

  loadStatements(): void {
    if (this.start && this.end) {
      this.accountService.getTransactions(this.accountData.id, this.start, this.end).subscribe({
        next: (response) => {
          this.statements = response
          
          this.statements = this.statements.map(item => ({
            ...item,
            createdAt: new Date(item.createdAt).toISOString()
          }));
          
          console.log('Filtros aplicados com sucesso', response)
        },
        error: (error) => {
          console.error('Erro ao carregar transações', error)
        }
      })
    }
  }

  getBalance(): number {
    return this.statements.reduce(
      (acc, current) => acc + current.ammount,
      0
    );
  }
}

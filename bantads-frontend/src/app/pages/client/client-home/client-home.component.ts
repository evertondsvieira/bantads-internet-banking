import { Component } from '@angular/core';

interface IFinancialBalance {
  title: string
  value: number
}

interface IOperation {
  title: string;
  icon: string;
  link: string;
}

interface ITransaction {
  title: string;
  type: 'Receitas' | 'Despesas';
  value: number;
}

@Component({
  selector: 'app-client-home',
  templateUrl: './client-home.component.html',
  styleUrl: './client-home.component.css'
})
export class ClientHomeComponent {
  operations: IOperation[] = [
    { title: 'Depositar', icon: 'attach_money', link: '/deposit' },
    { title: 'Sacar', icon: 'money_off', link: '/withdrawl' },
    { title: 'Transferir', icon: 'compare_arrows', link: '/transfer' },
    { title: 'Extrato', icon: 'description', link: '/statement' },
  ]

  balances: IFinancialBalance[] = [
    { title: 'Receita', value: 50 },
    { title: 'Despesas', value: 100 },
    { title: 'Saldo Atual', value: -100 },
  ]

  transactions: ITransaction[] = [
    { title: 'Salário', type: 'Receitas', value: 100 },
    { title: 'Parcela do carro', type: 'Despesas', value: 100 },
    { title: 'Parcela do carro', type: 'Despesas', value: 100 },
  ]

  filteredTransactions: ITransaction[] = [];

  constructor() {
    this.filteredTransactions = this.transactions;
  }

  filterRecipes(type: 'Receitas' | 'Despesas' | 'Todos') {
    if (type === 'Todos') {
      this.filteredTransactions = this.transactions;
    } else {
      this.filteredTransactions = this.transactions.filter(item => {
        return item.type === type;
      })
    }
  }

  getBalanceColor(value: number) {
    return value < 0 ? 'text-danger' : 'text-dark';
  }

  getTransactionColor(value: number, type: string) {
    if (type === 'Despesas') {
      return 'text-danger'
    }
  
    if (value < 0) {
      return 'text-danger'
    }

    return 'text-success'
  }
}

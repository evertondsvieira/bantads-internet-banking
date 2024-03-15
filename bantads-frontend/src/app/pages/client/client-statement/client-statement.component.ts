import { Component } from '@angular/core';

@Component({
  selector: 'app-client-statement',
  templateUrl: './client-statement.component.html',
  styleUrl: './client-statement.component.css'
})
export class ClientStatementComponent {
  currentDate: Date = new Date()
  initialValue = 0
  start: string = ''
  end: string = ''


  statements = [
    { id: 1, timestamp: '2024-03-11T10:30:45', operation: 'Transferência', origin: 'Cliente A', destiny: 'Cliente B', value: -100.00 },
    { id: 2, timestamp: '2024-03-12T11:45:30', operation: 'Depósito', origin: 'Cliente A', destiny: '', value: 150.00 },
    { id: 3, timestamp: '2024-03-13T15:20:10', operation: 'Saque', origin: 'Cliente A', destiny: '', value: -30.00 },
    { id: 4, timestamp: '2024-03-14T14:05:55', operation: 'Depósito', origin: 'Cliente A', destiny: '', value: 50.00 },
  ]

  getBalance(): number {
    return this.statements.reduce(
      (acc, current) => acc + current.value,
      this.initialValue
    )
  }

  filterStatements(start: string, end: string): any[] {
    const filteredStatements = this.statements.filter(statement => {
      const statementDate = new Date(statement.timestamp)
      const startDateObj = new Date(start)
      const endDateObj = new Date(end)
      return statementDate >= startDateObj && statementDate <= endDateObj
    })
    console.log({ start, end })

    return filteredStatements
  }
}

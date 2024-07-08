export class Transaction {
  constructor(
    public dateTime: Date,
    public type: 'Deposit' | 'Withdrawl' | 'Transfer',
    public amount?: number,
    public originAccount?: string,
    public destinationAccount?: string) {
  }

}

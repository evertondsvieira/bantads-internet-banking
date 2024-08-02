export class Transaction {
  constructor(
    public type: string,
    public ammount?: number,
    public originAccountId?: number,
    public destinationAccountId?: number
  ) {}
}
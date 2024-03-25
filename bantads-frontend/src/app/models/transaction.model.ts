export class Transaction {
  constructor(
    private _dateTime: Date,
    private _type: 'Deposit' | 'Withdrawl' | 'Transfer',
    private _amount: number,
    private _originAccount?: string,
    private _destinationAccount?: string) {
  }
  public get dateTime(): Date {
    return this._dateTime;
  }
  public set dateTime(value: Date) {
    this._dateTime = value;
  }
  public get type(): 'Deposit' | 'Withdrawl' | 'Transfer' {
    return this._type;
  }
  public set type(value: 'Deposit' | 'Withdrawl' | 'Transfer') {
    this._type = value;
  }
  public get amount(): number {
    return this._amount;
  }
  public set amount(value: number) {
    this._amount = value;
  }
  public get originAccount(): string | undefined {
    return this._originAccount;
  }
  public set originAccount(value: string | undefined) {
    this._originAccount = value;
  }
  public get destinationAccount(): string | undefined {
    return this._destinationAccount;
  }
  public set destinationAccount(value: string | undefined) {
    this._destinationAccount = value;
  }
}

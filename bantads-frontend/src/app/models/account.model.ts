import { Client } from './client.model';

export class Account {
  constructor(
    private _client: Client,
    private _accountId: string,
    private _creationDate: Date,
    private _limite: number
  ) {}

  public get client(): Client {
    return this._client;
  }
  public set client(value: Client) {
    this._client = value;
  }
  public get accountId(): string {
    return this._accountId;
  }
  public set accountId(value: string) {
    this._accountId = value;
  }
  public get creationDate(): Date {
    return this._creationDate;
  }
  public set creationDate(value: Date) {
    this._creationDate = value;
  }
  public get limite(): number {
    return this._limite;
  }
  public set limite(value: number) {
    this._limite = value;
  }
}

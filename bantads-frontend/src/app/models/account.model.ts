import { Client } from './client.model';

export class Account {
  constructor(
    private _id: number,
    private _client: Client,
    private _client_name: string,
    private _client_cpf: string,
    private _accountId: string,
    private _creationDate: Date,
    private _limite: number,
    private _balance: number
  ) {}

  public get id(): number {
    return this._id;
  }
  public set id(value: number) {
    this._id = value;
  }
  public get client(): Client {
    return this._client;
  }
  public set client(value: Client) {
    this._client = value;
  }
  public get client_name(): string {
    return this._client_name;
  }
  public set client_name(value: string) {
    this._client_name = value;
  }
  public get client_cpf(): string {
    return this._client_cpf;
  }
  public set client_cpf(value: string) {
    this._client_cpf = value;
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
  public get balance(): number {
    return this._balance;
  }
  public set balance(value: number) {
    this._balance = value;
  }
}

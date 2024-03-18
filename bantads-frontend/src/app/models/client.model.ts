import { Situation } from "./enum/situation.enum";

export class Client{
  constructor(
    private _id: string,
    private _name: string,
    private _cpf: string,
    private _accountId: string,
    private _salary: number,
    private _situation: Situation 
  ) {}

  public get situation(): Situation {
    return this._situation;
  }
  public set situation(value: Situation) {
    this._situation = value;
  }
  public get salary(): number {
    return this._salary;
  }
  public set salary(value: number) {
    this._salary = value;
  }
  public get accountId(): string {
    return this._accountId;
  }
  public set accountId(value: string) {
    this._accountId = value;
  }
  public get cpf(): string {
    return this._cpf;
  }
  public set cpf(value: string) {
    this._cpf = value;
  }
  public get name(): string {
    return this._name;
  }
  public set name(value: string) {
    this._name = value;
  }
  public get id(): string {
    return this._id;
  }
  public set id(value: string) {
    this._id = value;
  }
}
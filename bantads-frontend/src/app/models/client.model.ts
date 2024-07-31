import { Address } from './address.model';
import { Situation } from './enum/situation.enum';

export class Client {
  private _id: number;
  private _name: string;
  private _email: string;
  private _cpf: string;
  private _address: Address;
  private _phone: string;
  private _salary: number;
  private _situation: Situation;

  constructor(
    id: number,
    name: string,
    email: string,
    cpf: string,
    address: Address,
    phone: string,
    salary: number,
    situation: Situation,
  ) {
    this._id = id;
    this._name = name;
    this._email = email;
    this._cpf = cpf;
    this._address = address;
    this._phone = phone;
    this._salary = salary;
    this._situation = situation
  }

  public get id(): number {
    return this._id;
  }

  public set id(value: number) {
    this._id = value;
  }

  public get name(): string {
    return this._name;
  }

  public set name(value: string) {
    this._name = value;
  }

  public get email(): string {
    return this._email;
  }

  public set email(value: string) {
    this._email = value;
  }

  public get cpf(): string {
    return this._cpf;
  }

  public set cpf(value: string) {
    this._cpf = value;
  }

  public get address(): Address {
    return this._address;
  }

  public set address(value: Address) {
    this._address = value;
  }

  public get phone(): string {
    return this._phone;
  }

  public set phone(value: string) {
    this._phone = value;
  }

  public get salary(): number {
    return this._salary;
  }

  public set salary(value: number) {
    this._salary = value;
  }

  public get situation(): Situation {
    return this._situation;
  }

  public set situation(value: Situation) {
    this._situation = value;
  }
  
  public toClientObject(): any {
    return {
      id: this._id,
      name: this._name,
      email: this._email,
      cpf: this._cpf,
      address: this._address, 
      phone: this._phone,
      salary: this._salary,
      situation: this._situation
    };
  }
}

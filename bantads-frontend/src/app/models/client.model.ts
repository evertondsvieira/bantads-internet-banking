import { Address } from './address.model';
import { Situation } from './enum/situation.enum';

export class Client {
  constructor(
    public _address: Address,
    public id: string,
    public name: string,
    public email: string,
    public cpf: string,
    public phone: number,
    public salary: number,
    public accountId: string,
    public balance: number,
    public situation?: Situation, 
  ) {}

  public get address(): Address {
    return this._address;
  }

  public setAddress(address: Address) {
    this._address = address;
  }

  public setSituation(situation: Situation) {
    this.situation = situation;
  }

}


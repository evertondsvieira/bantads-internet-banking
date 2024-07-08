import { Address } from './address.model';
import { Situation } from './enum/situation.enum';

export class Client {
  constructor(
    public id: string,
    public name: string,
    public email: string,
    public cpf: string,
    public address: Address,
    public phone: number,
    public salary: number,
    public accountId: string,
    public balance: number,
    public situation: Situation,
  ) {}
}

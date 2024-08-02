import { Client } from './client.model';
import { Manager } from './manager.model';

export class Account {
  constructor(
    public id: number,
    public limit: number,
    public balance: number,
    public salary: number,
    public situation: string,
    public manager: Manager,
    public client: Client,
    public createdAt: number
  ) {}
}

export class Manager {

  constructor(
    private _id: string,
    private _name: string,
    private _cpf: string,
    private _email: string,
    private _phone: string
  ) {}

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
  public get email(): string {
    return this._email;
  }
  public set email(value: string) {
    this._email = value;
  }
  public get phone(): string {
    return this._phone;
  }
  public set phone(value: string) {
    this._phone = value;
  }

}

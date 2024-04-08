export class Address {
  constructor(
  private _type: string,
  private _street: string,
  private _number: number,
  private _complement: string,
  private _cep: number,
  private _city: string,
  private _state: string,
) {}

  public get type(): string {
    return this._type;
  }
  public set type(value: string) {
    this._type = value;
  }

  public get street(): string {
    return this._street;
  }
  public set street(value: string) {
    this._street = value;
  }

  public get number(): number {
    return this._number;
  }
  public set number(value: number) {
    this._number = value;
  }

  public get complement(): string {
    return this._complement;
  }
  public set complement(value: string) {
    this._complement = value;
  }

  public get cep(): number {
    return this._cep;
  }
  public set cep(value: number) {
    this._cep = value;
  }

  public get city(): string {
    return this._city;
  }
  public set city(value: string) {
    this._city = value;
  }

  public get state(): string {
    return this._state;
  }
  public set state(value: string) {
    this._state = value;
  }
}

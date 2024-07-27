export class Address {
  private _type: string;
  private _street: string;
  private _number: number;
  private _complement: string;
  private _cep: string;
  private _city: string;
  private _state: string;

  constructor(
    type: string,
    street: string,
    number: number,
    complement: string,
    cep: string,
    city: string,
    state: string
  ) {
    this._type = type;
    this._street = street;
    this._number = number;
    this._complement = complement;
    this._cep = cep;
    this._city = city;
    this._state = state;
  }

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

  public get cep(): string {
    return this._cep;
  }

  public set cep(value: string) {
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

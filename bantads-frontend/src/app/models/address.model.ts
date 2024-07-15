
export class Address {
  constructor(
  public _addressType?: string,
  public _street?: string,
  public _number?: string,
  public _complement?: string,
  public _cep?: string,
  public _city?: string ,
  public _state?: string,
) {}

  public get addressType(): string | undefined {
    return this._addressType;  
  }
  public set addressType(addressType: string) {
    this._addressType = addressType;
  }

  public get street(): string | undefined {
    return this._street;  
  }
  public set street(street: string){
    this._street = street;
  }

  public get number(): string | undefined{
    return this._number;  
  }

  public set number(number: string){
    this._number = number;
  }

  public get complement(): string | undefined {
    return this._complement;  
  }
  public set complement(complement: string) {
    this._complement = complement;
  }

  public get cep(): string | undefined{
    return this._cep;  
  }
  public set cep(cep: string) {
    this._cep = cep;
  }

  public get city(): string | undefined{
    return this._city;  
  }

  public set city(city: string){
    this._city = city;
  }

  public get state(): string | undefined{
    return this._state;  
  }
  public set state(state: string) {
    this._state = state;
  }
  
}

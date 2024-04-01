export class Adress {
  constructor(
  private _tipo: string,
  private _logradouro: string,
  private _numero: number,
  private _complemento: string,
  private _cep: number,
  private _cidade: string,
  private _estado: string
) {}

  public get tipo(): string {
    return this._tipo;
  }
  public set tipo(value: string) {
    this._tipo = value;
  }

  public get logradouro(): string {
    return this._logradouro;
  }
  public set logradouro(value: string) {
    this._logradouro = value;
  }

  public get numero(): number {
    return this._numero;
  }
  public set numero(value: number) {
    this._numero = value;
  }

  public get complemento(): string {
    return this._complemento;
  }
  public set complemento(value: string) {
    this._complemento = value;
  }

  public get cep(): number {
    return this._cep;
  }
  public set cep(value: number) {
    this._cep = value;
  }

  public get cidade(): string {
    return this._cidade;
  }
  public set cidade(value: string) {
    this._cidade = value;
  }

  public get estado(): string {
    return this._estado;
  }
  public set estado(value: string) {
    this._estado = value;
  }

}

export class User {
  constructor(
              public id : number,
              public name : string,
              public email : string,
              public cpf: string,
              public profile? : string) {
  }

  public setProfile(profile : string){
    this.profile = profile;
  }
}

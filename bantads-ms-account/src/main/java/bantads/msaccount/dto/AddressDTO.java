package bantads.msaccount.dto;

public class AddressDTO {
  private String type;
  private String street;
  private int number;
  private String complement;
  private String cep;
  private String city;
  private String state;

  public String getType() {
    return type;
  }
  public void setType(String type) {
    this.type = type;
  }
  public String getStreet() {
    return street;
  }
  public void setStreet(String street) {
    this.street = street;
  }
  public int getNumber() {
    return number;
  }
  public void setNumber(int number) {
    this.number = number;
  }
  public String getComplement() {
    return complement;
  }
  public void setComplement(String complement) {
    this.complement = complement;
  }
  public String getCep() {
    return cep;
  }
  public void setCep(String cep) {
    this.cep = cep;
  }
  public String getCity() {
    return city;
  }
  public void setCity(String city) {
    this.city = city;
  }
  public String getState() {
    return state;
  }
  public void setState(String state) {
    this.state = state;
  }
}
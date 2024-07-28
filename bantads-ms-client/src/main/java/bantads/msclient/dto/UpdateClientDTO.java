package bantads.msclient.dto;

public class UpdateClientDTO {
    private String name;
    private String email;
    private AddressDTO address;
    private String phone;
    private Double salary;
    private String situation;
    private String role;
    
    public String getName() {
      return name;
    }
    public void setName(String name) {
      this.name = name;
    }
    public String getEmail() {
      return email;
    }
    public void setEmail(String email) {
      this.email = email;
    }
    public AddressDTO getAddress() {
      return address;
    }
    public void setAddress(AddressDTO address) {
      this.address = address;
    }
    public String getPhone() {
      return phone;
    }
    public void setPhone(String phone) {
      this.phone = phone;
    }
    public Double getSalary() {
      return salary;
    }
    public void setSalary(Double salary) {
      this.salary = salary;
    }
    public String getSituation() {
      return situation;
    }
    public void setSituation(String situation) {
      this.situation = situation;
    }
    public String getRole() {
      return role;
    }
    public void setRole(String role) {
      this.role = role;
    }

}

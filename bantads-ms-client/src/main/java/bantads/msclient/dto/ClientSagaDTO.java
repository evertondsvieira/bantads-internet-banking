package bantads.msclient.dto;

public class ClientSagaDTO {
    private String name;
    private String email;
    private String cpf;
    private AddressDTO address;
    private String phone;
    private Double salary;

    public ClientSagaDTO() {
    }

    public ClientSagaDTO(String name, String email, String cpf, AddressDTO address, String phone, Double salary) {
        this.name = name;
        this.email = email;
        this.cpf = cpf;
        this.address = address;
        this.phone = phone;
        this.salary = salary;
    }
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
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
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
}
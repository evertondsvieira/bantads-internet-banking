package bantads.msclient.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO {
  private String id;
  private String name;
  private String email;
  private String cpf;
  private AddressDTO address;
  private Double salary;
}

package bantads.msclient.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
  private String id;
  private String type;
  private String street;
  private Integer number;
  private String complement;
  private String cep;
  private String city;
  private String state;
}

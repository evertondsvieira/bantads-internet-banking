package bantads.saga_profile_update.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AddressDTO {
  private String type;
  private String street;
  private int number;
  private String complement;
  private String cep;
  private String city;
  private String state;
}
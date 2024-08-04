package bantads.saga_profile_update.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UpdateClientDTO {
  private String name;
  private String email;
  private AddressDTO address;
  private String phone;
  private Double salary;
  private String situation;
  private String role;

}


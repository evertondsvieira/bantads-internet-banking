package bantads.saga_manager.dto;

import java.io.Serializable;

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
public class ManagerPassDTO implements Serializable{
    private String name;
    private String email;
    private String cpf;
    private String phone;
    private String password;
}
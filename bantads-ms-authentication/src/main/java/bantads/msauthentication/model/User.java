package bantads.msauthentication.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;

public class User implements Serializable{

    @Id
    private String id;

    private String login;

    private String password;

    private String role;

    private String salt;

    public User() {
        super();
    };

    public User(String id, String login, String password, String role, String salt) {
        super();
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;
        this.salt = salt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }    
}

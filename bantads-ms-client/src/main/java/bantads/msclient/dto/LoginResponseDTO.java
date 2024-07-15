package bantads.msclient.dto;

public class LoginResponseDTO {
  private boolean auth;
  private String token;
  private Long id;
  private String role;

  public LoginResponseDTO(boolean auth, String token, Long id, String role) {
    this.auth = auth;
    this.token = token;
    this.id = id;
    this.role = role;
  }

  public boolean isAuth() {
    return auth;
  }
  public void setAuth(boolean auth) {
    this.auth = auth;
  }
  public String getToken() {
    return token;
  }
  public void setToken(String token) {
    this.token = token;
  }
  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public String getRole() {
    return role;
  }
  public void setRole(String role) {
    this.role = role;
  }
}

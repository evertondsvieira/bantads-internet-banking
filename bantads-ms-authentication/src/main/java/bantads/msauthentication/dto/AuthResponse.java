package bantads.msauthentication.dto;

public class AuthResponse {
    private boolean auth;
    private UserDTO user;
    
    public AuthResponse() {
    }

    public AuthResponse(boolean auth, UserDTO user) {
        this.auth = auth;
        this.user = user;
    }

    public boolean isAuth() {
        return auth;
    }

    public void setAuth(boolean auth) {
        this.auth = auth;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
}

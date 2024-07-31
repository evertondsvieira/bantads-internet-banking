package bantads.saga_manager.store;

import org.springframework.stereotype.Component;

import bantads.saga_manager.dto.UserDTO;

@Component
public class UserStore {
    private UserDTO userStoreDTO;

    public UserDTO getUserStoreDTO() {
        return userStoreDTO;
    }

    public void setUserStoreDTO(UserDTO userStoreDTO) {
        this.userStoreDTO = userStoreDTO;
    }
}
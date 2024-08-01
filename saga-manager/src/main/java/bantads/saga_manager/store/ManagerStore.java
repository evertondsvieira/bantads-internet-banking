package bantads.saga_manager.store;

import org.springframework.stereotype.Component;

import bantads.saga_manager.dto.ManagerAccountDTO;

@Component
public class ManagerStore {
    private ManagerAccountDTO managerStoreDTO;

    public ManagerAccountDTO getManagerStoreDTO() {
        return managerStoreDTO;
    }

    public void setManagerStoreDTO(ManagerAccountDTO managerStoreDTO) {
        this.managerStoreDTO = managerStoreDTO;
    }
}
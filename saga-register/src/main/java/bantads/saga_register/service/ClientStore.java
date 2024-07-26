package bantads.saga_register.service;

import org.springframework.stereotype.Component;
import bantads.saga_register.dto.ClientDTO;

@Component
public class ClientStore {
    private ClientDTO clientStoreDTO;

    public ClientDTO getClientStoreDTO() {
        return clientStoreDTO;
    }

    public void setClientStoreDTO(ClientDTO clientStoreDTO) {
        this.clientStoreDTO = clientStoreDTO;
    }
}

package bantads.saga_manager.store;

import org.springframework.stereotype.Component;

@Component
public class CpfStore {
    private String cpfManagerStore;

    public String getCpfManagerStore() {
        return cpfManagerStore;
    }

    public void setCpfManagerStore(String cpfManagerStore) {
        this.cpfManagerStore = cpfManagerStore;
    }
}
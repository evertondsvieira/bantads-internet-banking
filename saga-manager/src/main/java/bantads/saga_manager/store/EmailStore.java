package bantads.saga_manager.store;

import org.springframework.stereotype.Component;

@Component
public class EmailStore {
    private String emailManagerStore;

    public String getEmailManagerStore() {
        return emailManagerStore;
    }

    public void setEmailManagerStore(String emailManagerStore) {
        this.emailManagerStore = emailManagerStore;
    }
}
package bantads.saga_manager.dto;

public class ResponseDTO {
    private boolean managerCreated;
    private ManagerDTO manager;
    
    public ResponseDTO() {
    }

    public ResponseDTO(boolean managerCreated, ManagerDTO manager) {
        this.managerCreated = managerCreated;
        this.manager = manager;
    }

    public boolean isCreated() {
        return managerCreated;
    }

    public void setManagerCreated(boolean managerCreated) {
        this.managerCreated = managerCreated;
    }

    public ManagerDTO getManager() {
        return manager;
    }

    public void setManager(ManagerDTO manager) {
        this.manager = manager;
    }
}
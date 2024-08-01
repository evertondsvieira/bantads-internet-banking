package bantads.msmanager.service;

import bantads.msmanager.dto.UpdateManagerDTO;
import bantads.msmanager.entity.Manager;
import bantads.msmanager.exception.ManagerAlreadyExistsException;
import bantads.msmanager.exception.ManagerNotFoundException;
import bantads.msmanager.repository.ManagerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManagerService {

    @Autowired
    private ManagerRepository managerRepository;

    public Manager createManager(Manager newManager) {
        if (managerRepository.findByCpf(newManager.getCpf()).isPresent()) {
            throw new ManagerAlreadyExistsException("Manager with CPF already exists");
        }
        return managerRepository.save(newManager);
    }

    @Transactional
    public Manager updateManager(Long id, UpdateManagerDTO updateManagerDTO) {
        Manager manager = managerRepository.findById(id).orElseThrow(() -> new ManagerNotFoundException("Manager not found"));
        return managerRepository.save(manager);
    }

    public Manager deleteManager(Long id) {
        Manager manager = managerRepository.findById(id).orElseThrow(() -> new ManagerNotFoundException("Manager not found"));
        managerRepository.delete(manager);
        return manager;
    }

    public Optional<Manager> getManagerById(Long id) {
        return managerRepository.findById(id);
    }

    public List<Manager> getAllManagers() {
        return managerRepository.findAll();
    }
}

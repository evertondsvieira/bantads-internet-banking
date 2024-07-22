package bantads.msmanager.controllers;

import bantads.msmanager.dto.*;
import bantads.msmanager.entity.Manager;
import bantads.msmanager.service.ManagerService;
import bantads.msmanager.exception.*;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ManagerController {

    private static final Logger logger = LoggerFactory.getLogger(ManagerController.class);

    @Autowired
    private ManagerService managerService;

    @GetMapping("/manager")
    public ResponseEntity<List<ManagerDTO>> getAllManagers(@RequestParam(required = false) String situation) {
        try {
            List<ManagerDTO> managerDTOs = (situation != null ? managerService.getAllManagers()
                    : managerService.getAllManagers())
                    .stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(managerDTOs);
        } catch (Exception e) {
            logger.error("Error fetching managers: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/manager/{id}")
    public ResponseEntity<ManagerDTO> getManagerById(@PathVariable Long id) {
        try {
          ManagerDTO managerDTO = convertToDTO(managerService.getManagerById(id)
                    .orElseThrow(() -> new ManagerNotFoundException("Manager not found")));

            return ResponseEntity.ok(managerDTO);
        } catch (ManagerNotFoundException e) {
            logger.error("Manager not found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            logger.error("Error fetching manager by ID: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/manager")
    public ResponseEntity<ManagerDTO> registerManager(@Valid @RequestBody ManagerDTO managerDTO) {
        try {
            Manager createdManager = managerService.createManager(convertToEntity(managerDTO));
            return new ResponseEntity<>(convertToDTO(createdManager), HttpStatus.CREATED);
        } catch (ManagerAlreadyExistsException e) {
            logger.error("Manager already exists: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (Exception e) {
            logger.error("Error registering manager: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/manager/{id}")
    public ResponseEntity<ManagerDTO> updateManager(@PathVariable Long id, @RequestBody UpdateManagerDTO updateManagerDTO) {
        try {
            Manager updatedManager = managerService.updateManager(id, updateManagerDTO);
            return ResponseEntity.ok(convertToDTO(updatedManager));
        } catch (ManagerNotFoundException e) {
            logger.error("Manager not found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            logger.error("Error updating manager: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private ManagerDTO convertToDTO(Manager manager) {
        ManagerDTO managerDTO = new ManagerDTO();
        managerDTO.setName(manager.getName());
        managerDTO.setCpf(manager.getCpf());
        managerDTO.setEmail(manager.getEmail());
        managerDTO.setPhone(manager.getPhone());

        return managerDTO;
    }

    private Manager convertToEntity(ManagerDTO managerDTO) {
        Manager manager = new Manager();
        manager.setName(managerDTO.getName());
        manager.setEmail(managerDTO.getEmail());
        manager.setCpf(managerDTO.getCpf());
        manager.setPhone(managerDTO.getPhone());

        return manager;
    }
}

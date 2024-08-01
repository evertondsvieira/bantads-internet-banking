package bantads.saga_manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import bantads.saga_manager.dto.ManagerAccountDTO;
import bantads.saga_manager.dto.ManagerDTO;
import bantads.saga_manager.dto.ManagerPassDTO;
import bantads.saga_manager.dto.ResponseDTO;
import bantads.saga_manager.dto.UserDTO;
import bantads.saga_manager.service.ManagerService;
import bantads.saga_manager.store.ManagerStore;
import bantads.saga_manager.store.UserStore;

@CrossOrigin
@RestController
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    @Autowired
    private UserStore userStoreDTO;

    @Autowired
    private ManagerStore managerStoreDTO;

    @PostMapping("/manager")
    public ResponseEntity<ResponseDTO> registerManager(@RequestBody ManagerPassDTO managerPassDTO) {        
        try {
            ManagerDTO managerDTO = new ManagerDTO(managerPassDTO.getName(), managerPassDTO.getEmail(), managerPassDTO.getCpf(), managerPassDTO.getPhone());
            UserDTO userDTO = new UserDTO();
            userDTO.setLogin(managerPassDTO.getEmail());
            userDTO.setPassword(managerPassDTO.getPassword());
            userDTO.setRole("MANAGER");
            userStoreDTO.setUserStoreDTO(userDTO);
            ManagerAccountDTO managerAccountDTO = new ManagerAccountDTO(managerPassDTO.getName(), managerPassDTO.getCpf());
            managerStoreDTO.setManagerStoreDTO(managerAccountDTO);
            managerService.createManager(managerDTO);
            ResponseDTO response = new ResponseDTO(true, managerDTO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseDTO response = new ResponseDTO();
            response.setManagerCreated(false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
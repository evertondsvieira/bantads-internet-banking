package bantads.saga_manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import bantads.saga_manager.dto.ManagerAccountDTO;
import bantads.saga_manager.dto.ManagerDTO;
import bantads.saga_manager.dto.ManagerPassDTO;
import bantads.saga_manager.dto.ResponseDTO;
import bantads.saga_manager.dto.UserDTO;
import bantads.saga_manager.service.DeleteManagerService;
import bantads.saga_manager.service.ManagerService;
import bantads.saga_manager.store.EmailStore;
import bantads.saga_manager.store.ManagerStore;
import bantads.saga_manager.store.UserStore;

@CrossOrigin
@RestController
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    @Autowired
    private DeleteManagerService deleteManagerService;

    @Autowired
    private UserStore userStoreDTO;

    @Autowired
    private ManagerStore managerStoreDTO;

    @Autowired
    private EmailStore emailManagerStore;

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
            ResponseDTO response = new ResponseDTO(true, "Solicitação de cadastro de gerente recebida com sucesso");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseDTO response = new ResponseDTO(false, "Erro no processamento da solicitação de cadastro de gerente");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/manager/{email}")
    public ResponseEntity<ResponseDTO> removeManager(@PathVariable("email") String email) {
        try {
            emailManagerStore.setEmailManagerStore(email);
            deleteManagerService.deleteManager(email);
            ResponseDTO response = new ResponseDTO(true, "Solicitação de remoção de gerente recebida com sucesso");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseDTO response = new ResponseDTO(false, "Erro no processamento da solicitação de remoção de gerente");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
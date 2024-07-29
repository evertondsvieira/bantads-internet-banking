package bantads.account_command.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bantads.account_command.dto.ManagerDTO;
import bantads.account_command.exceptions.RecordCannotBeDeleted;
import bantads.account_command.exceptions.RecordDuplicationException;
import bantads.account_command.exceptions.RecordNotFoundException;
import bantads.account_command.service.ManagerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/manager")
public class ManagerController {

  @Autowired
  private ManagerService managerService;

  private static final Logger logger = LoggerFactory.getLogger(ManagerController.class);

  @GetMapping("/{cpf}")
  public ResponseEntity<ManagerDTO> getManager(@PathVariable String cpf){
    try {
      ManagerDTO managerDTO = managerService.getManagerByCpf(cpf); 
      return ResponseEntity.ok(managerDTO);
    } 
    catch (RecordNotFoundException r){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    } 
    catch (RecordDuplicationException r) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    } 
    catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }

  @GetMapping
  public ResponseEntity<List<ManagerDTO>> getManagers(){
    try {
      List<ManagerDTO> managerDTOs = managerService.getManagers(); 
      return ResponseEntity.ok(managerDTOs);
    } 
    catch (RecordNotFoundException r){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    } 
    catch (RecordDuplicationException r) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    } 
    catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }



  @PostMapping
  public ResponseEntity<ManagerDTO> createManager(@RequestBody ManagerDTO managerDTO) {
    try {
      ManagerDTO managerDTOCreated = managerService.createManager(managerDTO);
      return new ResponseEntity<>(managerDTOCreated, HttpStatus.CREATED);
    } 
    catch (RecordDuplicationException r) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    } 
    catch (Exception e) {
      e.printStackTrace();
      logger.info(String.format("Error when creating manager with cpf %s", managerDTO.getCpf()));
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }  
  }

  @PutMapping("/{cpf}")
  public ResponseEntity<ManagerDTO> updateManager(@PathVariable String cpf, @RequestBody ManagerDTO managerDTO) {
    try {
      ManagerDTO managerDTOCreated = managerService.updateManager(cpf, managerDTO);
      return new ResponseEntity<>(managerDTOCreated, HttpStatus.OK);
    } 
    catch (RecordNotFoundException r){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    } 
    catch (Exception e) {
      logger.info(String.format("Error when updating manager with cpf %s", managerDTO.getCpf()));
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    } 
  }
  
  @DeleteMapping("/{cpf}")
  public ResponseEntity<String> deleteManager(@PathVariable String cpf) {
    try {
      managerService.deleteManager(cpf);
      return ResponseEntity.noContent().build();
    } 
    catch (RecordCannotBeDeleted r){
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    } 
    catch (RecordNotFoundException r){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    } 
    catch (Exception e) {
      e.printStackTrace();
      logger.info(String.format("Error when deleting manager with cpf %s", cpf));
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    } 
  }
}

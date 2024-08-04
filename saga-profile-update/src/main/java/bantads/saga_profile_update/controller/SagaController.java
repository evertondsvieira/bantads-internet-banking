package bantads.saga_profile_update.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bantads.saga_profile_update.dto.ClientDTO;
import bantads.saga_profile_update.service.SagaService;

@RestController
@RequestMapping("/client")
public class SagaController {

  @Autowired
  private SagaService sagaService;

  @PutMapping("/{id}")
  public ResponseEntity<String> updateClient(@PathVariable("id") Long id,@RequestBody ClientDTO clientDTO) {
    try {
      clientDTO.setId(id);
      String response = sagaService.updateClient(clientDTO);
      return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    } catch (Exception e) {
      return new ResponseEntity<>("Error when request the client update", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}

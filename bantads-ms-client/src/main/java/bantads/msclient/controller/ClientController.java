package bantads.msclient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import bantads.msclient.DTO.ClientDTO;
import bantads.msclient.service.ClientService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {
  
  @Autowired
  private ClientService clientService;

  @PostMapping
  @ResponseStatus(HttpStatus.OK)
  public ClientDTO createClient(@RequestBody ClientDTO clientDTO){
    return clientService.createClient(clientDTO);
  }
  
}

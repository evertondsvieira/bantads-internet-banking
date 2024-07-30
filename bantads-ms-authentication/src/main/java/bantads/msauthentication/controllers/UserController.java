package bantads.msauthentication.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import bantads.msauthentication.model.User;
import bantads.msauthentication.dto.AuthResponse;
import bantads.msauthentication.dto.UserDTO;
import bantads.msauthentication.repository.UserRepository;
import bantads.msauthentication.services.GeneratePassword;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserRepository repo;

    @Autowired
    private ModelMapper mapper;

    @GetMapping
    ResponseEntity<List<UserDTO>> listarTodos() {
        List<User> lista = repo.findAll();

        // Converte lista de Entity para lista DTO
        List<UserDTO> userDTOList = lista.stream()
                                        .map(e -> mapper.map(e, UserDTO.class))
                                        .collect(Collectors.toList());

        if (userDTOList.isEmpty()) {
            // Retorna código de status 204 - NO CONTENT, no header do retorno
            return ResponseEntity.noContent()
                                    .header("Message", "Nenhum usuário encontrado.")
                                    .build();
        }

        // Retorna a lista com código de status 200 (OK)
        return ResponseEntity.ok(userDTOList);
    }

    @GetMapping("/{login}")
    ResponseEntity<?> buscarPorLogin(@PathVariable("login") String login) {
        // Busca o usuário com o login informado
        User user = repo.findByLogin(login);

        if (user != null) {
            // Converte a entidade para DTO
            UserDTO userDTO = mapper.map(user, UserDTO.class);            
            // Retorna o DTO com código de status 200 (OK)
            return ResponseEntity.ok(userDTO);
        } else {
            // Retorna uma mensagem de erro com código de status 404 (Not Found)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).
                body("Usuário com o login informado nao encontrado");
        }
    }

    @PostMapping
    ResponseEntity<?> inserir(@RequestBody UserDTO userDTO) {
        try {
            // Verifica se já existe um usuário com o login fornecido
            User existingUser = repo.findByLogin(userDTO.getLogin());
            if (existingUser != null) {
                // Retorna um código de status 409 (Conflict) se o usuário já existir
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Usuario com este Login ja cadastrado no sistema.");
            }
            GeneratePassword passwordGenerator = new GeneratePassword();
            String salt = passwordGenerator.generateSalt();
            String hashedPassword = passwordGenerator.hashPassword(userDTO.getPassword(), salt);
            userDTO.setSalt(salt);
            userDTO.setPassword(hashedPassword);

            // salva a Entidade convertida do DTO
            repo.save(mapper.map(userDTO, User.class));

            // busca o usuario com o login inserido
            User usu = repo.findByLogin(userDTO.getLogin());

            // retorna o DTO equivalente à entidade
            return ResponseEntity.status(HttpStatus.CREATED).body(mapper.map(usu, UserDTO.class));
        } catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro no processamento do cadastro do usuario.");            
        }
    }

    @PostMapping("/login")
    ResponseEntity<AuthResponse> autenticar(@RequestBody UserDTO userDTO) {
        try {
            User usu = repo.findByLogin(userDTO.getLogin());
            GeneratePassword passwordGenerator = new GeneratePassword();
            String hashedPassword = passwordGenerator.hashPassword(userDTO.getPassword(), usu.getSalt());

            if (hashedPassword.equals(usu.getPassword()) && usu != null && usu.getPassword() != null) {
                // Usuário encontrado, retorna com código 200 (OK)
                UserDTO usuDTO = mapper.map(usu, UserDTO.class);
                AuthResponse response = new AuthResponse(true, usuDTO);
                return ResponseEntity.ok(response);
            } else {
                // Autenticação falhou, retorna com código 401 (Unauthorized)
                AuthResponse response = new AuthResponse(false, null);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
        } catch(Exception e) {
            e.printStackTrace();
            AuthResponse response = new AuthResponse(false, null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);            
        }
    }

    @DeleteMapping("/{login}")
    ResponseEntity<?> excluirPorLogin(@PathVariable("login") String login) {
        User user = repo.findByLogin(login);

        if (user != null) {
            // Exclui o usuário
            repo.delete(user);
            // Retorna uma mensagem com o Status Code 204 - NO CONTENT
            return ResponseEntity.noContent().build();
        } else {
            // Retorna uma mensagem de erro com código 404 (Not Found) se o usuário não for encontrado
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
        }
    }
}
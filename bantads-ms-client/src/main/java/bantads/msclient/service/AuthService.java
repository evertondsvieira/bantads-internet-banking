package bantads.msclient.service;

import bantads.msclient.dto.LoginRequestDTO;
import bantads.msclient.dto.LoginResponseDTO;
import bantads.msclient.entity.Client;
import bantads.msclient.exception.InvalidCredentialsException;
import bantads.msclient.repository.ClientRepository;
import bantads.msclient.utils.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

  @Autowired
  private ClientRepository clientRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private JwtTokenProvider jwtTokenProvider;

  public LoginResponseDTO authenticate(LoginRequestDTO loginRequest) {
    Optional<Client> clientOptional = clientRepository.findByEmail(loginRequest.getEmail());
    if (clientOptional.isPresent()) {
      Client client = clientOptional.get();
      if (passwordEncoder.matches(loginRequest.getPassword(), client.getPassword())) {
        String token = jwtTokenProvider.createToken(client.getEmail(), client.getRole());
        return new LoginResponseDTO(true, token, client.getId(), client.getRole());
      }
    }
    throw new InvalidCredentialsException("Invalid email or password");
  }
}

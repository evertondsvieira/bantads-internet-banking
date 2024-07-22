package bantads.account_command.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import bantads.account_command.entity.Client;

public interface ClientRepository extends JpaRepository<Client, Long>{
  Optional<Client> findByCpf(String cpf);
}

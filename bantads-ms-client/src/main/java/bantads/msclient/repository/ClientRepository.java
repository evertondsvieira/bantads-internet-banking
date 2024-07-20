package bantads.msclient.repository;

import bantads.msclient.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findById(Long id);
    Optional<Client> findByCpf(String cpf);
    Optional<Client> findByEmail(String email);
    List<Client> findBySituation(String situation);
}

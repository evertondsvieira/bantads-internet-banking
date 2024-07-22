package bantads.msmanager.repository;

import bantads.msmanager.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
    Optional<Manager> findById(Long id);
    Optional<Manager> findByCpf(String cpf);
    Optional<Manager> findByEmail(String email);
}

package bantads.account_command.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import bantads.account_command.entity.Manager;

public interface ManagerRepository extends JpaRepository<Manager, Long>{
  Optional<Manager> findByCpf(String cpf);
}

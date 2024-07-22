package bantads.account_command.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import bantads.account_command.entity.Manager;

public interface ManagerRepository extends JpaRepository<Manager, Long>{

}

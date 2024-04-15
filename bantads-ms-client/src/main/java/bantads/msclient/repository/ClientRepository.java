package bantads.msclient.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import bantads.msclient.model.Client;

public interface ClientRepository extends JpaRepository<Client, String> {

}

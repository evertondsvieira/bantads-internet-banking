package bantads.msauthentication.repository;

import bantads.msauthentication.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String>{

    public User findByLogin(String login);

    public User findByLoginAndPassword(String login, String password);

}
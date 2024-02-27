package starwarsapi20.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import starwarsapi20.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

}

package starwarsapi20.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import starwarsapi20.entities.User;
import starwarsapi20.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public List<User> findAll() {
        return repository.findAll();
    }

    public User findById(Long id) {
        Optional<User> obj = repository.findById(id);
        return obj.get();
    }


   //adiciona um novo usuario
    public User insert(User obj) {
        return repository.save(obj);
    }

    public void delete(long id) {
        repository.deleteById(id);

    }

}

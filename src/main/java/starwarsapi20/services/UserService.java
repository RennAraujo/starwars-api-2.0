package starwarsapi20.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import starwarsapi20.entities.User;
import starwarsapi20.repositories.UserRepository;
import starwarsapi20.services.exceptions.DataBaseException;
import starwarsapi20.services.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public List<User> findAll() {
        // Retorna todos os usuários
        return repository.findAll();
    }

    /**
     * Busca um usuário pelo ID.
     *
     * @param id Identificador do usuário.
     * @return O usuário com o ID correspondente.
     * @throws ResourceNotFoundException Se o usuário não for encontrado.
     */
    public User findById(Long id) {
        Optional<User> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    /**
     * Insere um novo usuário.
     *
     * @param obj O usuário a ser inserido.
     * @return O usuário inserido.
     */
    public User insert(User obj) {
        return repository.save(obj);
    }

    /**
     * Deleta um usuário pelo ID.
     *
     * @param id Identificador do usuário a ser deletado.
     * @throws ResourceNotFoundException Se o usuário não for encontrado.
     * @throws DataBaseException         Se ocorrer um erro de integridade de dados.
     */
    public void delete(long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException(e.getMessage());
        }
    }

    /**
     * Atualiza um usuário existente.
     *
     * @param id  Identificador do usuário a ser atualizado.
     * @param obj Novos dados do usuário.
     * @return O usuário atualizado.
     * @throws ResourceNotFoundException Se o usuário não for encontrado.
     */
    public User update(Long id, User obj) {
        try {
            User entity = repository.getReferenceById(id);
            updateData(entity, obj);
            return repository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    /**
     * Atualiza os dados do usuário com base nos novos dados fornecidos.
     *
     * @param entity Usuário existente.
     * @param obj    Novos dados do usuário.
     */
    private void updateData(User entity, User obj) {
        entity.setName(obj.getName());
        entity.setGenero(obj.getGenero());
        entity.setIdade(obj.getIdade());
        entity.setTraidor(obj.getTraidor());
    }
}

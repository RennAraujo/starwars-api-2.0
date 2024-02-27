package starwarsapi20.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import starwarsapi20.entities.User;
import starwarsapi20.repositories.UserRepository;

import java.util.Arrays;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;


    @Override
    public void run(String... args) throws Exception {
        User u1 = new User(1L, "Mario", "23", "Masculino", "Verdadeiro");
        User u2 = new User(2L, "Joana", "25", "Feminino", "Falso");
        User u3 = new User(3L, "Jose", "32", "Masculino", "Verdadeiro");
        User u4 = new User(4L, "Joana", "28", "Feminino", "Falso");
        userRepository.saveAll(Arrays.asList(u1,u2,u3,u4));
    }
}

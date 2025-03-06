package starwarsapi20.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import starwarsapi20.entities.Localizacao;
import starwarsapi20.entities.User;
import starwarsapi20.repositories.LocalizacaoRepository;
import starwarsapi20.repositories.UserRepository;

import java.util.Arrays;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LocalizacaoRepository localizacaoRepository;

    @Override
    public void run(String... args) throws Exception {
        // Criar usuários
        User u1 = new User(1L, "Mario", "23", "Masculino", "Verdadeiro");
        User u2 = new User(2L, "Joana", "25", "Feminino", "Falso");
        User u3 = new User(3L, "Jose", "32", "Masculino", "Verdadeiro");
        User u4 = new User(4L, "Joana", "28", "Feminino", "Falso");
        userRepository.saveAll(Arrays.asList(u1, u2, u3, u4));

        // Criar localizações
        Localizacao l1 = new Localizacao(null, "-23.5505", "-46.6333", "Terra", u1);
        Localizacao l2 = new Localizacao(null, "-14.2350", "-51.9253", "Marte", u2);
        Localizacao l3 = new Localizacao(null, "-16.6800", "-49.2550", "Venus", u3);
        Localizacao l4 = new Localizacao(null, "-15.7801", "-47.9292", "Terra", u4);

        // Salvar localizações
        localizacaoRepository.saveAll(Arrays.asList(l1, l2, l3, l4));

        // Atualizar usuários com suas localizações
        u1.setLocalizacao(l1);
        u2.setLocalizacao(l2);
        u3.setLocalizacao(l3);
        u4.setLocalizacao(l4);
        userRepository.saveAll(Arrays.asList(u1, u2, u3, u4));
    }
}

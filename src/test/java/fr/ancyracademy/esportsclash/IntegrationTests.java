package fr.ancyracademy.esportsclash;

import fr.ancyracademy.esportsclash.auth.application.ports.UserRepository;
import fr.ancyracademy.esportsclash.auth.application.services.jwtservice.JwtService;
import fr.ancyracademy.esportsclash.auth.domain.model.User;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@Import(PostgreSQLTestConfiguration.class)
@Transactional
public class IntegrationTests {

    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected JwtService jwtService;
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected EntityManager entityManager;

    protected String createJwt() {
        var user = userRepository.findByEmailAddress("contact@gmail.com").orElse(null);
        if (user == null) {
            user = new User("123", "contact@gmail.com", "");
            userRepository.save(user);
        }
        return "Bearer " + jwtService.tokenize(user);
    }

    protected void flushAndClear() {
        entityManager.flush();
        entityManager.clear();
    }
}

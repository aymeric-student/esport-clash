package fr.ancyracademy.esportsclash.auth;

import fr.ancyracademy.esportsclash.IntegrationTests;
import fr.ancyracademy.esportsclash.auth.application.services.passwordHasher.PasswordHasher;
import fr.ancyracademy.esportsclash.auth.domain.model.User;
import fr.ancyracademy.esportsclash.auth.domain.viewmodel.LoggedUserViewModel;
import fr.ancyracademy.esportsclash.auth.infrastructure.spring.LoginDto;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class LoginE2ETests extends IntegrationTests {

    @Autowired
    private PasswordHasher passwordHasher;

    @BeforeEach
    public void setUp() {
        userRepository.clear();
        var user = new User("123",
                "contact@gmail.com",
                passwordHasher.hash("azerty"));
        userRepository.save(user);
    }

    @Test
    public void shouldLogUserIn() throws Exception {


        var dto = new LoginDto("contact@gmail.com", "azerty");

        var result = mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        LoggedUserViewModel user = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                LoggedUserViewModel.class
        );

        Assert.assertEquals("123", user.getId());
        Assert.assertEquals("contact@gmail.com", user.getEmailAddress());
    }

    @Test
    public void whenEmailAddressIsInvalid_shouldThrow() throws Exception {


        var dto = new LoginDto("not-available@gmail.com", "azerty");

        var result = mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void whenEmailPasswordIsInvalid_shouldThrow() throws Exception {


        var dto = new LoginDto("contact@gmail.com", "123");

        var result = mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}

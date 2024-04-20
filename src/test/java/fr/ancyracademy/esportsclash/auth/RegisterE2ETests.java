package fr.ancyracademy.esportsclash.auth;

import fr.ancyracademy.esportsclash.IntegrationTests;
import fr.ancyracademy.esportsclash.auth.domain.model.User;
import fr.ancyracademy.esportsclash.auth.infrastructure.spring.RegisterDTO;
import fr.ancyracademy.esportsclash.player.domain.viewmodel.IdResponse;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


public class RegisterE2ETests extends IntegrationTests {
    @BeforeEach
    public void setUp() {
        userRepository.clear();
    }

    @Test
    public void shouldRegisterUser() throws Exception {
        var dto = new RegisterDTO("contact@gmail.com", "azerty");

        var result = mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        var idResponse = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                IdResponse.class
        );

        var user = userRepository.findById(
                idResponse.getId()
        ).get();

        Assert.assertNotNull(user);
        Assert.assertEquals(user.getEmailAddress(), dto.getEmailAddress());
    }

    @Test
    public void whenEmailAddressIsAnavailable_shouldFail() throws Exception {
        var existingUser = new User("123",
                "contact@gmail.com",
                "azerty");

        userRepository.save(existingUser);


        var dto = new RegisterDTO(existingUser.getEmailAddress(), "azerty");

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}

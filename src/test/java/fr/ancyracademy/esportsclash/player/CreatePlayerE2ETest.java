package fr.ancyracademy.esportsclash.player;

import fr.ancyracademy.esportsclash.IntegrationTests;
import fr.ancyracademy.esportsclash.player.application.ports.PlayerRepository;
import fr.ancyracademy.esportsclash.player.domain.viewmodel.IdResponse;
import fr.ancyracademy.esportsclash.player.infrastructure.spring.CreatePlayerDTO;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class CreatePlayerE2ETest extends IntegrationTests {

    @Autowired
    private PlayerRepository playerRepository;

    @Test
    public void shouldCreateUser() throws Exception {
        var dto = new CreatePlayerDTO("player");

        var result = mockMvc.perform(MockMvcRequestBuilders.post("/players")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto))
                        .header("Authorization", createJwt())
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        var idResponse = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                IdResponse.class
        );

        var player = playerRepository.findById(idResponse.getId()).get();

        Assert.assertNotNull(player);
        Assert.assertEquals(dto.getName(), player.getName());
    }
}

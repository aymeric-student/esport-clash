package fr.ancyracademy.esportsclash.player;

import fr.ancyracademy.esportsclash.IntegrationTests;
import fr.ancyracademy.esportsclash.player.application.ports.PlayerRepository;
import fr.ancyracademy.esportsclash.player.domain.model.Player;
import fr.ancyracademy.esportsclash.player.infrastructure.spring.RenamePlayerDTO;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class RenamePlayerE2ETest extends IntegrationTests {

    @Autowired
    private PlayerRepository playerRepository;

    @Test
    public void shouldRenameUser() throws Exception {
        var expectedPlayer = new Player("123", "player");
        playerRepository.save(expectedPlayer);

        var dto = new RenamePlayerDTO("new name");


        mockMvc.perform(MockMvcRequestBuilders.patch(
                                "/players/" + expectedPlayer.getId() + "/name")
                        .header("Authorization", createJwt())
                        .contentType(MediaType.APPLICATION_JSON)

                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        var player = playerRepository.findById(expectedPlayer.getId()).get();

        Assert.assertEquals(dto.getName(), player.getName());
    }

    @Test
    public void whenPlayerDoesNotExist_shoud_fail() throws Exception {
        var dto = new RenamePlayerDTO("new name");

        mockMvc.perform(MockMvcRequestBuilders.patch(
                                "/players/garbage/name")
                        .header("Authorization", createJwt())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}

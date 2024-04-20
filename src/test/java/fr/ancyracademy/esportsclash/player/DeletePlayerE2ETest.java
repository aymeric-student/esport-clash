package fr.ancyracademy.esportsclash.player;

import fr.ancyracademy.esportsclash.IntegrationTests;
import fr.ancyracademy.esportsclash.player.application.ports.PlayerRepository;
import fr.ancyracademy.esportsclash.player.domain.model.Player;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class DeletePlayerE2ETest extends IntegrationTests {

    @Autowired
    private PlayerRepository playerRepository;

    @Test
    public void shouldDeleteUser() throws Exception {
        var expectedPlayer = new Player("123", "player");
        playerRepository.save(expectedPlayer);

        mockMvc.perform(MockMvcRequestBuilders.delete(
                                "/players/" + expectedPlayer.getId())
                        .header("Authorization", createJwt())
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        var playerQuery = playerRepository.findById(expectedPlayer.getId());
        Assert.assertTrue(playerQuery.isEmpty());
    }

    @Test
    public void whenPlayerDoesNotExist_shoud_fail() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete(
                                "/players/garbage")
                        .header("Authorization", createJwt())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();
    }
}

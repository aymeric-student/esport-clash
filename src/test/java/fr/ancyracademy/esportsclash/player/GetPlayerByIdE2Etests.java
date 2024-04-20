package fr.ancyracademy.esportsclash.player;

import fr.ancyracademy.esportsclash.IntegrationTests;
import fr.ancyracademy.esportsclash.player.application.ports.PlayerRepository;
import fr.ancyracademy.esportsclash.player.domain.model.Player;
import fr.ancyracademy.esportsclash.player.domain.viewmodel.PlayerViewModel;
import fr.ancyracademy.esportsclash.player.infrastructure.spring.RenamePlayerDTO;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


public class GetPlayerByIdE2Etests extends IntegrationTests {

    @Autowired
    private PlayerRepository playerRepository;


    @Test
    public void shouldGetPlayerById() throws Exception {

        var player = new Player("123", "player");
        playerRepository.save(player);

        var result = mockMvc.perform(
                        MockMvcRequestBuilders.get(
                                        "/players/" + player.getId())
                                .header("Authorization", createJwt()))
                .andReturn();

        var viewModel = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                PlayerViewModel.class
        );


        Assert.assertEquals(player.getId(), viewModel.getId());
        Assert.assertEquals(player.getName(), viewModel.getName());
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

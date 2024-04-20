package fr.ancyracademy.esportsclash.team.e2e;

import fr.ancyracademy.esportsclash.IntegrationTests;
import fr.ancyracademy.esportsclash.player.application.ports.PlayerRepository;
import fr.ancyracademy.esportsclash.player.domain.model.Player;
import fr.ancyracademy.esportsclash.team.application.ports.TeamRepository;
import fr.ancyracademy.esportsclash.team.domain.model.Role;
import fr.ancyracademy.esportsclash.team.domain.model.Team;
import fr.ancyracademy.esportsclash.team.domain.viewmodel.TeamViewModel;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class GetTeamByIdE2ETests extends IntegrationTests {
    Player player;
    Team team;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @BeforeEach
    void setup() {
        team = new Team("1", "Team 1");
        player = new Player("1", "Player");

        team.addMember(player.getId(), Role.TOP);

        playerRepository.save(player);
        teamRepository.save(team);

        flushAndClear();
    }

    @Test
    public void shouldGetTheTeam() throws Exception {


        var result = mockMvc.perform(MockMvcRequestBuilders.get("/teams/" + team.getId())
                        .header("Authorization", createJwt())
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        var viewModel = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                TeamViewModel.class
        );

        Assert.assertEquals(team.getId(), viewModel.getId());
        Assert.assertEquals(team.getName(), viewModel.getName());

        var firstMembers = viewModel.getMembers().get(0);
        Assert.assertEquals(player.getId(), firstMembers.getPlayerId());
        Assert.assertEquals(player.getName(), firstMembers.getPlayerName());
        Assert.assertEquals("TOP", firstMembers.getRole());
    }
}

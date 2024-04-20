package fr.ancyracademy.esportsclash.team.e2e;

import fr.ancyracademy.esportsclash.IntegrationTests;
import fr.ancyracademy.esportsclash.player.application.ports.PlayerRepository;
import fr.ancyracademy.esportsclash.player.domain.model.Player;
import fr.ancyracademy.esportsclash.team.application.ports.TeamRepository;
import fr.ancyracademy.esportsclash.team.domain.model.Role;
import fr.ancyracademy.esportsclash.team.domain.model.Team;
import fr.ancyracademy.esportsclash.team.infrastructure.spring.dto.AddPlayerToTeamDto;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class AddPlayerToTeamE2ETests extends IntegrationTests {

    Player player;
    Team team;
    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @BeforeEach
    void setup() {
        teamRepository.clear();
        playerRepository.clear();
        team = new Team("1", "Team 1");
        player = new Player("1", "Player");
        teamRepository.save(team);
        playerRepository.save(player);
    }

    @Test
    public void shouldAddPlayerToTeam() throws Exception {

        var dto = new AddPlayerToTeamDto(
                team.getId(), player.getId(), "TOP"
        );

        mockMvc.perform(MockMvcRequestBuilders.post("/teams/add-player-to-team")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto))
                        .header("Authorization", createJwt())
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        var updatedTeam = teamRepository.findById(team.getId()).get();

        Assert.assertTrue(updatedTeam.hasMember(player.getId(), Role.TOP));

    }
}

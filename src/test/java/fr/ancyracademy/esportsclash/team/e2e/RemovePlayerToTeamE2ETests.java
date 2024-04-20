package fr.ancyracademy.esportsclash.team.e2e;

import fr.ancyracademy.esportsclash.IntegrationTests;
import fr.ancyracademy.esportsclash.player.application.ports.PlayerRepository;
import fr.ancyracademy.esportsclash.player.domain.model.Player;
import fr.ancyracademy.esportsclash.team.application.ports.TeamRepository;
import fr.ancyracademy.esportsclash.team.domain.model.Role;
import fr.ancyracademy.esportsclash.team.domain.model.Team;
import fr.ancyracademy.esportsclash.team.infrastructure.spring.dto.RemovePlayerFromTeamDto;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class RemovePlayerToTeamE2ETests extends IntegrationTests {
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
    }

    @Test
    public void shouldRemovePlayerToTeam() throws Exception {

        var dto = new RemovePlayerFromTeamDto(
                team.getId(), player.getId()
        );

        mockMvc.perform(MockMvcRequestBuilders.post("/teams/remove-player-from-team")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto))
                        .header("Authorization", createJwt())
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        var updatedTeam = teamRepository.findById(team.getId()).get();

        Assert.assertFalse(updatedTeam.hasMember(player.getId(), Role.TOP));
    }
}

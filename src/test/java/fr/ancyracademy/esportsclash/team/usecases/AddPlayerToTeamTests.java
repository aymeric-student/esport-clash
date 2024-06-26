package fr.ancyracademy.esportsclash.team.usecases;

import fr.ancyracademy.esportsclash.core.domain.exception.NotFoundException;
import fr.ancyracademy.esportsclash.player.domain.model.Player;
import fr.ancyracademy.esportsclash.player.infrastructure.persistence.ram.InMemoryPlayerRepository;
import fr.ancyracademy.esportsclash.team.application.usecases.AddPlayerToTeamCommand;
import fr.ancyracademy.esportsclash.team.application.usecases.AddPlayerToTeamCommandHandler;
import fr.ancyracademy.esportsclash.team.domain.model.Role;
import fr.ancyracademy.esportsclash.team.domain.model.Team;
import fr.ancyracademy.esportsclash.team.infrastructure.persistence.ram.InMemoryTeamRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AddPlayerToTeamTests {
    InMemoryTeamRepository teamRepository = new InMemoryTeamRepository();
    InMemoryPlayerRepository playerRepository = new InMemoryPlayerRepository();

    Team team = new Team("1", "Team ");
    Player player = new Player("1", "Player");

    AddPlayerToTeamCommandHandler addPlayerToTeamCommandHandler() {
        return new AddPlayerToTeamCommandHandler(playerRepository, teamRepository);
    }

    @BeforeEach
    void setup() {
        teamRepository.clear();
        playerRepository.clear();
        teamRepository.save(team);
        playerRepository.save(player);
    }

    @Test
    void shouldAddPlayerToTeam() {
        var command = new AddPlayerToTeamCommand(player.getId(), team.getId(), Role.TOP);
        var commandHandler = addPlayerToTeamCommandHandler();
        commandHandler.handle(command);
        var team = teamRepository.findById(command.getTeamId()).get();
        Assert.assertTrue(team.hasMember(command.getPlayerId(), command.getRole()));
    }

    @Test
    void whenPlayerDoesNotExist_shouldFail() {
        var command = new AddPlayerToTeamCommand("garbage", team.getId(), Role.TOP);
        var commandHandler = addPlayerToTeamCommandHandler();

        var exception = Assert.assertThrows(NotFoundException.class, () -> {
            commandHandler.handle(command);
        });

        Assert.assertEquals("Player with the key garbage not found", exception.getMessage());
    }

    @Test
    void whenTeamDoesNotExist_shouldFail() {
        var command = new AddPlayerToTeamCommand(player.getId(), "garbage", Role.TOP);
        var commandHandler = addPlayerToTeamCommandHandler();

        var exception = Assert.assertThrows(
                NotFoundException.class, () -> {
                    commandHandler.handle(command);
                });

        Assert.assertEquals("Team with the key garbage not found", exception.getMessage());
    }
}

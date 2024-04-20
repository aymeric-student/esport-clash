package fr.ancyracademy.esportsclash.team.usecases;

/*
public class RemovePlayerToTeamTests {
}
*/


import fr.ancyracademy.esportsclash.core.domain.exception.NotFoundException;
import fr.ancyracademy.esportsclash.team.application.usecases.RemovePlayerFromTeamCommand;
import fr.ancyracademy.esportsclash.team.application.usecases.RemovePlayerFromTeamCommandHandler;
import fr.ancyracademy.esportsclash.team.domain.model.Role;
import fr.ancyracademy.esportsclash.team.domain.model.Team;
import fr.ancyracademy.esportsclash.team.infrastructure.persistence.ram.InMemoryTeamRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RemovePlayerToTeamTests {
    InMemoryTeamRepository teamRepository = new InMemoryTeamRepository();

    Team team;
    String playerId = "playerId";

    Role role = Role.TOP;

    RemovePlayerFromTeamCommandHandler removePlayerToTeamCommandHandler() {
        return new RemovePlayerFromTeamCommandHandler(teamRepository);
    }

    @BeforeEach
    void setup() {
        teamRepository.clear();
        team = new Team("1", "Team 1");
        team.addMember(playerId, role);
        teamRepository.save(team);
    }

    @Test
    void shouldRemovePlayerFromTeam() {
        var command = new RemovePlayerFromTeamCommand(
                playerId, team.getId());

        var commandHandler = removePlayerToTeamCommandHandler();
        commandHandler.handle(command);
        var team = teamRepository.findById(command.getTeamId()).get();
        Assert.assertFalse(team.hasMember(playerId, role));
    }

    @Test
    void whenTeamDoesNotExist_shouldFail() {
        var command = new RemovePlayerFromTeamCommand(playerId, "garbage");
        var commandHandler = removePlayerToTeamCommandHandler();

        var exception = Assert.assertThrows(NotFoundException.class, () -> {
            commandHandler.handle(command);
        });

        Assert.assertEquals("Team with the key garbage not found", exception.getMessage());
    }

}

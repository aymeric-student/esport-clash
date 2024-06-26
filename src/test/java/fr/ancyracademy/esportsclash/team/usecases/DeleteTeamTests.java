package fr.ancyracademy.esportsclash.team.usecases;

import fr.ancyracademy.esportsclash.core.domain.exception.NotFoundException;
import fr.ancyracademy.esportsclash.team.application.usecases.DeleteTeamCommand;
import fr.ancyracademy.esportsclash.team.application.usecases.DeleteTeamcommandHandler;
import fr.ancyracademy.esportsclash.team.domain.model.Team;
import fr.ancyracademy.esportsclash.team.infrastructure.persistence.ram.InMemoryTeamRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DeleteTeamTests {
    InMemoryTeamRepository teamRepository = new InMemoryTeamRepository();
    Team team = new Team("1", "Team ");

    DeleteTeamcommandHandler deleteTeamCommandHandler() {
        return new DeleteTeamcommandHandler(teamRepository);
    }

    @BeforeEach
    void setup() {
        teamRepository.clear();
        teamRepository.save(team);
    }

    @Test
    public void shouldDeleteTeam() {
        var command = new DeleteTeamCommand(team.getId());
        var handler = deleteTeamCommandHandler();
        handler.handle(command);

        var teamQuery = teamRepository.findById(team.getId());
        Assert.assertFalse(teamQuery.isPresent());
    }

    @Test
    void whenTeamDoesNotExist_shouldFail() {
        var command = new DeleteTeamCommand("garbage");
        var commandHandler = deleteTeamCommandHandler();


        var exception = Assert.assertThrows(NotFoundException.class, () -> {
            commandHandler.handle(command);
        });

        Assert.assertEquals("Team with the key garbage not found", exception.getMessage());
    }
}

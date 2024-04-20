package fr.ancyracademy.esportsclash.team.usecases;

import fr.ancyracademy.esportsclash.team.application.usecases.CreateTeamCommand;
import fr.ancyracademy.esportsclash.team.application.usecases.CreateTeamCommandHandler;
import fr.ancyracademy.esportsclash.team.infrastructure.persistence.ram.InMemoryTeamRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class CreateTeamTests {
    InMemoryTeamRepository teamRepository = new InMemoryTeamRepository();

    CreateTeamCommandHandler createTeamCommandHandler() {
        return new CreateTeamCommandHandler(teamRepository);
    }

    @Test
    public void shouldCreateTeam() {
        var command = new CreateTeamCommand("Team 1");
        var handler = createTeamCommandHandler();
        var response = handler.handle(command);

        var teamQuery = teamRepository.findById(response.getId());
        Assert.assertTrue(teamQuery.isPresent());

        var team = teamQuery.get();

        Assert.assertEquals("Team 1", team.getName());
    }
}

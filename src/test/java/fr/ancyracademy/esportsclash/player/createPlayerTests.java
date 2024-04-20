package fr.ancyracademy.esportsclash.player;

import fr.ancyracademy.esportsclash.player.application.usecases.CreatePlayerCommand;
import fr.ancyracademy.esportsclash.player.application.usecases.CreatePlayerCommandHandler;
import fr.ancyracademy.esportsclash.player.domain.model.Player;
import fr.ancyracademy.esportsclash.player.infrastructure.persistence.ram.InMemoryPlayerRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class createPlayerTests {

    @Test
    void createPlayer() {
        var repository = new InMemoryPlayerRepository();
        var useCase = new CreatePlayerCommandHandler(repository);

        var command = new CreatePlayerCommand("John Doe");
        var result = useCase.handle(command);

        var expectedPlayer = new Player(result.getId(), "John Doe");

        Player actualPlayer = repository.findById(expectedPlayer.getId()).get();
        Assert.assertEquals("John Doe", actualPlayer.getName());
    }
}

package fr.ancyracademy.esportsclash.player.infrastructure.spring;

import fr.ancyracademy.esportsclash.player.application.ports.PlayerRepository;
import fr.ancyracademy.esportsclash.player.application.usecases.CreatePlayerCommandHandler;
import fr.ancyracademy.esportsclash.player.application.usecases.DeletePlayerCommandHandler;
import fr.ancyracademy.esportsclash.player.application.usecases.GetPlayerByIdCommandHandler;
import fr.ancyracademy.esportsclash.player.application.usecases.RenamePlayerCommandHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PlayerUseCaseConfiguration {
    @Bean
    public CreatePlayerCommandHandler createPlayerUseCase(PlayerRepository playerRepository) {
        return new CreatePlayerCommandHandler(playerRepository);
    }

    @Bean
    public RenamePlayerCommandHandler renamePlayerUseCase(PlayerRepository playerRepository) {
        return new RenamePlayerCommandHandler(playerRepository);
    }

    @Bean
    public DeletePlayerCommandHandler deletePlayerUseCase(PlayerRepository playerRepository) {
        return new DeletePlayerCommandHandler(playerRepository);
    }

    @Bean
    public GetPlayerByIdCommandHandler getPlayerByIdUseCase(PlayerRepository playerRepository) {
        return new GetPlayerByIdCommandHandler(playerRepository);
    }
}

package fr.ancyracademy.esportsclash.team.application.usecases;

import an.awesome.pipelinr.Command;
import fr.ancyracademy.esportsclash.core.domain.exception.NotFoundException;
import fr.ancyracademy.esportsclash.player.application.ports.PlayerRepository;
import fr.ancyracademy.esportsclash.team.application.ports.TeamRepository;

public class AddPlayerToTeamCommandHandler implements Command.Handler<AddPlayerToTeamCommand, Void> {
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;

    public AddPlayerToTeamCommandHandler(PlayerRepository playerRepository, TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
    }

    @Override
    public Void handle(AddPlayerToTeamCommand command) {
        var player = playerRepository.findById(command.getPlayerId())
                .orElseThrow(
                        () -> new NotFoundException("Player", command.getPlayerId()));

        var team = teamRepository
                .findById(command.getTeamId())
                .orElseThrow(
                        () -> new NotFoundException("Team", command.getTeamId()));

        team.addMember(player.getId(), command.getRole());
        teamRepository.save(team);

        return null;
    }
}

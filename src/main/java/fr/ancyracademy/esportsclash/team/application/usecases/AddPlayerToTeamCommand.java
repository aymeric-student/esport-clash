package fr.ancyracademy.esportsclash.team.application.usecases;

import an.awesome.pipelinr.Command;
import fr.ancyracademy.esportsclash.team.domain.model.Role;

public class AddPlayerToTeamCommand implements Command<Void> {
    private final String teamId;
    private final String playerId;

    private final Role role;

    public AddPlayerToTeamCommand(String playerId, String teamId, Role role) {
        this.teamId = teamId;
        this.playerId = playerId;
        this.role = role;
    }

    public String getTeamId() {
        return teamId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public Role getRole() {
        return role;
    }
}

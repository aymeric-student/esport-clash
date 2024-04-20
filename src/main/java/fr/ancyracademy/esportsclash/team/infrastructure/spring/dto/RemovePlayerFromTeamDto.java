package fr.ancyracademy.esportsclash.team.infrastructure.spring.dto;

public class RemovePlayerFromTeamDto {
    private String playerId;
    private String teamId;

    public RemovePlayerFromTeamDto(String playerId, String teamId) {
        this.playerId = playerId;
        this.teamId = teamId;
    }

    public RemovePlayerFromTeamDto() {
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getTeamId() {
        return teamId;
    }
}

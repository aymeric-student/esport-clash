package fr.ancyracademy.esportsclash.team.application.ports;

import fr.ancyracademy.esportsclash.team.domain.viewmodel.TeamViewModel;

public interface TeamQuery {
    TeamViewModel getTeamById(String id);
}

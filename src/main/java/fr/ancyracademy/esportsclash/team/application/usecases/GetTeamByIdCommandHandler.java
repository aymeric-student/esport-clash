package fr.ancyracademy.esportsclash.team.application.usecases;

import an.awesome.pipelinr.Command;
import fr.ancyracademy.esportsclash.team.application.ports.TeamQuery;
import fr.ancyracademy.esportsclash.team.domain.viewmodel.TeamViewModel;

public class GetTeamByIdCommandHandler implements Command.Handler<GetTeamByIdCommand, TeamViewModel> {
    private final TeamQuery teamQuery;

    public GetTeamByIdCommandHandler(TeamQuery teamQuery) {
        this.teamQuery = teamQuery;
    }

    @Override
    public TeamViewModel handle(GetTeamByIdCommand command) {
        return teamQuery.getTeamById(command.getId());
    }
}

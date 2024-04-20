package fr.ancyracademy.esportsclash.team.infrastructure.spring.controllers;

import an.awesome.pipelinr.Pipeline;
import fr.ancyracademy.esportsclash.player.domain.viewmodel.IdResponse;
import fr.ancyracademy.esportsclash.team.application.usecases.*;
import fr.ancyracademy.esportsclash.team.domain.viewmodel.TeamViewModel;
import fr.ancyracademy.esportsclash.team.infrastructure.spring.dto.AddPlayerToTeamDto;
import fr.ancyracademy.esportsclash.team.infrastructure.spring.dto.CreateTeamDTO;
import fr.ancyracademy.esportsclash.team.infrastructure.spring.dto.RemovePlayerFromTeamDto;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teams")
@Transactional
public class TeamController {
    private final Pipeline pipeline;

    public TeamController(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamViewModel> getTeamById(@PathVariable String id) {
        var result = this.pipeline.send(new GetTeamByIdCommand(id));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<IdResponse> create(@RequestBody CreateTeamDTO createTeamDTO) {
        var result = this.pipeline.send(new CreateTeamCommand(createTeamDTO.getName()));
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PostMapping("/add-player-to-team")
    public ResponseEntity<Void> addPlayerToTeam(@RequestBody AddPlayerToTeamDto dto) {
        var result = this.pipeline.send(
                new AddPlayerToTeamCommand(
                        dto.getPlayerId(),
                        dto.getTeamId(),
                        dto.getRole())
        );
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/remove-player-from-team")
    public ResponseEntity<Void> removePlayerFromTeam(@RequestBody RemovePlayerFromTeamDto dto) {
        var result = this.pipeline.send(new RemovePlayerFromTeamCommand(
                dto.getPlayerId(),
                dto.getTeamId()));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<IdResponse> deleteTeam(@PathVariable String id) {
        var result = this.pipeline.send(new DeleteTeamCommand(id));
        return ResponseEntity.noContent().build();
    }
}

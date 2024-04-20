package fr.ancyracademy.esportsclash.player.infrastructure.spring;

import an.awesome.pipelinr.Pipeline;
import fr.ancyracademy.esportsclash.player.application.usecases.CreatePlayerCommand;
import fr.ancyracademy.esportsclash.player.application.usecases.DeletePlayerCommand;
import fr.ancyracademy.esportsclash.player.application.usecases.GetPlayerByIdCommand;
import fr.ancyracademy.esportsclash.player.application.usecases.RenamePlayerCommand;
import fr.ancyracademy.esportsclash.player.domain.viewmodel.IdResponse;
import fr.ancyracademy.esportsclash.player.domain.viewmodel.PlayerViewModel;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/players")
@Transactional
public class PlayerController {
    private final Pipeline pipeline;

    public PlayerController(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlayerViewModel> get(
            @PathVariable("id") String id
    ) {
        var result = this.pipeline.send(new GetPlayerByIdCommand(id));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<IdResponse> create(@RequestBody CreatePlayerDTO createPlayerDTO) {
        var result = this.pipeline.send(new CreatePlayerCommand(createPlayerDTO.getName()));
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/name")
    public ResponseEntity<IdResponse> create(
            @RequestBody RenamePlayerDTO createPlayerDTO,
            @PathVariable("id") String id
    ) {
        var result = this.pipeline.send(new RenamePlayerCommand(id, createPlayerDTO.getName()));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<IdResponse> delete(@PathVariable("id") String id) {
        var result = this.pipeline.send(new DeletePlayerCommand(id));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

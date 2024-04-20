package fr.ancyracademy.esportsclash.auth.infrastructure.spring;

import an.awesome.pipelinr.Pipeline;
import fr.ancyracademy.esportsclash.auth.application.usescases.LoginCommand;
import fr.ancyracademy.esportsclash.auth.application.usescases.RegisterCommand;
import fr.ancyracademy.esportsclash.auth.domain.viewmodel.LoggedUserViewModel;
import fr.ancyracademy.esportsclash.player.domain.viewmodel.IdResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Transactional
public class AuthController {
    private final Pipeline pipeline;

    public AuthController(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    @PostMapping("/register")
    public ResponseEntity<IdResponse> register(
            @Valid @RequestBody RegisterDTO registerDTO
    ) {
        return new ResponseEntity(pipeline.send(
                new RegisterCommand(
                        registerDTO.getEmailAddress(),
                        registerDTO.getPassword()
                )
        ), HttpStatus.CREATED
        );
    }


    @PostMapping("/login")
    public ResponseEntity<LoggedUserViewModel> login(
            @Valid @RequestBody LoginDto loginDto
    ) {
        return ResponseEntity.ok(pipeline.send(
                new LoginCommand(
                        loginDto.getEmailAddress(),
                        loginDto.getPassword()
                )));
    }
}

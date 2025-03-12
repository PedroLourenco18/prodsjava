package br.com.pedroloureco.prodsjava.controller;

import br.com.pedroloureco.prodsjava.domain.reponse.AuthenticationResponse;
import br.com.pedroloureco.prodsjava.domain.user.UserAuthenticationDTO;
import br.com.pedroloureco.prodsjava.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid UserAuthenticationDTO user){
        String token = service.login(user);

        AuthenticationResponse response = new AuthenticationResponse(
                false,
                "User authenticated",
                token
        );

        return ResponseEntity.ok(response);
    }
}

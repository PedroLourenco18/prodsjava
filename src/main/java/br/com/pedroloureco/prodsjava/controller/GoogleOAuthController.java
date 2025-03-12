package br.com.pedroloureco.prodsjava.controller;

import br.com.pedroloureco.prodsjava.domain.reponse.AuthenticationResponse;
import br.com.pedroloureco.prodsjava.service.GoogleOAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oauth2/google")
@RequiredArgsConstructor
public class GoogleOAuthController {
    private final GoogleOAuthService service;

    @GetMapping()
    public ResponseEntity<String> googleLoginUrl() {
        return ResponseEntity.ok(service.getGoogleLoginUrl());
    }

    @GetMapping("/callback")
    public ResponseEntity<AuthenticationResponse> googleAuthentication(@RequestParam("code") String code){
        String token = service.authenticate(code);

        AuthenticationResponse response = new AuthenticationResponse(
                false,
                "User Authenticated",
                token
        );

        return ResponseEntity.ok(response);
    }
}

package br.com.pedroloureco.prodsjava.domain.reponse;

public record AuthenticationResponse(
        boolean error,
        String message,
        String token
) { }
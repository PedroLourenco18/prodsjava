package br.com.pedroloureco.prodsjava.domain.reponse;

public record BasicResponse(
        boolean error,
        String message
){ }

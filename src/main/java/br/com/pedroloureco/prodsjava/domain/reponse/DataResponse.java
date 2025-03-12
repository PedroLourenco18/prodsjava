package br.com.pedroloureco.prodsjava.domain.reponse;

public record DataResponse<T> (
        boolean error,
        String message,
        T data
){ }
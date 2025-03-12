package br.com.pedroloureco.prodsjava.domain.reponse;

import lombok.*;

import java.util.HashMap;
import java.util.Map;

public record FieldsErrorResponse(
        boolean error,
        String message,
        Map<String, String> fields
) { }

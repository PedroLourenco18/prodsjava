package br.com.pedroloureco.prodsjava.domain.user;

import jakarta.persistence.Column;
import lombok.Data;

import java.util.UUID;

@Data
public class UserOutputDTO{
    private UUID id;

    private String name;

    private String email;

    private UserRole role;
}

package br.com.pedroloureco.prodsjava.domain.user;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
public class UserInputDTO {
    @NotBlank(message = "Name can not be null")
    @Size(max = 100, message = "Name is too long, the max length is 100 characters")
    private String name;

    @NotBlank(message = "Email can not be null")
    @Email(message = "Email format is invalid")
    @Size(max = 150, message = "Email is too long, the max length is 150 characters")
    private String email;

    @NotBlank(message = "Password can not be null")
    @Size(min = 6, max = 40, message = "Password length must be between 6 and 40")
    private String password;

    @NotNull(message = "Role can not be null")
    private UserRole role;
}

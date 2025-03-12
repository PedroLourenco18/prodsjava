package br.com.pedroloureco.prodsjava.domain.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserAuthenticationDTO {

    @NotBlank(message = "Email can not be null")
    @Email(message = "Email format is invalid")
    @Size(max = 150, message = "Email is too long, the max length is 150 characters")
    private String email;

    @NotBlank(message = "Password can not be null")
    @Size(min = 6, max = 40, message = "Password length must be between 6 and 40")
    private String password;
}

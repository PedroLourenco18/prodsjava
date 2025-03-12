package br.com.pedroloureco.prodsjava.controller;

import br.com.pedroloureco.prodsjava.domain.user.UserInputDTO;
import br.com.pedroloureco.prodsjava.domain.user.UserOutputDTO;
import br.com.pedroloureco.prodsjava.domain.reponse.BasicResponse;
import br.com.pedroloureco.prodsjava.domain.reponse.DataResponse;
import br.com.pedroloureco.prodsjava.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@EnableMethodSecurity
public class UserController {
    private final UserService service;

    @PostMapping
    public ResponseEntity<DataResponse<UserOutputDTO>> save(@RequestBody @Valid UserInputDTO user){
        UserOutputDTO savedUser = service.save(user);

        DataResponse<UserOutputDTO> response = new DataResponse<>(
                false,
                "User successfully created",
                savedUser
        );

        URI location = URI.create("http://localhost:8080/user/" + savedUser.getId());

        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataResponse<UserOutputDTO>> findById(@PathVariable UUID id){
        UserOutputDTO user = service.findById(id);

        DataResponse<UserOutputDTO> response = new DataResponse<>(
                false,
                "User found",
                user
        );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BasicResponse> update(
            @PathVariable UUID id,
            @RequestBody @Valid UserInputDTO user){
        service.update(id, user);

        BasicResponse response = new BasicResponse(
                false,
                "User updated"
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BasicResponse> delete(@PathVariable UUID id){
        service.delete(id);

        BasicResponse response = new BasicResponse(
                false,
                "User deleted"
        );

        return ResponseEntity.ok(response);
    }
}

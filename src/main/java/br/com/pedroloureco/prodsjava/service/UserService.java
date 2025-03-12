package br.com.pedroloureco.prodsjava.service;

import br.com.pedroloureco.prodsjava.domain.mapper.UserMapper;
import br.com.pedroloureco.prodsjava.domain.product.ProductInputDTO;
import br.com.pedroloureco.prodsjava.domain.user.User;
import br.com.pedroloureco.prodsjava.domain.user.UserInputDTO;
import br.com.pedroloureco.prodsjava.domain.user.UserOutputDTO;
import br.com.pedroloureco.prodsjava.exception.ElementNotFoundException;
import br.com.pedroloureco.prodsjava.exception.EmailAlreadyExistsException;
import br.com.pedroloureco.prodsjava.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public UserOutputDTO save(UserInputDTO user){
        if(repository.existsByEmail(user.getEmail())){
            throw new EmailAlreadyExistsException(
                    "The email '" + user.getEmail() + "' has already been registered");
        }

        User newUser = mapper.toEntity(user);

        String encodedPassword = passwordEncoder.encode(newUser.getPassword());
        newUser.setPassword(encodedPassword);

        User savedUser = repository.save(newUser);
        return mapper.toDTO(savedUser);
    }

    public UserOutputDTO findById(UUID id){
        User user = repository.findById(id).orElseThrow(
                () -> new ElementNotFoundException("User not found")
        );
        return mapper.toDTO(user);
    }

    public void update(UUID id, UserInputDTO updatedUserDTO) {
        if (!repository.existsById(id)) {
            throw new ElementNotFoundException("Can not updated an unsaved user");
        }

        if(repository.existsByEmailIsAndIdIsNot(updatedUserDTO.getEmail(), id)){
            throw new EmailAlreadyExistsException(
                    "The email '" + updatedUserDTO.getEmail() + "' has already been registered");
        }

        User updatedUser = mapper.toEntity(updatedUserDTO);
        updatedUser.setId(id);

        String encodedPassword = passwordEncoder.encode(updatedUser.getPassword());
        updatedUser.setPassword(encodedPassword);

        repository.save(updatedUser);
    }

    public void delete(UUID id){
        if(!repository.existsById(id)){
            throw new ElementNotFoundException("Can not delete an unsaved user");
        }

        repository.deleteById(id);
    }
}

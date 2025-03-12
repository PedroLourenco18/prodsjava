package br.com.pedroloureco.prodsjava.service;

import br.com.pedroloureco.prodsjava.domain.user.UserAuthenticationDTO;
import br.com.pedroloureco.prodsjava.repository.UserRepository;
import br.com.pedroloureco.prodsjava.security.CustomUserDetails;
import br.com.pedroloureco.prodsjava.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final TokenService tokenService;

    public String login(UserAuthenticationDTO user){
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(
                user.getEmail(), user.getPassword());

        Authentication authentication = authenticationManager.authenticate(usernamePassword);

        return tokenService.generateToken(((CustomUserDetails) authentication.getPrincipal()).getUser());
    }
}

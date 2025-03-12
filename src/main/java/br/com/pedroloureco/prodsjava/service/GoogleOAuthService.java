package br.com.pedroloureco.prodsjava.service;

import br.com.pedroloureco.prodsjava.domain.google_user_info.GoogleUserInfo;
import br.com.pedroloureco.prodsjava.domain.user.User;
import br.com.pedroloureco.prodsjava.domain.user.UserRole;
import br.com.pedroloureco.prodsjava.repository.UserRepository;
import br.com.pedroloureco.prodsjava.security.TokenService;
import br.com.pedroloureco.prodsjava.util.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class GoogleOAuthService {

    private final GoogleAuthorizationCodeFlow googleAuthorizationCodeFlow;

    private final TokenService tokenService;

    private final UserRepository userRepository;

    private final String DEFAULT_PASSWORD = "123456";

    @Value("${google.redirect.uri}")
    private String redirectUri;

    public String getGoogleLoginUrl (){
        return googleAuthorizationCodeFlow.newAuthorizationUrl()
                .setRedirectUri(redirectUri)
                .build();
    }

    public String authenticate(String code){
        try {
            GoogleTokenResponse tokenResponse = googleAuthorizationCodeFlow.newTokenRequest(code)
                    .setRedirectUri(redirectUri)
                    .execute();

            HttpRequestFactory requestFactory = new NetHttpTransport().createRequestFactory();
            GenericUrl userInfoUrl = new GenericUrl("https://www.googleapis.com/oauth2/v3/userinfo");
            HttpRequest request = null;
                request = requestFactory.buildGetRequest(userInfoUrl);
            request.getHeaders().setAuthorization("Bearer " + tokenResponse.getAccessToken());

            String userInfo = request.execute().parseAsString();

            GoogleUserInfo googleUserInfo =  JsonUtils.mapper().readValue(userInfo, GoogleUserInfo.class);

            User user = userRepository.findByEmail(googleUserInfo.getEmail());

            if(user == null){
                user = saveUser(googleUserInfo);
            }

            return tokenService.generateToken(user);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private User saveUser(GoogleUserInfo userInfo){
        User user = new User();
        user.setName(userInfo.getName());
        user.setEmail(userInfo.getEmail());
        user.setPassword(DEFAULT_PASSWORD);
        user.setRole(UserRole.USER);

        return userRepository.save(user);
    }
}

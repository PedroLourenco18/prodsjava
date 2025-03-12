package br.com.pedroloureco.prodsjava.domain.google_user_info;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GoogleUserInfo {
    String sub;

    String name;

    @JsonProperty("given_name")
    String givenName;

    String email;

    String picture;

    @JsonProperty("email_verified")
    String emailVerified;
}

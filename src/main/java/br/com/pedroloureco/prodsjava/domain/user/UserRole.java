package br.com.pedroloureco.prodsjava.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public enum UserRole {
    USER(List.of()),
    ADMIN(List.of(UserRole.USER));

    private final List<UserRole> allMinorRolesObtained;
}

package br.com.pedroloureco.prodsjava.repository;

import br.com.pedroloureco.prodsjava.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByEmailIsAndIdIsNot(String email, UUID id);

    boolean existsByEmailAndId(String email, UUID id);
}

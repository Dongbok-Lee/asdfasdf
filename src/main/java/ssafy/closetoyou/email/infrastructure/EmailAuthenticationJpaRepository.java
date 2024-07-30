package ssafy.closetoyou.email.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailAuthenticationJpaRepository extends JpaRepository<EmailAuthenticationEntity,Long> {

    Optional<EmailAuthenticationEntity> findByEmail(String email);
    boolean existsByEmail(String email);
}

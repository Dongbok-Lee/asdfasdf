package ssafy.closetoyou.user.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity,Long> {

    boolean existsByEmailAndDeleted(String email, boolean deleted);
    Optional<UserEntity> findByUserIdAndDeleted(long id, boolean deleted);

    Optional<UserEntity> findByEmailAndDeleted(String email, boolean deleted);
}

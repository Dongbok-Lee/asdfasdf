package ssafy.closetoyou.user.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ssafy.closetoyou.global.error.exception.CloseToYouException;
import ssafy.closetoyou.global.error.exception.ErrorCode;
import ssafy.closetoyou.user.domain.User;


@RequiredArgsConstructor
@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    public User saveUser(User user) {
        return userJpaRepository.save(UserEntity.fromModel(user)).toModel();
    }

    public boolean existsEmail(String email) {
        return userJpaRepository.existsByEmailAndDeleted(email, false);
    }

    @Override
    public User findById(Long id) {
        return userJpaRepository.findByUserIdAndDeleted(id, false).orElseThrow(
                () -> new CloseToYouException(ErrorCode.NOT_EXIST_USER)).toModel();
    }

    @Override
    public User findByEmail(String email) {
        return userJpaRepository.findByEmailAndDeleted(email, false).orElseThrow(
                () -> new CloseToYouException(ErrorCode.NOT_EXIST_USER)).toModel();
    }
}

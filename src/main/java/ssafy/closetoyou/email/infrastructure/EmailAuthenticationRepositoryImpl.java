package ssafy.closetoyou.email.infrastructure;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ssafy.closetoyou.global.error.exception.CloseToYouException;
import ssafy.closetoyou.global.error.exception.ErrorCode;
import ssafy.closetoyou.email.domain.EmailAuthentication;
import ssafy.closetoyou.email.service.port.EmailAuthenticationRepository;

@RequiredArgsConstructor
@Repository
public class EmailAuthenticationRepositoryImpl implements EmailAuthenticationRepository {

    private final EmailAuthenticationJpaRepository emailAuthenticationJpaRepository;

    @Override
    public void save(EmailAuthentication emailAuthentication) {
        emailAuthenticationJpaRepository.save(EmailAuthenticationEntity.fromModel(emailAuthentication));
    }

    @Override
    public EmailAuthentication findEmailAuthenticationCode(String email) {
        return emailAuthenticationJpaRepository.findByEmail(email)
                .orElseThrow(() -> new CloseToYouException(ErrorCode.NOT_FOUND_MAIL_CODE))
                .toModel();
    }

    @Override
    public boolean existsEmailAuthenticationCode(String email) {
        return emailAuthenticationJpaRepository.existsByEmail(email);
    }
}

package ssafy.closetoyou.refreshtoken.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.closetoyou.refreshtoken.repository.RefreshTokenRepository;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class RefreshTokenService {

    private RefreshTokenRepository refreshTokenRepository;

    public Optional<Long> getUserIdByRefreshToken(String refreshToken) {
        return refreshTokenRepository.findUserIdByRefreshToken(refreshToken);
    }
}

package ssafy.closetoyou.email.controller.port;

import ssafy.closetoyou.email.domain.EmailAuthenticationCheck;

public interface EmailAuthenticationService {
    void sendEmail(String email);

    void checkAuthenticationCode(EmailAuthenticationCheck emailAuthenticationCheck);
}

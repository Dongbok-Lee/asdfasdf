package ssafy.closetoyou.email.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssafy.closetoyou.email.controller.port.EmailAuthenticationService;
import ssafy.closetoyou.email.domain.EmailAuthenticationCheck;
import ssafy.closetoyou.email.domain.SendEmail;
import ssafy.closetoyou.global.common.response.SuccessResponse;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/maillog")
public class EmailAuthenticationController {
    private final EmailAuthenticationService emailAuthenticationService;

    @PostMapping("/send")
    public ResponseEntity<SuccessResponse<Object>> sendEmail(@RequestBody SendEmail sendEmail) {
        emailAuthenticationService.sendEmail(sendEmail.getEmail());
        return ResponseEntity.ok().body(new SuccessResponse<>("이메일 전송에 성공하였습니다."));
    }

    @PostMapping("/check")
    public ResponseEntity<SuccessResponse<Object>> checkEmailAuthenticationCode(
            @Valid @RequestBody EmailAuthenticationCheck emailAuthenticationCheck) {
        emailAuthenticationService.checkAuthenticationCode(emailAuthenticationCheck);
        return ResponseEntity.ok(new SuccessResponse<>("해당 이메일 인증에 성공했습니다."));
    }
}

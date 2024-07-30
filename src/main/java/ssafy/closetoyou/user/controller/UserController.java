package ssafy.closetoyou.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ssafy.closetoyou.global.common.response.SuccessIdResponse;
import ssafy.closetoyou.global.security.login.userdetail.CustomUserDetail;
import ssafy.closetoyou.user.controller.port.UserService;
import ssafy.closetoyou.user.domain.User;
import ssafy.closetoyou.user.domain.UserSignUp;
import ssafy.closetoyou.user.controller.response.UserResponse;
import ssafy.closetoyou.global.common.response.SuccessResponse;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<SuccessResponse<UserResponse>> getUserInformation(Authentication authentication) {
        User user = ((CustomUserDetail)authentication.getPrincipal()).getUser();
        UserResponse userResponse = UserResponse.fromModel(user);
        return ResponseEntity.ok(
                new SuccessResponse<>("유저 조회에 성공하였습니다.", userResponse));
    }


    @PostMapping
    public ResponseEntity<SuccessIdResponse> userSignUp(@Valid @RequestBody UserSignUp userSignUp) {
        Long userId = userService.signUp(userSignUp);
        return ResponseEntity.ok(
                new SuccessIdResponse("일반 이용자 회원가입 성공", userId));
    }
}
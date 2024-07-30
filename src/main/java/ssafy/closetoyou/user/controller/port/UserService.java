package ssafy.closetoyou.user.controller.port;

import ssafy.closetoyou.user.domain.UserSignUp;

public interface UserService {
    Long signUp(UserSignUp userSignUp);
}

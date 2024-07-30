package ssafy.closetoyou.global.error.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    NO_TOKEN_EXCEPTION(HttpStatus.UNAUTHORIZED, "해당 토큰이 존재하지 않습니다."),
    EXPIRED_TOKEN_EXCEPTION(HttpStatus.UNAUTHORIZED, "해당 토큰이 만료되었습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "사용할 수 없는 토큰입니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "리프레시 토큰이 유효하지 않습니다"),
    INVALID_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "Access 토큰이 유효하지 않습니다"),
    LOGIN_FAIL(HttpStatus.BAD_REQUEST,"로그인 실패! 이메일이나 비밀번호를 확인해주세요."),
    OAUTH_LOGIN_FAIL(HttpStatus.BAD_REQUEST,"존재하지않거나 만료된 code를 사용해 인증에 실패했습니다."),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT,"이미 존재하는 이메일입니다."),
    INVALID_EMAIL_FORM(HttpStatus.NOT_FOUND,"유효하지 않은 형태의 이메일입니다."),
    NOT_EXIST_USER(HttpStatus.NOT_FOUND,"존재하지 않는 사용자입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
    REGISTER_EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "만료된 리프레쉬 토큰입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 정보의 사용자를 찾을 수 없습니다."),
    MAIL_SEND_FAIL(HttpStatus.INTERNAL_SERVER_ERROR,"메일 전송에 실패했습니다."),
    MAIL_CHECK_FAIL(HttpStatus.UNAUTHORIZED,"메일 인증에 실패했습니다."),
    NOT_FOUND_MAIL_CODE(HttpStatus.NOT_FOUND, "해당 메일 코드를 찾을 수 없습니다."),
    ALREADY_AUTHENTICATED_CODE(HttpStatus.BAD_REQUEST, "이미 인증이 완료된 이메일 입니다."),
    INVALID_CERTIFICATION_NUMBER(HttpStatus.BAD_REQUEST,"잘못된 인증번호를 입력하셨습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러"),
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "잘못된 파라미터가 포함되었습니다.");


    private final HttpStatus status;
    private final String message;


}

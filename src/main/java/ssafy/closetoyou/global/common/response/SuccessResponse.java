package ssafy.closetoyou.global.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
public class SuccessResponse<T> {
    private String message; // 결과 메시지

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private T data; // 반환 데이터

    public SuccessResponse(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public SuccessResponse(String message) {
        this.message = message;
    }
}

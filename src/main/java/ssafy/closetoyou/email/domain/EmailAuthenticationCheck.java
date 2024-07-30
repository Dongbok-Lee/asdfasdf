package ssafy.closetoyou.email.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class EmailAuthenticationCheck {

    @Email
    private String email;

    @NotBlank
    private int code;
}

package ssafy.closetoyou.email.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access= PROTECTED)
public class SendEmail {
    String email;
}

package ssafy.closetoyou.email.infrastructure;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ssafy.closetoyou.email.domain.EmailAuthentication;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class EmailAuthenticationEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long emailAuthenticationId;

    private String email;

    private int authenticationCode;

    private boolean isVerified;

    @CreatedDate
    private LocalDateTime createdDateTime;

    @Builder
    public EmailAuthenticationEntity(Long emailAuthenticationId, String email, int authenticationCode, boolean isVerified, LocalDateTime createdDateTime) {
        this.emailAuthenticationId = emailAuthenticationId;
        this.email = email;
        this.authenticationCode = authenticationCode;
        this.isVerified = isVerified;
        this.createdDateTime = createdDateTime;
    }

    public static EmailAuthenticationEntity fromModel(EmailAuthentication emailAuthentication) {
        return builder()
                .email(emailAuthentication.getEmail())
                .authenticationCode(emailAuthentication.getAuthenticationCode())
                .isVerified(emailAuthentication.isVerified())
                .build();
    }

    public EmailAuthentication toModel() {
        return EmailAuthentication.builder()
                .emailAuthenticationId(emailAuthenticationId)
                .email(email)
                .authenticationCode(authenticationCode)
                .isVerified(isVerified)
                .createdDateTime(createdDateTime)
                .build();
    }
}

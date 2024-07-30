package ssafy.closetoyou.user.infrastructure;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ssafy.closetoyou.user.domain.User;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity(name = "user")
@Getter
@NoArgsConstructor(access = PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class UserEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long userId;

    private String nickname;

    private String password;

    private String email;

    private boolean isHighContrast;

    private boolean deleted;

    @CreatedDate
    private LocalDateTime createdDateTime;

    @LastModifiedDate
    private LocalDateTime updatedDateTime;

    @Builder
    public UserEntity(Long userId, String nickname, String password, String email, boolean isHighContrast, boolean deleted) {
        this.userId = userId;
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.isHighContrast = isHighContrast;
        this.deleted = deleted;
    }



    public static UserEntity fromModel(User user){
        return builder()
                .nickname(user.getNickname())
                .password(user.getPassword())
                .email(user.getEmail())
                .isHighContrast(user.isHighContrast())
                .deleted(user.isDeleted())
                .build();
    }

    public User toModel() {
        return User.builder()
                .userId(userId)
                .nickname(nickname)
                .password(password)
                .email(email)
                .isHighContrast(isHighContrast)
                .createdDateTime(createdDateTime)
                .updatedDateTime(updatedDateTime)
                .deleted(deleted)
                .build();
    }
}

package eeit.OldProject.steve.DTO;

import eeit.OldProject.steve.Entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

public class PendingUser {
    @Getter
    private User user;
    @Getter
    private String verificationCode;
    private LocalDateTime expiresAt;

    public PendingUser(User user, String verificationCode, LocalDateTime expiresAt) {
        this.user = user;
        this.verificationCode = verificationCode;
        this.expiresAt = expiresAt;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }

}

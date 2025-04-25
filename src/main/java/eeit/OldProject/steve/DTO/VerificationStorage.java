package eeit.OldProject.steve.DTO;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class VerificationStorage {
    private final Map<String, PendingUser> verificationMap = new HashMap<>();

    public void save(String email, PendingUser pendingUser) {
        verificationMap.put(email, pendingUser);
    }

    public PendingUser get(String email) {
        return verificationMap.get(email);
    }

    public void remove(String email) {
        verificationMap.remove(email);
    }
}
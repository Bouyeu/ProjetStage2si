package d2a.sn.user.Service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class passWordService {
    private final BCryptPasswordEncoder passwordEncoder;
    public passWordService(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }
    public boolean checkPassword(String password, String hashedPassword) {
        return passwordEncoder.matches(password, hashedPassword);
    }
}

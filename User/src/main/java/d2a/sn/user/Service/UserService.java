package d2a.sn.user.Service;


import d2a.sn.user.Entite.User;
import d2a.sn.user.Exception.RessourceNotFound;
import d2a.sn.user.Repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public User registerUser(User user) {
        // Hacher le mot de passe avant de l'enregistrer
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        return userRepository.save(user);
    }
    public List<User> getAllUsersActive() {
        return userRepository.findByIsActiveTrue();
    }
    public ResponseEntity<String> masquerUser(Long userId) throws RessourceNotFound {
        User user = userRepository.findById(userId).orElseThrow(() -> new RessourceNotFound("n'est trouver"));
        user.setActive(false);
        userRepository.save(user);
        return ResponseEntity.ok().body("user suppprimer avec succé");
    }
}

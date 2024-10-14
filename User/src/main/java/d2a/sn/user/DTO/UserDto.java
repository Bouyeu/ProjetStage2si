package d2a.sn.user.DTO;

import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

        private Long id;
        private String nom_user;
        private String prenom_user;
        private String email;
        private String password; // Le mot de passe sera haché avant d'être envoyé
        private String telephone;

        public void setPassword(String password) {
            this.password =hashPassword(password);

        }

        private String hashPassword(String password) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            return encoder.encode(password);
        }
}

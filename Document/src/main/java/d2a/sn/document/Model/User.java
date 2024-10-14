package d2a.sn.document.Model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class User {
        private Long id;
        private String nom_user;
        private String prenom_user;
        private String email;
        private String password;
        private String telephone;
}

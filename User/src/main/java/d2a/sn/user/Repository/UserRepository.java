package d2a.sn.user.Repository;

import d2a.sn.user.Entite.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long id);
    List<User> findByIsActiveTrue();
}

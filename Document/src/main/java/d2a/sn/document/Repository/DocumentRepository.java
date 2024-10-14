package d2a.sn.document.Repository;

import d2a.sn.document.Entite.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DocumentRepository extends JpaRepository<Document,Long> {
    Optional<Document> findById(Long id);
    List<Document> findByIsActiveTrue();
}

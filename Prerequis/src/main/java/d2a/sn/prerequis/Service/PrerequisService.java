package d2a.sn.prerequis.Service;

import d2a.sn.prerequis.Entité.Prerequis;
import d2a.sn.prerequis.Repository.PrerequisRepository;
import org.springframework.stereotype.Service;

@Service
public class PrerequisService {
        private PrerequisRepository prerequisRepository;

        public PrerequisService(PrerequisRepository prerequisRepository) {
            this.prerequisRepository = prerequisRepository;
        }

        public void addDocumentToPrerequis(Long prerequisId, Long documentId) {
            // Récupérons le prérequis
            Prerequis prerequis = prerequisRepository.findById(prerequisId)
                    .orElseThrow(() -> new RuntimeException("Prerequis not found"));
            prerequis.getIdDocument().add(documentId);
            // Sauvegarde du prerequis
            prerequisRepository.save(prerequis);
        }


}

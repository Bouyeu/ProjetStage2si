package d2a.sn.document.Service;


import d2a.sn.document.Client.UserClient;
import d2a.sn.document.Entite.Document;
import d2a.sn.document.Entite.Prerequis;
import d2a.sn.document.Entite.Status;
import d2a.sn.document.Exception.RessourceNotFound;
import d2a.sn.document.Model.User;
import d2a.sn.document.Repository.DocumentRepository;
import d2a.sn.document.Repository.PrerequisRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
public class DocumentService {
    private DocumentRepository documentRepository;
    private UserClient userClient;
    private MailService mailService;
    private MinioService minioService;
    private PrerequisRepository prerequisRepository;
    public DocumentService(DocumentRepository documentRepository, UserClient userClient,  MailService mailService, MinioService minioService, PrerequisRepository prerequisRepository) {
        this.documentRepository = documentRepository;
        this.userClient = userClient;

        this.mailService = mailService;
        this.minioService = minioService;
        this.prerequisRepository = prerequisRepository;
    }
    @Transactional
    public void UpdateStatusDocument(){
     List<Document> documents = documentRepository.findAll();
     for (Document document : documents) {
         LocalDate currentDate = LocalDate.now();
         if (document.getDateExpiration().isBefore(currentDate)) {
             document.setStatus(Status.expire);
         }

     }
        documentRepository.saveAll(documents);
    }

    public Document findDocumentById(Long documentId) {
        return documentRepository.findById(documentId).orElse(null);}
    public  Document lierDocumentToUser( Long userId,Long documentId)throws RessourceNotFound {
        User user = userClient.findUserById(userId);
        String email=user.getEmail();
        String nom= user.getNom_user();
        System.out.println(email);
        if (user == null) {
            throw new RessourceNotFound("User not found");
        }
        Document document = documentRepository.findById(documentId).orElseThrow(() -> new RessourceNotFound("Document not found"));
        document.setUserId(userId);
        String objet="Rappelle";
        String contenu="Votre" + document.getNom_doc()+ "à expiré";;
        String contenu15="Votre " +document.getNom_doc()+ "expire dans 15jours";
        String contenu10="Votre " +document.getNom_doc()+ "expire dans 10jours";
        String contenu5="Votre" +document.getNom_doc()+ "expire dans 5jours";
        mailService.sendEmail(email, nom, objet, contenu15);
        if(document.getDateExpiration().isEqual(LocalDate.now().plusDays(5))){
            mailService.sendEmail(email, nom, objet, contenu15);
        }
        if(document.getDateExpiration().isEqual(LocalDate.now().plusDays(10))){
            mailService.sendEmail(email, nom, objet, contenu10);
        }
        if(document.getDateExpiration().isEqual(LocalDate.now().plusDays(5))){
            mailService.sendEmail(email, nom, objet, contenu5);
        }
        if(document.getDateExpiration().isEqual(LocalDate.now())){
            mailService.sendEmail(email, nom, objet, contenu);
        }
       return documentRepository.save(document);
    }
    @Scheduled(cron = "0 0 0 * * *")
    public void updateDocumentStatus(){
        List<Document> documents = documentRepository.findAll();
        for (Document document : documents) {
            LocalDate date = LocalDate.now();
            if (document.getDateExpiration().isBefore(date)){
                document.setStatus(Status.expire);
                documentRepository.save(document);
            }
        }
    }

public List<Document> allDocumentActive(){
        return documentRepository.findByIsActiveTrue();
}
public ResponseEntity<String> masquerDocument(Long idDocument) throws RessourceNotFound {
        Document document= documentRepository.findById(idDocument).orElseThrow(()->new RessourceNotFound("document n'est pas trouve"));
        document.setActive(false);
        documentRepository.save(document);
        return ResponseEntity.ok().body("suppression réussie");
}
        public void assignDocumentToPrerequis(Long documentId, Long prerequisId) throws RessourceNotFound {
            Document document = documentRepository.findById(documentId)
                    .orElseThrow(() -> new RessourceNotFound("Document not found"));
            Prerequis prerequis = prerequisRepository.findById(prerequisId)
                    .orElseThrow(() -> new RessourceNotFound("Prerequis not found"));
            document.getPrerequis().add(prerequis);
            prerequis.getDocuments().add(document);
            documentRepository.save(document);
            prerequisRepository.save(prerequis);
        }

public List<Prerequis> getPrerequiByDocumentId(Long documentId) throws RessourceNotFound {
        Document document=documentRepository.findById(documentId).orElseThrow(()->new RessourceNotFound("Document not found"));
        return document.getPrerequis();
}
}



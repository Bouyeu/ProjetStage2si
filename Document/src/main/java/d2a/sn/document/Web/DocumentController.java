package d2a.sn.document.Web;

import d2a.sn.document.Client.UserClient;
import d2a.sn.document.Entite.Document;
import d2a.sn.document.Entite.DocumentWithUser;
import d2a.sn.document.Entite.Prerequis;
import d2a.sn.document.Entite.Status;
import d2a.sn.document.Exception.RessourceNotFound;
import d2a.sn.document.Model.User;
import d2a.sn.document.Repository.DocumentRepository;
import d2a.sn.document.Service.DocumentService;
import d2a.sn.document.Service.MinioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class DocumentController {
    private DocumentRepository documentRepository;
    private DocumentService documentService;
    private final UserClient userClient;
    private MinioService minioService;

    public DocumentController (DocumentRepository documentRepository, DocumentService documentService, UserClient userClient, MinioService minioService) {
        this.documentRepository = documentRepository;
        this.documentService = documentService;
        this.userClient = userClient;
        this.minioService = minioService;

    }
    @PostMapping("/save")
    public ResponseEntity<Document> saveDocument(
            @RequestParam("file") MultipartFile file,
            @RequestParam("nom_doc") String nomDoc,
            @RequestParam("date0btention") LocalDate date0btention) {

        // Upload le fichier dans MinIO et récupérer l'URL
        String fileName = file.getOriginalFilename();
        String fileUrl;
        try {
            fileUrl = minioService.uploadFile(fileName,file.getInputStream(), file.getContentType());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // Gérer l'erreur
        }

        // Créer et sauvegarder le Document
        Document document = new Document();
        document.setNom_doc(nomDoc);
        document.setUrl(fileUrl); // Enregistrer l'URL du fichier
        document.setDate0btention(date0btention);
        document.setDateExpiration(date0btention.plusDays(10));
        document.setStatus(Status.valide);
        document.setActive(true);
        // Vérifier le statut d'expiration
        if (document.getDateExpiration().isBefore(LocalDate.now())) {
            document.setStatus(Status.expire);
        }

        Document savedDocument = documentRepository.save(document);
        return ResponseEntity.ok(savedDocument);
    }
    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }
    @GetMapping("/documents")
    public List<Document> getAllDocument(){
        return documentService.allDocumentActive();
    }
   @GetMapping("/documents/{id}")
   public ResponseEntity<DocumentWithUser> getDocumentWithUser(@PathVariable Long id) throws  Exception{
       Document document = documentRepository.findById(id).orElseThrow(() -> new RessourceNotFound("Document not found"));
       User user = userClient.findUserById(document.getUserId());
       DocumentWithUser response = new DocumentWithUser();
       response.setDocument(document);
       response.setUser(user);

       return ResponseEntity.ok(response);
   }

    @PutMapping("/modifier/{id}")
    public ResponseEntity<Document> updateDocument(@RequestBody Document documentDetail, @PathVariable(value = "id") Long id) throws RessourceNotFound {
        Document document = documentRepository.findById(id).orElseThrow(() -> new RessourceNotFound("document not found on :: " + id));
        document.setDate0btention(documentDetail.getDate0btention());
        document.setNom_doc(documentDetail.getNom_doc());
        document.setDateExpiration(document.getDate0btention().plusDays(3));
        final Document updatedDocument = documentRepository.save(document);
        return ResponseEntity.ok(updatedDocument);

    }
    @PostMapping("/{documentId}/assign/{userId}")
    public ResponseEntity<Document> assignAdocumentToUser(@PathVariable Long userId, @PathVariable Long documentId) throws RessourceNotFound {

        Document document = documentService.lierDocumentToUser(userId, documentId);

        return ResponseEntity.ok(document);

    }
@PostMapping("/supprimer/{id}")
    public ResponseEntity<String> deleteDocument(@PathVariable (value="id") Long idDocument) throws RessourceNotFound {
        documentService.masquerDocument(idDocument);
        return ResponseEntity.ok().body("suprpression réussi");
}

    @PostMapping("/{documentId}/assigne/{prerequisId}")
    public void assignDocumentToPrerequis(@PathVariable Long documentId, @PathVariable Long prerequisId) throws RessourceNotFound {
        documentService.assignDocumentToPrerequis(documentId, prerequisId);
    }
    @GetMapping("/voirPrerequis/documentId")
    public ResponseEntity<List<Prerequis>> getPrerequis(@PathVariable (value="documentId") Long documentId) throws RessourceNotFound {
        List<Prerequis> prerequis =documentService.getPrerequiByDocumentId(documentId);
        return ResponseEntity.ok(prerequis);
    }

    }


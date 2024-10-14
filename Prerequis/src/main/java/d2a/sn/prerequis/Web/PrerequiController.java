package d2a.sn.prerequis.Web;

import d2a.sn.prerequis.Entité.Prerequis;
import d2a.sn.prerequis.Exception.RessourceNotFound;
import d2a.sn.prerequis.Repository.PrerequisRepository;
import d2a.sn.prerequis.Service.PrerequisService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PrerequiController {
    private PrerequisRepository prerequisRepository;
    private PrerequisService prerequisService;
    public PrerequiController(PrerequisRepository prerequisRepository, PrerequisService prerequisService) {
        this.prerequisRepository = prerequisRepository;
        this.prerequisService = prerequisService;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }
    @PostMapping("/nouvelle")
    public ResponseEntity<Prerequis>savePrerequis( @RequestBody Prerequis prerequis) {
       Prerequis savePrerequis = prerequisRepository.save(prerequis);
       return ResponseEntity.ok(savePrerequis);
    }
    @GetMapping("/prerequis")
    public List<Prerequis> getPrerequis(){
        return prerequisRepository.findAll();
    }
    @GetMapping("/prerequis/{id}")
    public ResponseEntity<Prerequis> getPrerequisById(@PathVariable(value = "id") Long prerequisId)
            throws RessourceNotFound {
        Prerequis prerequis =
                prerequisRepository
                        .findById(prerequisId)
                        .orElseThrow(() -> new RessourceNotFound("prerequis not found on :: " + prerequisId));
        return ResponseEntity.ok().body(prerequis);
    }
    @PostMapping("/assigner/{prerequisId}/{idDocument}")
    public ResponseEntity<String> addDocumentToPrerequis(
            @PathVariable Long prerequisId,Long idDocument) {
        prerequisService.addDocumentToPrerequis(prerequisId, idDocument);
        return ResponseEntity.ok("Document assigned to prerequisite successfully.");
    }

}

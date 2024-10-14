package d2a.sn.document.Web;

import d2a.sn.document.Entite.Prerequis;
import d2a.sn.document.Exception.RessourceNotFound;
import d2a.sn.document.Repository.PrerequisRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prerequis")
public class PrerequiController {
    private PrerequisRepository prerequisRepository;
    public PrerequiController(PrerequisRepository prerequisRepository) {
        this.prerequisRepository = prerequisRepository;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }
    @PostMapping("/nouvelle")
    public ResponseEntity<Prerequis>savePrerequis(@RequestBody Prerequis prerequis) {
        Prerequis savePrerequis = prerequisRepository.save(prerequis);
        return ResponseEntity.ok(savePrerequis);
    }
    @GetMapping("/Allprerequis")
   public List<Prerequis> getAllPrerequis(){
        return prerequisRepository.findAll();
    }

@GetMapping("/prerequis/{prerequisId}")
    public ResponseEntity<Prerequis> getPrerequis(@PathVariable (value = "prerequisId") Long prerequisId) throws RessourceNotFound {
        Prerequis prerequis = prerequisRepository.findById(prerequisId).orElseThrow(()->new RessourceNotFound("non trouver"));
        return ResponseEntity.ok(prerequis);
}

}

package d2a.sn.prerequis.Modele;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDate;
import java.util.List;

public class Document {
    private Long id;
    private String nom_doc;
    private String url;
    private LocalDate date0btention;
    private LocalDate dateExpiration;
    private Status status;
    private  Long userId;
    private boolean isActive ;
    private List<Long> idPrerequis;
}

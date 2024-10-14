package d2a.sn.document.Entite;

import com.fasterxml.jackson.annotation.JsonIgnore;
import d2a.sn.document.Model.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public class Document {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String nom_doc;
        private String url;
        private LocalDate date0btention;
        private LocalDate dateExpiration;
        @Enumerated(EnumType.STRING)
        private Status status;
        private  Long userId;
        @JsonIgnore
        private boolean isActive ;
    @ManyToMany
    @JoinTable(name = "Document_Prerequis",
            joinColumns = @JoinColumn(name ="DocumentId"),
            inverseJoinColumns = @JoinColumn(name="PrerequisID")
    )

    private List<Prerequis>prerequis;

}


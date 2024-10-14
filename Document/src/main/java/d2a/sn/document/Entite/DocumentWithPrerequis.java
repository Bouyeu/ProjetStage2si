package d2a.sn.document.Entite;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentWithPrerequis {
    private Document document;
    private Prerequis prerequis;
}

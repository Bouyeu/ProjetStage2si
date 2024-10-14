package d2a.sn.document.Entite;

import d2a.sn.document.Model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentWithUser {
    private Document document;
    private User user;
}

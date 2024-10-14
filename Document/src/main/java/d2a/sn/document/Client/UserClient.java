package d2a.sn.document.Client;

import d2a.sn.document.Model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name ="USER" ,url = "http://localhost:8082/api")
public interface UserClient {
  @GetMapping("/users/{id}")
  User findUserById(@PathVariable Long id);
  @GetMapping("/users/{userId}/email") // L'endpoint qui renvoie l'email d'un utilisateur par ID
  String getUserEmail(@PathVariable("userId") Long userId);

}

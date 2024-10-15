package d2a.sn.user.Web;

import d2a.sn.user.Client.UserClient;
import d2a.sn.user.DTO.UserDto;
import d2a.sn.user.Entite.User;
import d2a.sn.user.Exception.RessourceNotFound;
import d2a.sn.user.Repository.UserRepository;
import d2a.sn.user.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserCotroller {
    private UserRepository userRepository;
    private UserService  userService;
    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;

        this.userService = userService;
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @PostMapping("/save")
   /* public User saveUser(@RequestBody User user) {
        user.setPassword();
        return userRepository.save(user);
    }*/
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        /*User user = new User();
        user.setNom_user(userDto.getNom_user());
        user.setPrenom_user(userDto.getPrenom_user());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setTelephone(userDto.getTelephone());
*/
        user.setActive(true);
        User savedUser = userService.registerUser(user); // Utiliser le service pour gérer le hachage
        return ResponseEntity.ok(savedUser);
    }
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsersActive();
    }
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUsersById(@PathVariable(value = "id") Long userId)
            throws RessourceNotFound {
        User user =
                userRepository
                        .findById(userId)
                        .orElseThrow(() -> new RessourceNotFound("User not found on :: " + userId));
        return ResponseEntity.ok().body(user);
    }

    @PutMapping("/modifier/{id}")

    public ResponseEntity<User> updateUser(@RequestBody User userDetail, @PathVariable(value = "id") Long id) throws RessourceNotFound {
        User user = userRepository.findById(id).orElseThrow(() -> new RessourceNotFound("User not found on :: " + id));
        user.setNom_user(userDetail.getNom_user());
        user.setPrenom_user(userDetail.getPrenom_user());
        user.setEmail(userDetail.getEmail());
        user.setPassword(userDetail.getPassword());
        final User updatedUser = userRepository.save(user);
        return ResponseEntity.ok(updatedUser);

    }
@PostMapping("/supprimer/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable(value = "id") Long usrId) throws RessourceNotFound {
      userService.masquerUser(usrId);
      return ResponseEntity.ok().body("suppression reussi");
    }
    }
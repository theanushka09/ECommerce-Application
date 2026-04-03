package ecom.app.Ecommerce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;  //creating ref of UserService

    @GetMapping("/api/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.fetchAllUsers());
    }

    @PostMapping("/api/users")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.addUser(user));
    }

    @GetMapping("/api/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return userService.fetchUser(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

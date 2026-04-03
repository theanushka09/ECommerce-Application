package ecom.app.Ecommerce;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service //mark it as service that contains business logic
public class UserService {

    private List<User> users = new ArrayList<User>();
    private Long nextId = 1L;

    public List<User> fetchAllUsers() {
        return users;
    }

    public String addUser(User user) {
        user.setId(nextId++);
        users.add(user);
        return "User added successfully";
    }

    public Optional<User> fetchUser(Long id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }

    public boolean updateUser(Long id, User user) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .map(existingUser ->{
                    existingUser.setFirstName(user.getFirstName());
                    existingUser.setLastName(user.getLastName());
                    return true;
                }).orElse(false);
    }
}

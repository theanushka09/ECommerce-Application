package ecom.app.Ecommerce;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
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

    public boolean updateUser(Long id, User updatedUser) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .map(user ->{
                    user.setFirstName(updatedUser.getFirstName());
                    user.setLastName(updatedUser.getLastName());
                    return true;
                }).orElse(false);
    }
}

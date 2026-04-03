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

    public User fetchUser(Long id) {
        for(User user : users) {
            if(user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }
}

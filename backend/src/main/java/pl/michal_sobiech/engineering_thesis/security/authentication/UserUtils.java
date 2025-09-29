package pl.michal_sobiech.engineering_thesis.security.authentication;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public class UserUtils {

    public static User createUser(String username, String passwordHash) {
        UserDetails userDetails = User.withUsername(username).password(passwordHash).build();
        if (userDetails instanceof User user) {
            return user;
        } else {
            throw new IllegalStateException("Expected UserDetails to be an instance of User");
        }
    }

}

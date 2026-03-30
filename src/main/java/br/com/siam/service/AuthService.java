package br.com.siam.service;

import br.com.siam.dao.UserDAO;
import br.com.siam.model.User;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;

public class AuthService {

    private final UserDAO userDAO;

    public AuthService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public Optional<User> authenticate(String login, String password) {
        validateLoginInput(login, password);

        Optional<User> userOptional = userDAO.findByLogin(login);

        if (userOptional.isEmpty()) {
            return Optional.empty();
        }

        User user = userOptional.get();

        boolean passwordMatches = BCrypt.checkpw(password, user.getPasswordHash());

        if (!passwordMatches) {
            return Optional.empty();
        }

        return Optional.of(user);
    }

    private void validateLoginInput(String login, String password) {
        if (login == null || login.isBlank()) {
            throw new IllegalArgumentException("Login cannot be empty.");
        }

        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password cannot be empty.");
        }
    }
}

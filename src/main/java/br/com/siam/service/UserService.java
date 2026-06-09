package br.com.siam.service;

import org.mindrot.jbcrypt.BCrypt;
import br.com.siam.dao.UserDAO;
import br.com.siam.dao.impl.UserDAOImpl;
import br.com.siam.model.User;

import java.util.List;

public class UserService {

    private final UserDAO UserDAO;

    public UserService() {
        this.UserDAO = new UserDAOImpl();
    }
    public List<User> getAllUsers() {

        return UserDAO.findAll();
    }

    private void validateUser(User user) {

        if (user.getName() == null ||
                user.getName().isBlank()) {

            throw new IllegalArgumentException(
                    "Nome é obrigatório."
            );
        }

        if (user.getLogin() == null ||
                user.getLogin().isBlank()) {

            throw new IllegalArgumentException(
                    "Login é obrigatório."
            );
        }

        if (user.getRegistration() == null ||
                user.getRegistration().isBlank()) {

            throw new IllegalArgumentException(
                    "Matrícula é obrigatória."
            );
        }

        if (user.getPasswordHash() == null ||
                user.getPasswordHash().isBlank()) {

            throw new IllegalArgumentException(
                    "Senha é obrigatória."
            );
        }

        if (UserDAO.findByEmailOrRegistration(
                user.getLogin()
        ).isPresent()) {

            throw new IllegalArgumentException(
                    "Já existe usuário com esse login."
            );
        }
    }

    public void createUser(User user) {

        validateUser(user);

        String encryptedPassword =
                BCrypt.hashpw(
                        user.getPasswordHash(),
                        BCrypt.gensalt()
                );

        user.setPasswordHash(
                encryptedPassword
        );

        user.setActive(true);

        UserDAO.save(user);
    }

    public void deactivateUser(Integer userId) {

        UserDAO.deactivate(userId);
    }

    public void reactivateUser(Integer userId) {
        UserDAO.reactivate(userId);
    }

    public void updateProfile(
            User autheticatedUser,
            String name,
            String currentPassword,
            String newPassword,
            String confirmPassword
    ) {
        if (name.isBlank()) {
            throw new IllegalArgumentException(
                    "Nome é obrigatório."
            );
        }

        if (newPassword.isBlank()) {
            UserDAO.updateProfile(
                    autheticatedUser.getId(),
                    name,
                    autheticatedUser.getPasswordHash()
            );

            return;
        }

        if (!BCrypt.checkpw(
                currentPassword,
                autheticatedUser.getPasswordHash()
        )) {
            throw new IllegalArgumentException("Senha atual incorreta.");
        }

        if (!newPassword.equals(confirmPassword)) {
            throw new IllegalArgumentException("COnfirmação de senha inválida.");
        }

        String newHash = BCrypt.hashpw(
                newPassword,
                BCrypt.gensalt()
        );

        UserDAO.updateProfile(
                autheticatedUser.getId(),
                name,
                newHash
        );
    }
}

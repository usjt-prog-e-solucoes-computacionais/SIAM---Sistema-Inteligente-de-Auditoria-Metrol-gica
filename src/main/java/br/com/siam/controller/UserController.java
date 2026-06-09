package br.com.siam.controller;

import br.com.siam.model.User;
import br.com.siam.service.UserService;

import java.util.List;

public class UserController {

    private final UserService userService;

    public UserController() {

        this.userService =
                new UserService();
    }

    public List<User> getAllUsers() {

        return userService.getAllUsers();
    }

    public void createUser(User user) {

        userService.createUser(user);
    }

    public void deactivateUser(Integer userId) {

        userService.deactivateUser(userId);
    }

    public void updateProfile(
            User authenticatedUser,
            String name,
            String currentPassword,
            String newPassword,
            String confirmPassword
    ) {
        userService.updateProfile(
                authenticatedUser, name, currentPassword, newPassword, confirmPassword);
    }

    public void reactivateUser(Integer userId) {

        userService.reactivateUser(userId);
    }
}
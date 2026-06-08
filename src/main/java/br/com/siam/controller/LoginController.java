package br.com.siam.controller;

import br.com.siam.model.User;
import br.com.siam.service.AuthService;

import java.util.Optional;

public class LoginController {

    private final AuthService authService;

    public LoginController() {
        this.authService = new AuthService();
    }

    public Optional<User> login(String identifier, String password) {
        return authService.authenticate(identifier, password);
    }
}

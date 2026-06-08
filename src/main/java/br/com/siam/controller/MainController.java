package br.com.siam.controller;

import br.com.siam.model.User;
import br.com.siam.view.MainView;

public class MainController {

    private final MainView mainView;
    private final User authenticatedUser;

    public MainController(MainView mainView, User authenticatedUser) {
        this.mainView = mainView;
        this.authenticatedUser = authenticatedUser;
        configureView();
    }

    private void configureView() {
    }

    private boolean isAdmin() {
        return "ADMIN".equalsIgnoreCase(authenticatedUser.getUserType());
    }

    public User getAuthenticatedUser() {
        return authenticatedUser;
    }
}

package br.com.siam.controller;

import br.com.siam.model.User;

public class MainController {

    private final MainView mainView;
    private final User authenticatedUser;

    public MainController(MainView mainView, User authenticatedUser) {
        this.mainView = mainView;
        this.authenticatedUser = authenticatedUser;
        configureView();
    }

    private void configureView() {
        mainView.displayAutheticatedUser(authenticatedUser);
        mainView.setAdminPanel(isAdmin());
    }

    private boolean isAdmin() {
        return "ADMIN".equalsIgnoreCase(authenticatedUser.getUserType());
    }

    public User getAuthenticatedUser() {
        return authenticatedUser;
    }
}

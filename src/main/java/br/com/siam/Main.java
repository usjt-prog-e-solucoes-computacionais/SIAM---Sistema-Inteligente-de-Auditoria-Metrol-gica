package br.com.siam;

import br.com.siam.view.LoginView;
import org.mindrot.jbcrypt.BCrypt;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginView loginView = new LoginView();
            loginView.setVisible(true);
        });
    }
}
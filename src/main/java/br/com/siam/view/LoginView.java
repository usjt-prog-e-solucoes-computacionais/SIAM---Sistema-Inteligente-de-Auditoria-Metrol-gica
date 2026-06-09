package br.com.siam.view;

import br.com.siam.controller.LoginController;
import br.com.siam.model.User;
import br.com.siam.util.IconUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Optional;

public class LoginView extends JFrame {

    private JLabel logoLabel;
    private JLabel systemTitleLabel;
    private JLabel identifierLabel;
    private JLabel passwordLabel;
    private JLabel footerLabel;

    private JTextField identifierField;
    private JPasswordField passwordField;

    private JCheckBox termsCheckBox;

    private JButton loginButton;

    private final LoginController loginController;

    public LoginView() {
        this.loginController = new LoginController();

        initializeComponents();
        configureWindow();
        buildLayout();
        registerEvents();
    }

    private void initializeComponents() {
        logoLabel = new JLabel(
                IconUtils.getScaledLogo(
                        96,
                        96
                )
        );

        logoLabel.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        systemTitleLabel = new JLabel("SIAM - Sistema Inteligente de Auditoria", SwingConstants.CENTER);
        systemTitleLabel.setFont(new Font("Arial", Font.BOLD, 18));

        identifierLabel = new JLabel("E-mail ou Matrícula:");
        passwordLabel = new JLabel("Senha:");
        footerLabel = new JLabel("(c) 2026 Danilo. Todos os direitos reservados.", SwingConstants.CENTER);

        identifierField = new JTextField(25);
        passwordField = new JPasswordField(25);

        termsCheckBox = new JCheckBox("Li e aceito os Termos de Licença de Uso do SIAM");

        loginButton = new JButton("ENTRAR NO SISTEMA");
        loginButton.setPreferredSize(new Dimension(220, 36));
    }

    private void configureWindow() {
        setTitle("SIAM - Sistema Inteligente de Auditoria Metrológica");

        setIconImage(
                IconUtils.getApplicationIcon()
        );

        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void buildLayout() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        contentPanel.add(buildHeaderPanel());
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(buildFormPanel());
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(buildTermsPanel());
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(buildActionPanel());
        contentPanel.add(Box.createVerticalGlue());
        contentPanel.add(buildFooterPanel());

        mainPanel.add(contentPanel, BorderLayout.CENTER);
        setContentPane(mainPanel);
    }

    private JPanel buildHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));

        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        systemTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(logoLabel);
        headerPanel.add(Box.createVerticalStrut(10));
        headerPanel.add(systemTitleLabel);

        return headerPanel;
    }

    private JPanel buildFormPanel() {
        JPanel formWrapperPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                new EmptyBorder(20, 20, 20, 20)
        ));

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(10, 10, 10, 10);
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        formPanel.add(identifierLabel, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        formPanel.add(identifierField, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        formPanel.add(passwordLabel, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        formPanel.add(passwordField, gridBagConstraints);

        formWrapperPanel.add(formPanel);
        return formWrapperPanel;
    }

    private JPanel buildTermsPanel() {
        JPanel termsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        termsPanel.add(termsCheckBox);
        return termsPanel;
    }

    private JPanel buildActionPanel() {
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        actionPanel.add(loginButton);
        return actionPanel;
    }

    private JPanel buildFooterPanel() {
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footerPanel.add(footerLabel);
        return footerPanel;
    }

    private void registerEvents() {
        loginButton.addActionListener(event -> handleLogin());
    }

    private void handleLogin() {
        String identifier = identifierField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (identifier.isEmpty() || password.isEmpty()) {
            showWarningMessage("Preencha o e-mail ou matrícula e a senha.");
            return;
        }

        if (!termsCheckBox.isSelected()) {
            showWarningMessage("Você deve aceitar os Termos de Licença de Uso.");
            return;
        }

        try {
            Optional<User> authenticatedUser = loginController.login(identifier, password);

            if (authenticatedUser.isPresent()) {
                User user = authenticatedUser.get();

                MainView mainView = new MainView(user);
                mainView.setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Credenciais inválidas.",
                        "Erro de autenticação",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        } catch (RuntimeException exception) {
            exception.printStackTrace();
            JOptionPane.showMessageDialog(
                    this,
                    "Erro interno ao autenticar usuário.",
                    "Erro interno",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void showWarningMessage(String message) {
        JOptionPane.showMessageDialog(
                this,
                message,
                "Atenção",
                JOptionPane.WARNING_MESSAGE
        );
    }
}

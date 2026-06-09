package br.com.siam.view;

import br.com.siam.controller.UserController;
import br.com.siam.model.User;
import br.com.siam.util.IconUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ProfileView extends JFrame {

    private final User authenticatedUser;

    private final UserController userController;

    private JLabel titleLabel;

    private JLabel nameLabel;
    private JLabel loginLabel;
    private JLabel registrationLabel;
    private JLabel currentPasswordLabel;
    private JLabel newPasswordLabel;
    private JLabel confirmPasswordLabel;

    private JTextField nameField;
    private JTextField loginField;
    private JTextField registrationField;

    private JPasswordField currentPasswordField;
    private JPasswordField newPasswordField;
    private JPasswordField confirmPasswordField;

    private JButton saveButton;
    private JButton cancelButton;

    public ProfileView(User authenticatedUser) {

        this.authenticatedUser = authenticatedUser;

        this.userController = new UserController();

        initializeComponents();

        configureWindow();

        buildLayout();

        loadUserData();

        registerEvents();
    }

    private void initializeComponents() {

        titleLabel =
                new JLabel(
                        "Meu Perfil",
                        SwingConstants.CENTER
                );

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        22
                )
        );

        nameLabel =
                new JLabel("Nome:");

        loginLabel =
                new JLabel("Login:");

        registrationLabel =
                new JLabel("Matrícula:");

        currentPasswordLabel =
                new JLabel("Senha Atual:");

        newPasswordLabel =
                new JLabel("Nova Senha:");

        confirmPasswordLabel =
                new JLabel("Confirmar Nova Senha:");

        nameField =
                new JTextField(25);

        loginField =
                new JTextField(25);

        registrationField =
                new JTextField(25);

        currentPasswordField =
                new JPasswordField(25);

        newPasswordField =
                new JPasswordField(25);

        confirmPasswordField =
                new JPasswordField(25);

        saveButton =
                new JButton("Salvar Alterações");

        cancelButton =
                new JButton("Cancelar");

        loginField.setEditable(false);

        registrationField.setEditable(false);
    }

    private void configureWindow() {

        setTitle("SIAM - Meu Perfil");

        setIconImage(
                IconUtils.getApplicationIcon()
        );

        setSize(700, 500);

        setLocationRelativeTo(null);

        setResizable(false);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void buildLayout() {

        JPanel mainPanel =
                new JPanel(
                        new BorderLayout()
                );

        mainPanel.setBorder(
                new EmptyBorder(
                        20,
                        20,
                        20,
                        20
                )
        );

        mainPanel.add(
                buildHeaderPanel(),
                BorderLayout.NORTH
        );

        mainPanel.add(
                buildFormPanel(),
                BorderLayout.CENTER
        );

        mainPanel.add(
                buildActionPanel(),
                BorderLayout.SOUTH
        );

        setContentPane(mainPanel);
    }

    private JPanel buildHeaderPanel() {

        JPanel headerPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER
                        )
                );

        headerPanel.add(titleLabel);

        return headerPanel;
    }

    private JPanel buildFormPanel() {

        JPanel formPanel =
                new JPanel(
                        new GridBagLayout()
                );

        formPanel.setBorder(
                BorderFactory.createTitledBorder(
                        "Informações do Usuário"
                )
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(
                        10,
                        10,
                        10,
                        10
                );

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        gbc.anchor =
                GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(nameLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(loginLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(loginField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(registrationLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(registrationField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(currentPasswordLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(currentPasswordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(newPasswordLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(newPasswordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(confirmPasswordLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(confirmPasswordField, gbc);

        return formPanel;
    }

    private JPanel buildActionPanel() {

        JPanel actionPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER
                        )
                );

        actionPanel.add(saveButton);

        actionPanel.add(cancelButton);

        return actionPanel;
    }

    private void loadUserData() {

        nameField.setText(
                authenticatedUser.getName()
        );

        loginField.setText(
                authenticatedUser.getLogin()
        );

        registrationField.setText(
                authenticatedUser.getRegistration()
        );
    }

    private void registerEvents() {

        saveButton.addActionListener(
                event -> saveProfile()
        );

        cancelButton.addActionListener(
                event -> dispose()
        );
    }

    private void saveProfile() {

        try {

            String name =
                    nameField.getText().trim();

            String currentPassword =
                    new String(
                            currentPasswordField.getPassword()
                    );

            String newPassword =
                    new String(
                            newPasswordField.getPassword()
                    );

            String confirmPassword =
                    new String(
                            confirmPasswordField.getPassword()
                    );

            userController.updateProfile(
                    authenticatedUser,
                    name,
                    currentPassword,
                    newPassword,
                    confirmPassword
            );

            authenticatedUser.setName(name);

            JOptionPane.showMessageDialog(
                    this,
                    "Perfil atualizado com sucesso.",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE
            );

            dispose();

        } catch (Exception exception) {

            JOptionPane.showMessageDialog(
                    this,
                    exception.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
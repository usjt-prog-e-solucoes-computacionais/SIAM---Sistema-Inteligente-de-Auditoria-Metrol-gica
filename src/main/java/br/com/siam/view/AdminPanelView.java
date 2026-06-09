package br.com.siam.view;

import br.com.siam.controller.UserController;
import br.com.siam.model.User;
import br.com.siam.util.IconUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdminPanelView extends JFrame {

    private final UserController userController;

    private JTextField nameField;
    private JTextField loginField;
    private JTextField registrationField;

    private JPasswordField passwordField;

    private JComboBox<String> userTypeComboBox;

    private JButton createUserButton;
    private JButton deactivateUserButton;

    private JTable userTable;

    private DefaultTableModel userTableModel;

    private JScrollPane userTableScrollPane;

    public AdminPanelView() {

        this.userController =
                new UserController();

        initializeComponents();

        configureWindow();

        buildLayout();

        registerEvents();

        loadUsers();
    }

    private JPanel buildFormPanel() {

        JPanel formPanel = new JPanel(
                new GridBagLayout()
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.anchor =
                GridBagConstraints.WEST;

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        gbc.weightx = 1;

        // Nome

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(
                new JLabel("Nome:"),
                gbc
        );

        gbc.gridx = 1;
        formPanel.add(
                nameField,
                gbc
        );

        // Login

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(
                new JLabel("Login:"),
                gbc
        );

        gbc.gridx = 1;
        formPanel.add(
                loginField,
                gbc
        );

        // Matrícula

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(
                new JLabel("Matrícula:"),
                gbc
        );

        gbc.gridx = 1;
        formPanel.add(
                registrationField,
                gbc
        );

        // Senha

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(
                new JLabel("Senha:"),
                gbc
        );

        gbc.gridx = 1;
        formPanel.add(
                passwordField,
                gbc
        );

        // Tipo

        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(
                new JLabel("Tipo:"),
                gbc
        );

        gbc.gridx = 1;
        formPanel.add(
                userTypeComboBox,
                gbc
        );

        // Botões

        JPanel buttonsPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT
                        )
                );

        buttonsPanel.add(
                createUserButton
        );

        buttonsPanel.add(
                deactivateUserButton
        );

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;

        formPanel.add(
                buttonsPanel,
                gbc
        );

        return formPanel;
    }

    private void initializeComponents() {

        nameField =
                new JTextField(20);

        loginField =
                new JTextField(20);

        registrationField =
                new JTextField(20);

        passwordField =
                new JPasswordField(20);

        userTypeComboBox =
                new JComboBox<>(
                        new String[]{
                                "ADMIN",
                                "FISCAL"
                        }
                );

        createUserButton =
                new JButton("Cadastrar Usuário");

        deactivateUserButton =
                new JButton("Desativar Usuário");

        String[] columns = {
                "ID",
                "Nome",
                "Login",
                "Matrícula",
                "Tipo",
                "Ativo"
        };

        userTableModel =
                new DefaultTableModel(columns, 0) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column
                    ) {
                        return false;
                    }
                };

        userTable =
                new JTable(userTableModel);

        userTableScrollPane =
                new JScrollPane(userTable);
    }

    private void configureWindow() {

        setTitle("Painel Administrativo");

        setSize(1000, 700);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setIconImage(
                IconUtils.getApplicationIcon()
        );
    }

    private void buildLayout() {

        JPanel mainPanel =
                new JPanel(
                        new BorderLayout(
                                10,
                                10
                        )
                );

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        10,
                        10,
                        10
                )
        );

        mainPanel.add(
                buildFormPanel(),
                BorderLayout.NORTH
        );

        mainPanel.add(
                userTableScrollPane,
                BorderLayout.CENTER
        );

        setContentPane(
                mainPanel
        );
    }

    private void registerEvents() {

        createUserButton.addActionListener(
                event -> createUser()
        );

        deactivateUserButton.addActionListener(
                event -> deactivateUser()
        );
    }

    private void createUser() {

        try {

            User user =
                    new User();

            user.setName(
                    nameField.getText().trim()
            );

            user.setLogin(
                    loginField.getText().trim()
            );

            user.setRegistration(
                    registrationField.getText().trim()
            );

            user.setPasswordHash(
                    new String(
                            passwordField.getPassword()
                    )
            );

            user.setUserType(
                    userTypeComboBox
                            .getSelectedItem()
                            .toString()
            );

            userController.createUser(user);

            JOptionPane.showMessageDialog(
                    this,
                    "Usuário cadastrado com sucesso."
            );

            clearForm();

            loadUsers();

        } catch (Exception exception) {

            JOptionPane.showMessageDialog(
                    this,
                    exception.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void clearForm() {

        nameField.setText("");

        loginField.setText("");

        registrationField.setText("");

        passwordField.setText("");

        userTypeComboBox.setSelectedIndex(0);
    }

    private void deactivateUser() {

        int selectedRow =
                userTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Selecione um usuário."
            );

            return;
        }

        Integer userId =
                (Integer)
                        userTableModel.getValueAt(
                                selectedRow,
                                0
                        );

        userController.deactivateUser(userId);

        loadUsers();
    }

    private void loadUsers() {

        userTableModel.setRowCount(0);

        List<User> users =
                userController.getAllUsers();

        for (User user : users) {

            userTableModel.addRow(
                    new Object[]{
                            user.getId(),
                            user.getName(),
                            user.getLogin(),
                            user.getRegistration(),
                            user.getUserType(),
                            user.getActive()
                                    ? "SIM"
                                    : "NÃO"
                    }
            );
        }
    }
}
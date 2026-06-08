package br.com.siam.view;

import br.com.siam.controller.GasStationController;
import br.com.siam.model.GasStation;
import br.com.siam.model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class GasStationDetailsView extends JFrame {

    private final User authenticatedUser;

    private final GasStationController gasStationController;

    private GasStation gasStation;

    private JLabel titleLabel;

    private JLabel cnpjLabel;
    private JLabel corporateNameLabel;
    private JLabel addressLabel;

    private JTextField cnpjField;
    private JTextField corporateNameField;
    private JTextField addressField;

    private JButton saveButton;
    private JButton deactivateButton;
    private JButton closeButton;

    public GasStationDetailsView(
            Integer gasStationId,
            User authenticatedUser
    ) {

        this.authenticatedUser = authenticatedUser;

        this.gasStationController =
                new GasStationController();

        this.gasStation =
                gasStationController.getById(
                        gasStationId
                );

        initializeComponents();

        configureWindow();

        buildLayout();

        loadData();

        configurePermissions();

        registerEvents();
    }

    private void initializeComponents() {

        titleLabel =
                new JLabel(
                        "Detalhes do Posto",
                        SwingConstants.CENTER
                );

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        22
                )
        );

        cnpjLabel =
                new JLabel("CNPJ:");

        corporateNameLabel =
                new JLabel("Razão Social:");

        addressLabel =
                new JLabel("Endereço:");

        cnpjField =
                new JTextField(30);

        corporateNameField =
                new JTextField(30);

        addressField =
                new JTextField(30);

        saveButton =
                new JButton("Salvar");

        deactivateButton =
                new JButton("Desativar");

        closeButton =
                new JButton("Fechar");
    }

    private void configureWindow() {

        setTitle("SIAM - Detalhes do Posto");

        setSize(750, 350);

        setLocationRelativeTo(null);

        setResizable(false);

        setDefaultCloseOperation(
                DISPOSE_ON_CLOSE
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
                buildButtonPanel(),
                BorderLayout.SOUTH
        );

        setContentPane(mainPanel);
    }

    private JPanel buildHeaderPanel() {

        JPanel panel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER
                        )
                );

        panel.add(titleLabel);

        return panel;
    }

    private JPanel buildFormPanel() {

        JPanel panel =
                new JPanel(
                        new GridBagLayout()
                );

        panel.setBorder(
                BorderFactory.createTitledBorder(
                        "Informações do Posto"
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

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(
                cnpjLabel,
                gbc
        );

        gbc.gridx = 1;
        panel.add(
                cnpjField,
                gbc
        );

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(
                corporateNameLabel,
                gbc
        );

        gbc.gridx = 1;
        panel.add(
                corporateNameField,
                gbc
        );

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(
                addressLabel,
                gbc
        );

        gbc.gridx = 1;
        panel.add(
                addressField,
                gbc
        );

        return panel;
    }

    private JPanel buildButtonPanel() {

        JPanel panel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER
                        )
                );

        panel.add(saveButton);

        panel.add(deactivateButton);

        panel.add(closeButton);

        return panel;
    }

    private void loadData() {

        cnpjField.setText(
                gasStation.getCnpj()
        );

        corporateNameField.setText(
                gasStation.getCorporateName()
        );

        addressField.setText(
                gasStation.getAddress()
        );
    }

    private void configurePermissions() {

        boolean isAdmin =
                authenticatedUser
                        .getUserType()
                        .equalsIgnoreCase(
                                "ADMIN"
                        );

        if (!isAdmin) {

            cnpjField.setEditable(false);

            corporateNameField.setEditable(false);

            addressField.setEditable(false);

            saveButton.setVisible(false);

            deactivateButton.setVisible(false);
        }
    }

    private void registerEvents() {

        saveButton.addActionListener(
                event -> saveGasStation()
        );

        deactivateButton.addActionListener(
                event -> deactivateGasStation()
        );

        closeButton.addActionListener(
                event -> dispose()
        );
    }

    private void saveGasStation() {

        try {

            gasStation.setCnpj(
                    cnpjField.getText().trim()
            );

            gasStation.setCorporateName(
                    corporateNameField.getText().trim()
            );

            gasStation.setAddress(
                    addressField.getText().trim()
            );

            gasStationController.update(
                    gasStation
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Posto atualizado com sucesso.",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (Exception exception) {

            JOptionPane.showMessageDialog(
                    this,
                    exception.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void deactivateGasStation() {

        int option =
                JOptionPane.showConfirmDialog(
                        this,
                        """
                        Deseja realmente desativar este posto?

                        Todas as bombas vinculadas
                        também serão desativadas.
                        """,
                        "Confirmação",
                        JOptionPane.YES_NO_OPTION
                );

        if (option != JOptionPane.YES_OPTION) {

            return;
        }

        try {

            gasStationController.deactivate(
                    gasStation.getId()
            );

            JOptionPane.showMessageDialog(
                    this,
                    """
                    Posto desativado com sucesso.

                    Todas as bombas vinculadas
                    também foram desativadas.
                    """,
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
package br.com.siam.view;

import br.com.siam.controller.GasPumpController;
import br.com.siam.model.GasPump;
import br.com.siam.model.User;
import br.com.siam.util.IconUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class GasPumpDetailsView extends JFrame {

    private final User authenticatedUser;

    private final GasPumpController gasPumpController;

    private GasPump gasPump;

    private JLabel titleLabel;

    private JLabel serialNumberLabel;
    private JLabel modelLabel;
    private JLabel gasStationLabel;

    private JTextField serialNumberField;
    private JTextField modelField;
    private JTextField gasStationField;

    private JButton saveButton;
    private JButton deactivateButton;
    private JButton reactivateButton;
    private JButton closeButton;

    public GasPumpDetailsView(
            Integer gasPumpId,
            User authenticatedUser
    ) {

        this.authenticatedUser = authenticatedUser;

        this.gasPumpController =
                new GasPumpController();

        this.gasPump =
                gasPumpController.getById(
                        gasPumpId
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
                        "Detalhes da Bomba",
                        SwingConstants.CENTER
                );

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        22
                )
        );

        serialNumberLabel =
                new JLabel("Número de Série:");

        modelLabel =
                new JLabel("Modelo:");

        gasStationLabel =
                new JLabel("Posto:");

        serialNumberField =
                new JTextField(30);

        modelField =
                new JTextField(30);

        gasStationField =
                new JTextField(30);

        gasStationField.setEditable(false);

        saveButton =
                new JButton("Salvar");

        deactivateButton =
                new JButton("Desativar");

        reactivateButton =
                new JButton("Reativar");

        closeButton =
                new JButton("Fechar");
    }

    private void configureWindow() {

        setTitle("SIAM - Detalhes da Bomba");

        setIconImage(
                IconUtils.getApplicationIcon()
        );

        setSize(700, 350);

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
                        "Informações da Bomba"
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
                serialNumberLabel,
                gbc
        );

        gbc.gridx = 1;
        panel.add(
                serialNumberField,
                gbc
        );

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(
                modelLabel,
                gbc
        );

        gbc.gridx = 1;
        panel.add(
                modelField,
                gbc
        );

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(
                gasStationLabel,
                gbc
        );

        gbc.gridx = 1;
        panel.add(
                gasStationField,
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

        panel.add(reactivateButton);

        if (gasPump.getActive()) {

            reactivateButton.setVisible(false);

        } else {

            deactivateButton.setVisible(false);
        }

        panel.add(closeButton);

        return panel;
    }

    private void loadData() {

        serialNumberField.setText(
                gasPump.getSerialNumber()
        );

        modelField.setText(
                gasPump.getModel()
        );

        if (gasPump.getGasStation() != null) {

            gasStationField.setText(
                    gasPump.getGasStation()
                            .getCorporateName()
            );
        }
    }

    private void configurePermissions() {

        boolean isAdmin =
                authenticatedUser
                        .getUserType()
                        .equalsIgnoreCase(
                                "ADMIN"
                        );

        if (!isAdmin) {

            serialNumberField.setEditable(false);

            modelField.setEditable(false);

            saveButton.setVisible(false);

            deactivateButton.setVisible(false);

            reactivateButton.setVisible(false);
        }
    }

    private void registerEvents() {

        saveButton.addActionListener(
                event -> saveGasPump()
        );

        deactivateButton.addActionListener(
                event -> deactivateGasPump()
        );

        reactivateButton.addActionListener(
                event -> reactivateGasPump()
        );

        closeButton.addActionListener(
                event -> dispose()
        );
    }

    private void reactivateGasPump() {

        int option =
                JOptionPane.showConfirmDialog(
                        this,
                        "Deseja realmente reativar esta bomba?",
                        "Confirmação",
                        JOptionPane.YES_NO_OPTION
                );

        if (option != JOptionPane.YES_OPTION) {

            return;
        }

        try {

            gasPumpController.reactivate(
                    gasPump.getId()
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Bomba reativada com sucesso.",
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

    private void saveGasPump() {

        try {

            gasPump.setSerialNumber(
                    serialNumberField
                            .getText()
                            .trim()
            );

            gasPump.setModel(
                    modelField
                            .getText()
                            .trim()
            );

            gasPumpController.update(
                    gasPump
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Bomba atualizada com sucesso.",
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

    private void deactivateGasPump() {

        int option =
                JOptionPane.showConfirmDialog(
                        this,
                        "Deseja realmente desativar esta bomba?",
                        "Confirmação",
                        JOptionPane.YES_NO_OPTION
                );

        if (option != JOptionPane.YES_OPTION) {

            return;
        }

        try {

            gasPumpController.deactivate(
                    gasPump.getId()
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Bomba desativada com sucesso.",
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
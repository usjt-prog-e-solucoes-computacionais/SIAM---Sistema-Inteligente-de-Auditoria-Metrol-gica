package br.com.siam.view;

import br.com.siam.controller.FiscalizationController;
import br.com.siam.model.Fiscalization;
import br.com.siam.model.User;
import br.com.siam.util.IconUtils;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;

public class FiscalizationDetailsView extends JFrame {

    private final User authenticatedUser;

    private final FiscalizationController fiscalizationController;

    private Fiscalization fiscalization;

    private JTextField irregularityTypeField;

    private JTextField measurementErrorField;

    private JComboBox<String> auditStatusComboBox;

    private JTextArea nextStepsTextArea;

    private JButton saveButton;

    private JButton archiveButton;
    private JButton reactivateButton;

    private JLabel fiscalizationTitleLabel;

    private JLabel inspectorLabel;
    private JLabel fiscalizationDateLabel;

    private JButton closeButton;

    public FiscalizationDetailsView(
            Integer fiscalizationId,
            User authenticatedUser
    ) {

        this.authenticatedUser =
                authenticatedUser;

        this.fiscalizationController =
                new FiscalizationController();

        this.fiscalization =
                fiscalizationController
                        .getById(
                                fiscalizationId
                        );

        initializeComponents();

        configureWindow();

        buildLayout();

        loadData();

        configurePermissions();

        registerEvents();
    }

    private void initializeComponents() {

        fiscalizationTitleLabel =
                new JLabel(
                        "",
                        SwingConstants.CENTER
                );

        fiscalizationTitleLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        22
                )
        );

        inspectorLabel =
                new JLabel();

        fiscalizationDateLabel =
                new JLabel();

        closeButton =
                new JButton("Fechar");

        irregularityTypeField =
                new JTextField(30);

        measurementErrorField =
                new JTextField(30);

        auditStatusComboBox =
                new JComboBox<>(
                        new String[]{
                                "PENDENTE",
                                "EM_ANALISE",
                                "CONCLUIDO"
                        }
                );

        nextStepsTextArea =
                new JTextArea(
                        6,
                        30
                );

        saveButton =
                new JButton("Salvar");

        archiveButton =
                new JButton("Arquivar");


        reactivateButton =
                new JButton("Reativar");
    }

    private void configureWindow() {

        setTitle(
                "Detalhes da Fiscalização"
        );

        setIconImage(
                IconUtils.getApplicationIcon()
        );

        setSize(
                800,
                600
        );

        setLocationRelativeTo(null);

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
                BorderFactory.createEmptyBorder(
                        15,
                        15,
                        15,
                        15
                )
        );

        /*
         * HEADER
         */
        JPanel headerPanel =
                new JPanel(
                        new BorderLayout()
                );

        headerPanel.add(
                fiscalizationTitleLabel,
                BorderLayout.CENTER
        );

        /*
         * DETAILS
         */
        JPanel detailsPanel =
                new JPanel(
                        new GridBagLayout()
                );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(
                        5,
                        5,
                        5,
                        5
                );

        gbc.anchor =
                GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        detailsPanel.add(
                new JLabel("Fiscal:"),
                gbc
        );

        gbc.gridx = 1;
        detailsPanel.add(
                inspectorLabel,
                gbc
        );

        gbc.gridx = 0;
        gbc.gridy++;
        detailsPanel.add(
                new JLabel("Data:"),
                gbc
        );

        gbc.gridx = 1;
        detailsPanel.add(
                fiscalizationDateLabel,
                gbc
        );

        gbc.gridx = 0;
        gbc.gridy++;
        detailsPanel.add(
                new JLabel("Status:"),
                gbc
        );

        gbc.gridx = 1;
        detailsPanel.add(
                auditStatusComboBox,
                gbc
        );

        gbc.gridx = 0;
        gbc.gridy++;
        detailsPanel.add(
                new JLabel("Tipo de Irregularidade:"),
                gbc
        );

        gbc.gridx = 1;
        detailsPanel.add(
                irregularityTypeField,
                gbc
        );

        gbc.gridx = 0;
        gbc.gridy++;
        detailsPanel.add(
                new JLabel("Erro de Medição:"),
                gbc
        );

        gbc.gridx = 1;
        detailsPanel.add(
                measurementErrorField,
                gbc
        );

        /*
         * NEXT STEPS
         */
        JPanel nextStepsPanel =
                new JPanel(
                        new BorderLayout()
                );

        nextStepsPanel.setBorder(
                BorderFactory.createTitledBorder(
                        "Próximos Passos"
                )
        );

        nextStepsPanel.add(
                new JScrollPane(
                        nextStepsTextArea
                ),
                BorderLayout.CENTER
        );

        /*
         * AI PANEL
         */
        JPanel aiPanel =
                new JPanel(
                        new BorderLayout()
                );

        aiPanel.setBorder(
                BorderFactory.createTitledBorder(
                        "Sugestões da IA"
                )
        );

        aiPanel.add(
                new JLabel(
                        "Em breve...",
                        SwingConstants.CENTER
                ),
                BorderLayout.CENTER
        );

        /*
         * CENTER
         */
        JPanel centerPanel =
                new JPanel();

        centerPanel.setLayout(
                new BoxLayout(
                        centerPanel,
                        BoxLayout.Y_AXIS
                )
        );

        centerPanel.add(detailsPanel);
        centerPanel.add(Box.createVerticalStrut(15));
        centerPanel.add(nextStepsPanel);
        centerPanel.add(Box.createVerticalStrut(15));
        centerPanel.add(aiPanel);

        /*
         * BUTTONS
         */
        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER
                        )
                );

        buttonPanel.add(saveButton);

        buttonPanel.add(archiveButton);

        buttonPanel.add(reactivateButton);

        if (fiscalization.getActive()) {

            reactivateButton.setVisible(false);

        } else {

            archiveButton.setVisible(false);
        }

        buttonPanel.add(closeButton);

        mainPanel.add(
                headerPanel,
                BorderLayout.NORTH
        );

        mainPanel.add(
                centerPanel,
                BorderLayout.CENTER
        );

        mainPanel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        setContentPane(mainPanel);
    }

    private void loadData() {

        fiscalizationTitleLabel.setText(
                "Fiscalização #"
                        + fiscalization.getId()
        );

        inspectorLabel.setText(
                fiscalization.getUser().getName()
        );

        fiscalizationDateLabel.setText(
                fiscalization
                        .getFiscalizationDate()
                        .toString()
        );

        irregularityTypeField.setText(
                fiscalization.getIrregularityType()
        );

        measurementErrorField.setText(
                String.valueOf(
                        fiscalization.getMeasurementError()
                )
        );

        auditStatusComboBox.setSelectedItem(
                fiscalization.getAuditStatus()
        );
    }

    private void configurePermissions() {

        boolean canEdit =
                authenticatedUser
                        .getUserType()
                        .equalsIgnoreCase(
                                "ADMIN"
                        )
                        ||
                        fiscalization
                                .getUser().getId()
                                .equals(
                                        authenticatedUser.getId()
                                );

        if (!canEdit) {

            irregularityTypeField
                    .setEditable(false);

            measurementErrorField
                    .setEditable(false);

            auditStatusComboBox
                    .setEnabled(false);

            saveButton
                    .setVisible(false);

            archiveButton
                    .setVisible(false);

            reactivateButton.
                    setVisible(false);
        }
    }

    private void registerEvents() {

        saveButton.addActionListener(
                event -> saveFiscalization()
        );

        archiveButton.addActionListener(
                event -> archiveFiscalization()
        );


        reactivateButton.addActionListener(
                event -> reactivateFiscalization()
        );

        closeButton.addActionListener(
                event -> dispose()
        );
    }


    private void reactivateFiscalization() {

        int option =
                JOptionPane.showConfirmDialog(
                        this,
                        "Deseja realmente reativar esta fiscalização?",
                        "Confirmação",
                        JOptionPane.YES_NO_OPTION
                );

        if (option != JOptionPane.YES_OPTION) {

            return;
        }

        try {

            fiscalizationController.reactivateFiscalization(
                    fiscalization.getId()
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Fiscalização reativada com sucesso.",
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

    private void saveFiscalization() {

        fiscalization.setIrregularityType(
                irregularityTypeField.getText()
        );

        fiscalization.setMeasurementError(
                new BigDecimal(
                        measurementErrorField.getText()
                )
        );

        fiscalization.setAuditStatus(
                auditStatusComboBox
                        .getSelectedItem()
                        .toString()
        );

        fiscalizationController
                .updateFiscalization(
                        fiscalization
                );

        JOptionPane.showMessageDialog(
                this,
                "Fiscalização atualizada."
        );
    }

    private void archiveFiscalization() {

        fiscalizationController
                .archiveFiscalization(
                        fiscalization.getId()
                );

        JOptionPane.showMessageDialog(
                this,
                "Fiscalização arquivada."
        );

        dispose();
    }
}
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

        JPanel panel =
                new JPanel(
                        new BorderLayout()
                );

        JPanel formPanel =
                new JPanel(
                        new GridLayout(
                                4,
                                2,
                                10,
                                10
                        )
                );

        formPanel.add(
                new JLabel(
                        "Irregularidade:"
                )
        );

        formPanel.add(
                irregularityTypeField
        );

        formPanel.add(
                new JLabel(
                        "Erro de Medição:"
                )
        );

        formPanel.add(
                measurementErrorField
        );

        formPanel.add(
                new JLabel(
                        "Status:"
                )
        );

        formPanel.add(
                auditStatusComboBox
        );

        panel.add(
                formPanel,
                BorderLayout.NORTH
        );

        panel.add(
                new JScrollPane(
                        nextStepsTextArea
                ),
                BorderLayout.CENTER
        );

        JPanel buttonPanel =
                new JPanel();

        buttonPanel.add(
                saveButton
        );

        buttonPanel.add(
                archiveButton
        );

        panel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        setContentPane(panel);
    }

    private void loadData() {

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
        }
    }

    private void registerEvents() {

        saveButton.addActionListener(
                event -> saveFiscalization()
        );

        archiveButton.addActionListener(
                event -> archiveFiscalization()
        );
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
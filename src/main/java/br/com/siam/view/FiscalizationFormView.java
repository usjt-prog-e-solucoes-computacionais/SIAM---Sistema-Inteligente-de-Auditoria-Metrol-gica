package br.com.siam.view;

import br.com.siam.controller.FiscalizationController;
import br.com.siam.model.Fiscalization;
import br.com.siam.model.GasPump;
import br.com.siam.model.GasStation;
import br.com.siam.model.User;
import br.com.siam.service.GasPumpService;
import br.com.siam.service.GasStationService;
import br.com.siam.util.IconUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class FiscalizationFormView extends JFrame {

    private final User authenticatedUser;

    private final FiscalizationController fiscalizationController;
    private final GasStationService gasStationService;
    private final GasPumpService gasPumpService;

    private JComboBox<GasStation> gasStationComboBox;
    private JComboBox<GasPump> gasPumpComboBox;

    private JComboBox<String> irregularityTypeComboBox;
    private JComboBox<String> auditStatusComboBox;

    private JTextField measurementErrorField;

    private JButton saveButton;

    private JTextField searchField;
    private JButton searchButton;

    private JTable fiscalizationTable;
    private DefaultTableModel fiscalizationTableModel;

    public FiscalizationFormView(User authenticatedUser) {

        this.authenticatedUser = authenticatedUser;

        this.fiscalizationController =
                new FiscalizationController();

        this.gasStationService =
                new GasStationService();

        this.gasPumpService =
                new GasPumpService();

        initializeComponents();
        configureWindow();
        buildLayout();
        registerEvents();

        loadGasStations();
        loadFiscalizations();
    }

    private void initializeComponents() {

        gasStationComboBox =
                new JComboBox<>();

        gasPumpComboBox =
                new JComboBox<>();

        irregularityTypeComboBox =
                new JComboBox<>(
                        new String[]{
                                "LACRE",
                                "PLACA_IDENTIFICACAO",
                                "ERRO_VOLUMETRICO",
                                "CALIBRACAO",
                                "OUTRO"
                        }
                );

        auditStatusComboBox =
                new JComboBox<>(
                        new String[]{
                                "PENDING",
                                "IN_PROGRESS",
                                "COMPLETED"
                        }
                );

        measurementErrorField =
                new JTextField(15);

        saveButton =
                new JButton("Salvar Fiscalização");

        searchField =
                new JTextField(25);

        searchButton =
                new JButton("Buscar");

        String[] columns = {
                "ID",
                "Fiscal",
                "Série",
                "Irregularidade",
                "Erro",
                "Status",
                "Data"
        };

        fiscalizationTableModel =
                new DefaultTableModel(columns, 0) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column
                    ) {
                        return false;
                    }
                };

        fiscalizationTable =
                new JTable(fiscalizationTableModel);
    }

    private void configureWindow() {

        setTitle("Cadastro de Fiscalizações");

        setIconImage(
                IconUtils.getApplicationIcon()
        );

        setSize(1200, 700);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );
    }

    private void buildLayout() {

        JPanel mainPanel =
                new JPanel(new BorderLayout(10, 10));

        mainPanel.setBorder(
                new EmptyBorder(10,10,10,10)
        );

        JPanel formPanel =
                new JPanel(new GridBagLayout());

        formPanel.setBorder(
                BorderFactory.createTitledBorder(
                        "Nova Fiscalização"
                )
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets = new Insets(8,8,8,8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Posto:"), gbc);

        gbc.gridx = 1;
        formPanel.add(gasStationComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Bomba:"), gbc);

        gbc.gridx = 1;
        formPanel.add(gasPumpComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Irregularidade:"), gbc);

        gbc.gridx = 1;
        formPanel.add(irregularityTypeComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Erro de Medição:"), gbc);

        gbc.gridx = 1;
        formPanel.add(measurementErrorField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(new JLabel("Status:"), gbc);

        gbc.gridx = 1;
        formPanel.add(auditStatusComboBox, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        formPanel.add(saveButton, gbc);

        JPanel searchPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT
                        )
                );

        searchPanel.add(
                new JLabel("Pesquisar:")
        );

        searchPanel.add(searchField);

        searchPanel.add(searchButton);

        JScrollPane tableScrollPane =
                new JScrollPane(
                        fiscalizationTable
                );

        JPanel centerPanel =
                new JPanel(
                        new BorderLayout()
                );

        centerPanel.add(
                searchPanel,
                BorderLayout.NORTH
        );

        centerPanel.add(
                tableScrollPane,
                BorderLayout.CENTER
        );

        mainPanel.add(
                formPanel,
                BorderLayout.NORTH
        );

        mainPanel.add(
                centerPanel,
                BorderLayout.CENTER
        );

        setContentPane(mainPanel);
    }

    private void registerEvents() {

        gasStationComboBox.addActionListener(
                event -> loadGasPumpsByStation()
        );

        saveButton.addActionListener(
                event -> saveFiscalization()
        );

        searchButton.addActionListener(
                event -> searchFiscalizations()
        );
    }

    private void loadGasStations() {

        gasStationComboBox.removeAllItems();

        List<GasStation> gasStations =
                gasStationService.getAllGasStations();

        for (GasStation gasStation : gasStations) {

            gasStationComboBox.addItem(
                    gasStation
            );
        }

        loadGasPumpsByStation();
    }

    private void loadGasPumpsByStation() {

        gasPumpComboBox.removeAllItems();

        GasStation selectedStation =
                (GasStation)
                        gasStationComboBox
                                .getSelectedItem();

        if (selectedStation == null) {
            return;
        }

        List<GasPump> gasPumps =
                gasPumpService.getByGasStation(
                        selectedStation.getId()
                );

        for (GasPump gasPump : gasPumps) {

            gasPumpComboBox.addItem(
                    gasPump
            );
        }
    }

    private void saveFiscalization() {

        try {

            GasPump selectedGasPump =
                    (GasPump)
                            gasPumpComboBox
                                    .getSelectedItem();

            Fiscalization fiscalization =
                    new Fiscalization();

            fiscalization.setUser(
                    authenticatedUser
            );

            fiscalization.setGasPump(
                    selectedGasPump
            );

            fiscalization.setIrregularityType(
                    irregularityTypeComboBox
                            .getSelectedItem()
                            .toString()
            );

            fiscalization.setMeasurementError(
                    new BigDecimal(
                            measurementErrorField
                                    .getText()
                                    .trim()
                    )
            );

            fiscalization.setAuditStatus(
                    auditStatusComboBox
                            .getSelectedItem()
                            .toString()
            );

            fiscalization.setFiscalizationDate(
                    LocalDateTime.now()
            );

            fiscalizationController
                    .createFiscalization(
                            fiscalization
                    );

            JOptionPane.showMessageDialog(
                    this,
                    "Fiscalização cadastrada com sucesso!"
            );

            clearForm();

            loadFiscalizations();

        } catch (Exception exception) {

            JOptionPane.showMessageDialog(
                    this,
                    exception.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void loadFiscalizations() {

        fiscalizationTableModel.setRowCount(0);

        List<Fiscalization> fiscalizations =
                fiscalizationController
                        .getAllFiscalizations();

        for (Fiscalization fiscalization
                : fiscalizations) {

            fiscalizationTableModel.addRow(
                    new Object[]{
                            fiscalization.getId(),
                            fiscalization.getUser().getName(),
                            fiscalization.getGasPump().getSerialNumber(),
                            fiscalization.getIrregularityType(),
                            fiscalization.getMeasurementError(),
                            fiscalization.getAuditStatus(),
                            fiscalization.getFiscalizationDate()
                    }
            );
        }
    }

    private void searchFiscalizations() {

        String term =
                searchField.getText().trim();

        fiscalizationTableModel.setRowCount(0);

        List<Fiscalization> fiscalizations =
                fiscalizationController
                        .search(term);

        for (Fiscalization fiscalization
                : fiscalizations) {

            fiscalizationTableModel.addRow(
                    new Object[]{
                            fiscalization.getId(),
                            fiscalization.getUser().getName(),
                            fiscalization.getGasPump().getSerialNumber(),
                            fiscalization.getIrregularityType(),
                            fiscalization.getMeasurementError(),
                            fiscalization.getAuditStatus(),
                            fiscalization.getFiscalizationDate()
                    }
            );
        }
    }

    private void clearForm() {

        measurementErrorField.setText("");
    }
}
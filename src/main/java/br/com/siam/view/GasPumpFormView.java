package br.com.siam.view;

import br.com.siam.controller.GasPumpController;
import br.com.siam.model.GasPump;
import br.com.siam.model.GasStation;
import br.com.siam.model.User;
import br.com.siam.service.GasStationService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class GasPumpFormView extends JFrame {

    private final GasPumpController gasPumpController;
    private final GasStationService gasStationService;

    private JComboBox<GasStation> gasStationComboBox;

    private JTextField serialNumberField;
    private JTextField modelField;

    private JButton saveButton;

    private JTextField searchField;
    private JButton searchButton;

    private JTable gasPumpTable;
    private DefaultTableModel gasPumpTableModel;

    private User authenticatedUser;

    public GasPumpFormView(User authenticatedUser) {

        this.authenticatedUser = authenticatedUser;

        this.gasPumpController =
                new GasPumpController();

        this.gasStationService =
                new GasStationService();

        initializeComponents();

        configureWindow();

        buildLayout();

        registerEvents();

        loadGasStations();

        loadGasPumps();
    }

    private void initializeComponents() {

        gasStationComboBox =
                new JComboBox<>();

        serialNumberField =
                new JTextField(20);

        modelField =
                new JTextField(20);

        saveButton =
                new JButton("Salvar");

        searchField =
                new JTextField(20);

        searchButton =
                new JButton("Buscar");

        String[] columns = {
                "ID",
                "Posto",
                "Série",
                "Modelo"
        };

        gasPumpTableModel =
                new DefaultTableModel(columns, 0) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column
                    ) {
                        return false;
                    }
                };

        gasPumpTable =
                new JTable(gasPumpTableModel);
    }

    private void configureWindow() {

        setTitle("Cadastro de Bombas");

        setSize(900, 600);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );
    }

    private void buildLayout() {

        JPanel mainPanel =
                new JPanel(new BorderLayout(10, 10));

        JPanel formPanel =
                new JPanel(new GridLayout(4, 2, 10, 10));

        formPanel.setBorder(
                BorderFactory.createTitledBorder(
                        "Nova Bomba"
                )
        );

        formPanel.add(new JLabel("Posto:"));
        formPanel.add(gasStationComboBox);

        formPanel.add(new JLabel("Número de Série:"));
        formPanel.add(serialNumberField);

        formPanel.add(new JLabel("Modelo:"));
        formPanel.add(modelField);

        formPanel.add(new JLabel());
        formPanel.add(saveButton);

        JPanel searchPanel =
                new JPanel(new FlowLayout(
                        FlowLayout.LEFT
                ));

        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        JScrollPane scrollPane =
                new JScrollPane(gasPumpTable);

        JPanel centerPanel =
                new JPanel(new BorderLayout());

        centerPanel.add(
                searchPanel,
                BorderLayout.NORTH
        );

        centerPanel.add(
                scrollPane,
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

        saveButton.addActionListener(event -> {
            saveGasPump();
        });

        searchButton.addActionListener(event -> {
            searchGasPumps();
        });

        gasPumpTable.addMouseListener(
                new MouseAdapter() {

                    @Override
                    public void mouseClicked(
                            MouseEvent event
                    ) {

                        if (
                                event.getClickCount() == 2
                        ) {

                            openGasPumpDetails();
                        }
                    }
                }
        );
    }

    private void openGasPumpDetails() {

        int selectedRow =
                gasPumpTable.getSelectedRow();

        if (selectedRow == -1) {

            return;
        }

        Integer gasPumpId =
                (Integer)
                        gasPumpTableModel
                                .getValueAt(
                                        selectedRow,
                                        0
                                );

        GasPumpDetailsView view =
                new GasPumpDetailsView(
                        gasPumpId,
                        authenticatedUser
                );

        view.setVisible(true);
    }

    private void loadGasStations() {

        gasStationComboBox.removeAllItems();

        List<GasStation> gasStations =
                gasStationService.getAllGasStations();

        for (GasStation gasStation : gasStations) {

            gasStationComboBox.addItem(gasStation);
        }
    }

    private void saveGasPump() {

        try {

            GasStation selectedGasStation =
                    (GasStation)
                            gasStationComboBox
                                    .getSelectedItem();

            gasPumpController.createGasPump(
                    selectedGasStation,
                    serialNumberField.getText().trim(),
                    modelField.getText().trim()
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Bomba cadastrada com sucesso!"
            );

            clearForm();

            loadGasPumps();

        } catch (Exception exception) {

            JOptionPane.showMessageDialog(
                    this,
                    exception.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void loadGasPumps() {

        gasPumpTableModel.setRowCount(0);

        List<GasPump> gasPumps =
                gasPumpController.getAllGasPumps();

        for (GasPump gasPump : gasPumps) {

            gasPumpTableModel.addRow(
                    new Object[]{
                            gasPump.getId(),
                            gasPump.getGasStation()
                                    .getCorporateName(),
                            gasPump.getSerialNumber(),
                            gasPump.getModel()
                    }
            );
        }
    }

    private void searchGasPumps() {

        String term =
                searchField.getText().trim();

        gasPumpTableModel.setRowCount(0);

        List<GasPump> gasPumps =
                gasPumpController
                        .searchGasPumps(term);

        for (GasPump gasPump : gasPumps) {

            gasPumpTableModel.addRow(
                    new Object[]{
                            gasPump.getId(),
                            gasPump.getGasStation()
                                    .getCorporateName(),
                            gasPump.getSerialNumber(),
                            gasPump.getModel()
                    }
            );
        }
    }

    private void clearForm() {

        serialNumberField.setText("");

        modelField.setText("");
    }
}
package br.com.siam.view;

import br.com.siam.controller.GasStationController;
import br.com.siam.model.GasStation;
import br.com.siam.model.User;
import br.com.siam.util.IconUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class GasStationFormView extends JFrame {

    private User authenticatedUser;

    private final GasStationController gasStationController;

    /*
     * Form Components
     */
    private JTextField cnpjField;

    private JTextField corporateNameField;

    private JTextField addressField;

    private JButton saveButton;

    /*
     * Search Components
     */
    private JTextField searchField;

    private JButton searchButton;

    /*
     * Table Components
     */
    private JTable gasStationTable;

    private DefaultTableModel gasStationTableModel;

    public GasStationFormView(User authenticatedUser) {

        this.gasStationController =
                new GasStationController();

        this.authenticatedUser = authenticatedUser;

        initializeComponents();

        configureWindow();

        buildLayout();

        registerEvents();

        loadGasStations();
    }

    private void initializeComponents() {

        cnpjField = new JTextField(20);

        corporateNameField = new JTextField(25);

        addressField = new JTextField(30);

        saveButton = new JButton("Salvar");

        searchField = new JTextField(20);

        searchButton = new JButton("Buscar");

        String[] columns = {
                "ID",
                "CNPJ",
                "Razão Social",
                "Endereço"
        };

        gasStationTableModel =
                new DefaultTableModel(columns, 0) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column
                    ) {
                        return false;
                    }
                };

        gasStationTable =
                new JTable(gasStationTableModel);

        gasStationTable.setRowHeight(28);
    }

    private void configureWindow() {

        setTitle("Cadastro de Postos");

        setIconImage(
                IconUtils.getApplicationIcon()
        );

        setSize(900, 600);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void buildLayout() {

        JPanel mainPanel =
                new JPanel(new BorderLayout(10, 10));

        /*
         * Form Panel
         */
        JPanel formPanel =
                new JPanel(new GridLayout(4, 2, 10, 10));

        formPanel.setBorder(
                BorderFactory.createTitledBorder(
                        "Novo Posto"
                )
        );

        formPanel.add(new JLabel("CNPJ:"));
        formPanel.add(cnpjField);

        formPanel.add(new JLabel("Razão Social:"));
        formPanel.add(corporateNameField);

        formPanel.add(new JLabel("Endereço:"));
        formPanel.add(addressField);

        formPanel.add(new JLabel());
        formPanel.add(saveButton);

        /*
         * Search Panel
         */
        JPanel searchPanel =
                new JPanel(new FlowLayout(FlowLayout.LEFT));

        searchPanel.setBorder(
                BorderFactory.createTitledBorder(
                        "Busca"
                )
        );

        searchPanel.add(searchField);

        searchPanel.add(searchButton);

        /*
         * Table Panel
         */
        JScrollPane tableScrollPane =
                new JScrollPane(gasStationTable);

        JPanel centerPanel =
                new JPanel(new BorderLayout());

        centerPanel.add(searchPanel, BorderLayout.NORTH);

        centerPanel.add(tableScrollPane, BorderLayout.CENTER);

        mainPanel.add(formPanel, BorderLayout.NORTH);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        setContentPane(mainPanel);
    }

    private void registerEvents() {

        saveButton.addActionListener(event -> {
            handleSaveGasStation();
        });

        searchButton.addActionListener(event -> {
            handleSearch();
        });

        gasStationTable.addMouseListener(
                new MouseAdapter() {

                    @Override
                    public void mouseClicked(
                            MouseEvent event
                    ) {

                        if (
                                event.getClickCount() == 2
                        ) {

                            openGasStationDetails();
                        }
                    }
                }
        );
    }

    private void openGasStationDetails() {

        int selectedRow =
                gasStationTable.getSelectedRow();

        if (selectedRow == -1) {

            return;
        }

        Integer gasStationId =
                (Integer)
                        gasStationTableModel
                                .getValueAt(
                                        selectedRow,
                                        0
                                );

        GasStationDetailsView view =
                new GasStationDetailsView(
                        gasStationId,
                        authenticatedUser
                );

        view.setVisible(true);
    }

    private void handleSaveGasStation() {

        try {

            gasStationController.createGasStation(
                    cnpjField.getText().trim(),
                    corporateNameField.getText().trim(),
                    addressField.getText().trim()
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Posto cadastrado com sucesso!"
            );

            clearForm();

            loadGasStations();

        } catch (Exception exception) {

            JOptionPane.showMessageDialog(
                    this,
                    exception.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void loadGasStations() {

        gasStationTableModel.setRowCount(0);

        List<GasStation> gasStations =
                gasStationController.findAllGasStations();

        for (GasStation gasStation : gasStations) {

            gasStationTableModel.addRow(new Object[]{
                    gasStation.getId(),
                    gasStation.getCnpj(),
                    gasStation.getCorporateName(),
                    gasStation.getAddress()
            });
        }
    }

    private void handleSearch() {

        String term =
                searchField.getText().trim();

        gasStationTableModel.setRowCount(0);

        List<GasStation> gasStations =
                gasStationController
                        .searchGasStations(term);

        for (GasStation gasStation : gasStations) {

            gasStationTableModel.addRow(new Object[]{
                    gasStation.getId(),
                    gasStation.getCnpj(),
                    gasStation.getCorporateName(),
                    gasStation.getAddress()
            });
        }
    }

    private void clearForm() {

        cnpjField.setText("");

        corporateNameField.setText("");

        addressField.setText("");
    }
}
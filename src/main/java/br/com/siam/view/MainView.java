package br.com.siam.view;

import br.com.siam.controller.MainController;
import br.com.siam.model.Fiscalization;
import br.com.siam.model.User;
import br.com.siam.service.FiscalizationService;
import br.com.siam.view.FiscalizationFormView;
import br.com.siam.view.GasPumpFormView;
import br.com.siam.view.GasStationFormView;
import br.com.siam.view.LoginView;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MainView extends JFrame {

    private final User authenticatedUser;

    private MainController mainController;

    private final FiscalizationService fiscalizationService;

    /*
     * Header Components
     */
    private JLabel systemTitleLabel;
    private JLabel loggedUserLabel;

    private JButton profileButton;
    private JButton logoutButton;

    /*
     * Action Components
     */
    private JButton newFiscalizationButton;
    private JButton newGasStationButton;
    private JButton newGasPumpButton;
    private JButton indicatorsButton;

    /*
     * Filter Components
     */
    private JLabel searchLabel;

    private JTextField searchField;

    private JButton searchButton;

    private JCheckBox onlyMyFiscalizationsCheckBox;

    /*
     * Listing Components
     */
    private JTable fiscalizationTable;

    private DefaultTableModel fiscalizationTableModel;

    private JScrollPane fiscalizationScrollPane;

    /*
     * Pagination Components
     */
    private JButton previousPageButton;
    private JButton nextPageButton;

    private JLabel currentPageLabel;

    /*
     * Admin Components
     */
    private JPanel adminPanel;

    private JButton adminPanelButton;

    public MainView(User authenticatedUser) {

        this.authenticatedUser = authenticatedUser;

        this.fiscalizationService = new FiscalizationService();

        initializeComponents();

        configureWindow();

        buildLayout();

        registerEvents();

        this.mainController =
                new MainController(this, authenticatedUser);

        displayAuthenticatedUser();

        configurePermissions();

        loadFiscalizations();
    }

    private void initializeComponents() {

        /*
         * Header
         */
        systemTitleLabel =
                new JLabel("SIAM - Sistema Inteligente de Auditoria");

        systemTitleLabel.setFont(
                new Font("Arial", Font.BOLD, 18)
        );

        loggedUserLabel = new JLabel();

        profileButton = new JButton("Perfil");

        logoutButton = new JButton("Sair");

        /*
         * Actions
         */
        newFiscalizationButton =
                new JButton("Nova Fiscalização");

        newGasStationButton =
                new JButton("Novo Posto");

        newGasPumpButton =
                new JButton("Nova Bomba");

        indicatorsButton =
                new JButton("Indicadores");

        /*
         * Filters
         */
        searchLabel = new JLabel("Buscar:");

        searchField = new JTextField(30);

        searchButton = new JButton("Filtrar");

        onlyMyFiscalizationsCheckBox =
                new JCheckBox("Somente minhas fiscalizações");

        onlyMyFiscalizationsCheckBox.setSelected(true);

        /*
         * Table
         */
        String[] columns = {
                "ID",
                "Tipo Irregularidade",
                "Status",
                "Erro Medição",
                "Data"
        };

        fiscalizationTableModel =
                new DefaultTableModel(columns, 0) {

                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                };

        fiscalizationTable =
                new JTable(fiscalizationTableModel);

        fiscalizationTable.setRowHeight(28);

        fiscalizationScrollPane =
                new JScrollPane(fiscalizationTable);

        /*
         * Pagination
         */
        previousPageButton = new JButton("<<");

        nextPageButton = new JButton(">>");

        currentPageLabel = new JLabel("Página 1");

        /*
         * Admin
         */
        adminPanel = new JPanel();

        adminPanel.setLayout(
                new BoxLayout(adminPanel, BoxLayout.Y_AXIS)
        );

        adminPanel.setBorder(
                BorderFactory.createTitledBorder("Admin")
        );

        adminPanelButton =
                new JButton("Gerenciar Usuários");

        adminPanel.add(adminPanelButton);
    }

    private void configureWindow() {

        setTitle("SIAM - Dashboard Principal");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setExtendedState(JFrame.MAXIMIZED_BOTH);

        setLocationRelativeTo(null);

        setMinimumSize(new Dimension(1200, 700));
    }

    private void buildLayout() {

        JPanel mainPanel =
                new JPanel(new BorderLayout(15, 15));

        mainPanel.setBorder(
                new EmptyBorder(15, 15, 15, 15)
        );

        mainPanel.add(buildHeaderPanel(), BorderLayout.NORTH);

        mainPanel.add(buildSidebarPanel(), BorderLayout.WEST);

        mainPanel.add(buildCenterPanel(), BorderLayout.CENTER);

        mainPanel.add(buildRightPanel(), BorderLayout.EAST);

        setContentPane(mainPanel);
    }

    private void handleCreateGasStation() {

        GasStationFormView view =
                new GasStationFormView(authenticatedUser);

        view.addWindowListener(
                new java.awt.event.WindowAdapter() {

                    @Override
                    public void windowClosed(
                            java.awt.event.WindowEvent e
                    ) {

                        loadFiscalizations();
                    }
                }
        );

        view.setVisible(true);
    }

    private JPanel buildHeaderPanel() {

        JPanel headerPanel =
                new JPanel(new BorderLayout());

        JPanel leftPanel =
                new JPanel(new FlowLayout(FlowLayout.LEFT));

        leftPanel.add(systemTitleLabel);

        JPanel rightPanel =
                new JPanel(new FlowLayout(FlowLayout.RIGHT));

        rightPanel.add(loggedUserLabel);

        rightPanel.add(profileButton);

        rightPanel.add(logoutButton);

        headerPanel.add(leftPanel, BorderLayout.WEST);

        headerPanel.add(rightPanel, BorderLayout.EAST);

        return headerPanel;
    }

    private JPanel buildSidebarPanel() {

        JPanel sidebarPanel =
                new JPanel();

        sidebarPanel.setLayout(
                new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS)
        );

        sidebarPanel.setPreferredSize(
                new Dimension(220, 0)
        );

        sidebarPanel.add(newFiscalizationButton);

        sidebarPanel.add(Box.createVerticalStrut(10));

        sidebarPanel.add(newGasStationButton);

        sidebarPanel.add(Box.createVerticalStrut(10));

        sidebarPanel.add(newGasPumpButton);

        sidebarPanel.add(Box.createVerticalStrut(10));

        sidebarPanel.add(indicatorsButton);

        return sidebarPanel;
    }

    private JPanel buildCenterPanel() {

        JPanel centerPanel =
                new JPanel(new BorderLayout(10, 10));

        centerPanel.add(buildFiltersPanel(), BorderLayout.NORTH);

        centerPanel.add(buildListingPanel(), BorderLayout.CENTER);

        centerPanel.add(buildPaginationPanel(), BorderLayout.SOUTH);

        return centerPanel;
    }

    private JPanel buildFiltersPanel() {

        JPanel filtersPanel =
                new JPanel(new FlowLayout(FlowLayout.LEFT));

        filtersPanel.add(searchLabel);

        filtersPanel.add(searchField);

        filtersPanel.add(searchButton);

        filtersPanel.add(onlyMyFiscalizationsCheckBox);

        return filtersPanel;
    }

    private JPanel buildListingPanel() {

        JPanel listingPanel =
                new JPanel(new BorderLayout());

        listingPanel.add(
                fiscalizationScrollPane,
                BorderLayout.CENTER
        );

        return listingPanel;
    }

    private JPanel buildPaginationPanel() {

        JPanel paginationPanel =
                new JPanel(new FlowLayout(FlowLayout.CENTER));

        paginationPanel.add(previousPageButton);

        paginationPanel.add(currentPageLabel);

        paginationPanel.add(nextPageButton);

        return paginationPanel;
    }

    private JPanel buildRightPanel() {

        JPanel rightPanel =
                new JPanel(new BorderLayout());

        rightPanel.setPreferredSize(
                new Dimension(220, 0)
        );

        rightPanel.add(adminPanel, BorderLayout.NORTH);

        return rightPanel;
    }

    private void displayAuthenticatedUser() {

        loggedUserLabel.setText(
                authenticatedUser.getName()
                        + " (" + authenticatedUser.getUserType() + ")"
        );
    }

    private void configurePermissions() {

        boolean isAdmin =
                authenticatedUser.getUserType()
                        .equalsIgnoreCase("ADMIN");

        adminPanel.setVisible(isAdmin);
    }

    private void loadFiscalizations() {

        fiscalizationTableModel.setRowCount(0);

        List<Fiscalization> fiscalizations;

        if (onlyMyFiscalizationsCheckBox.isSelected()) {

            fiscalizations =
                    fiscalizationService
                            .getUserFiscalizations(
                                    authenticatedUser.getId()
                            );

        } else {

            fiscalizations =
                    fiscalizationService
                            .getAllFiscalizations();
        }

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        for (Fiscalization fiscalization : fiscalizations) {

            fiscalizationTableModel.addRow(
                    new Object[]{
                            fiscalization.getId(),
                            fiscalization.getUser().getName(),
                            fiscalization.getGasPump()
                                    .getGasStation(),
                            fiscalization.getGasPump()
                                    .getSerialNumber(),
                            fiscalization.getIrregularityType(),
                            fiscalization.getAuditStatus(),
                            fiscalization.getMeasurementError(),
                            fiscalization.getFiscalizationDate()
                                    .format(formatter)
                    }
            );
        }
    }

    private void handleCreateFiscalization() {

        FiscalizationFormView fiscalizationFormView =
                new FiscalizationFormView(authenticatedUser);

        fiscalizationFormView.setVisible(true);
    }

    private void handleCreateGasPump() {

        GasPumpFormView gasPumpFormView =
                new GasPumpFormView(authenticatedUser);

        gasPumpFormView.setVisible(true);
    }

    private void handleProfile() {

        ProfileView profileView =
                new ProfileView(
                        authenticatedUser
                );

        profileView.setVisible(true);
    }

    private void handleIndicators() {
        DashboardView dashboardView = new DashboardView();

        dashboardView.setVisible(true);
    }

    private void handleLogout() {

        int option = JOptionPane.showConfirmDialog(
                this,
                "Deseja realmente sair do sistema?",
                "Confirmação",
                JOptionPane.YES_NO_OPTION
        );

        if (option == JOptionPane.YES_OPTION) {

            LoginView loginView =
                    new LoginView();

            loginView.setVisible(true);

            dispose();
        }
    }

    private void registerEvents() {

        onlyMyFiscalizationsCheckBox
                .addActionListener(event ->
                        loadFiscalizations()
                );

        searchButton
                .addActionListener(event -> {

                    String searchTerm =
                            searchField.getText().trim();

                    if (searchTerm.isEmpty()) {

                        loadFiscalizations();

                        return;
                    }

                    loadFiscalizationsBySearch(
                            searchTerm
                    );
                });

        newFiscalizationButton
                .addActionListener(event ->
                        handleCreateFiscalization()
                );

        newGasStationButton
                .addActionListener(event ->
                        handleCreateGasStation()
                );

        newGasPumpButton
                .addActionListener(event ->
                        handleCreateGasPump()
                );

        indicatorsButton
                .addActionListener(event ->
                        handleIndicators()
                );

        profileButton
                .addActionListener(event ->
                        handleProfile()
                );

        logoutButton
                .addActionListener(event ->
                        handleLogout()
                );

        adminPanelButton.addActionListener(
                event -> handleAdminPanel()
        );

        fiscalizationTable.addMouseListener(
                new MouseAdapter() {

                    @Override
                    public void mouseClicked(
                            MouseEvent event
                    ) {

                        if (event.getClickCount() == 2) {

                            openFiscalizationDetails();
                        }
                    }
                }
        );
    }

    private void openFiscalizationDetails() {

        int selectedRow =
                fiscalizationTable.getSelectedRow();

        if (selectedRow == -1) {

            return;
        }

        Integer fiscalizationId =
                (Integer)
                        fiscalizationTableModel.getValueAt(
                                selectedRow,
                                0
                        );

        FiscalizationDetailsView detailsView =
                new FiscalizationDetailsView(
                        fiscalizationId,
                        authenticatedUser
                );

        detailsView.setVisible(true);
    }

    private void handleAdminPanel() {

        AdminPanelView adminPanelView =
                new AdminPanelView();

        adminPanelView.setVisible(true);
    }

    private void loadFiscalizationsBySearch(String searchTerm) {

        fiscalizationTableModel.setRowCount(0);

        List<Fiscalization> fiscalizations =
                fiscalizationService
                        .search(searchTerm);

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        for (Fiscalization fiscalization : fiscalizations) {

            fiscalizationTableModel.addRow(new Object[]{
                    fiscalization.getId(),
                    fiscalization.getIrregularityType(),
                    fiscalization.getAuditStatus(),
                    fiscalization.getMeasurementError(),
                    fiscalization.getFiscalizationDate()
                            .format(formatter)
            });
        }
    }
}
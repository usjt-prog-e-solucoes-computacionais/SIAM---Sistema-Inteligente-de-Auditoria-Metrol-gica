package br.com.siam.view;

import br.com.siam.controller.MainController;
import br.com.siam.model.User;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {

    private final User authenticatedUser;

    private MainController mainController;

    private JLabel headerSectionLabel;
    private JLabel actionsSectionLabel;
    private JLabel filtersSectionLabel;
    private JLabel listingSectionLabel;
    private JLabel paginationSectionLabel;
    private JLabel loggedUserLabel;

    private JPanel adminPanelPlaceholder;

    public MainView(User authenticatedUser) {
        this.authenticatedUser = authenticatedUser;

        initializeComponents();
        configureWindow();
        buildLayout();

        this.mainController = new MainController(this, authenticatedUser);
    }

    private void initializeComponents() {
        headerSectionLabel = new JLabel("Header Section (Usuário / Perfil)");
        actionsSectionLabel = new JLabel("Área de Ações");
        filtersSectionLabel = new JLabel("Área de Filtros");
        listingSectionLabel = new JLabel("Área de Listagem");
        paginationSectionLabel = new JLabel("Área de Paginação");
        loggedUserLabel = new JLabel("Usuário autenticado: ");

        adminPanelPlaceholder = new JPanel();
        adminPanelPlaceholder.add(new JLabel("Painel Admin"));
        adminPanelPlaceholder.setBorder(BorderFactory.createLineBorder(Color.GRAY));
    }

    private void configureWindow() {
        setTitle("SIAM - Tela Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
    }

    private void buildLayout() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBorder(BorderFactory.createTitledBorder("Header"));
        headerPanel.add(headerSectionLabel);
        headerPanel.add(loggedUserLabel);

        JPanel actionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        actionsPanel.setBorder(BorderFactory.createTitledBorder("Ações"));
        actionsPanel.add(actionsSectionLabel);

        JPanel filtersPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filtersPanel.setBorder(BorderFactory.createTitledBorder("Filtros"));
        filtersPanel.add(filtersSectionLabel);

        JPanel listingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        listingPanel.setBorder(BorderFactory.createTitledBorder("Listagem"));
        listingPanel.add(listingSectionLabel);

        JPanel paginationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        paginationPanel.setBorder(BorderFactory.createTitledBorder("Paginação"));
        paginationPanel.add(paginationSectionLabel);

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.add(actionsPanel, BorderLayout.NORTH);
        centerPanel.add(filtersPanel, BorderLayout.CENTER);
        centerPanel.add(listingPanel, BorderLayout.SOUTH);

        JPanel rigthPanel = new JPanel(new BorderLayout());
        rigthPanel.setBorder(BorderFactory.createTitledBorder("Admin"));
        rigthPanel.add(adminPanelPlaceholder, BorderLayout.NORTH);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(paginationPanel, BorderLayout.SOUTH);
        mainPanel.add(rigthPanel, BorderLayout.EAST);

        setContentPane(mainPanel);
    }

    public void displayAuthenticatedUser(User user) {
        loggedUserLabel.setText("Usuário autenticado: " + user.getName() + " (" + user.getUserType() + ")");
    }

    public void setAdminPanelVisible(boolean visible) {
        adminPanelPlaceholder.setVisible(visible);
    }
}

package br.com.siam.view;

import br.com.siam.controller.DashboardController;
import br.com.siam.dto.DashboardDTO;
import br.com.siam.util.IconUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DashboardView extends JFrame {

    private final DashboardController dashboardController;

    private JLabel totalFiscalizationsValue;

    private JLabel totalGasStationsValue;

    private JLabel totalGasPumpsValue;

    private JLabel pendingValue;

    private JLabel completedValue;

    private JLabel archivedValue;

    public DashboardView() {

        this.dashboardController =
                new DashboardController();

        initializeComponents();

        configureWindow();

        buildLayout();

        loadDashboard();
    }

    private void initializeComponents() {

        totalFiscalizationsValue =
                new JLabel("0");

        totalGasStationsValue =
                new JLabel("0");

        totalGasPumpsValue =
                new JLabel("0");

        pendingValue =
                new JLabel("0");

        completedValue =
                new JLabel("0");

        archivedValue =
                new JLabel("0");
    }

    private void configureWindow() {

        setTitle("Dashboard SIAM");

        setIconImage(
                IconUtils.getApplicationIcon()
        );

        setSize(900, 500);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                DISPOSE_ON_CLOSE
        );
    }

    private void buildLayout() {

        JPanel mainPanel =
                new JPanel(
                        new BorderLayout()
                );

        mainPanel.setBorder(
                new EmptyBorder(
                        20,
                        20,
                        20,
                        20
                )
        );

        JLabel title =
                new JLabel(
                        "Dashboard SIAM",
                        SwingConstants.CENTER
                );

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );

        mainPanel.add(
                title,
                BorderLayout.NORTH
        );

        JPanel cardsPanel =
                new JPanel(
                        new GridLayout(
                                2,
                                3,
                                15,
                                15
                        )
                );

        cardsPanel.add(
                createCard(
                        "Fiscalizações",
                        totalFiscalizationsValue
                )
        );

        cardsPanel.add(
                createCard(
                        "Postos Ativos",
                        totalGasStationsValue
                )
        );

        cardsPanel.add(
                createCard(
                        "Bombas Ativas",
                        totalGasPumpsValue
                )
        );

        cardsPanel.add(
                createCard(
                        "Pendentes",
                        pendingValue
                )
        );

        cardsPanel.add(
                createCard(
                        "Concluídas",
                        completedValue
                )
        );

        cardsPanel.add(
                createCard(
                        "Arquivadas",
                        archivedValue
                )
        );

        mainPanel.add(
                cardsPanel,
                BorderLayout.CENTER
        );

        setContentPane(mainPanel);
    }

    private JPanel createCard(
            String title,
            JLabel valueLabel
    ) {

        JPanel card =
                new JPanel(
                        new BorderLayout()
                );

        card.setBorder(
                BorderFactory.createTitledBorder(
                        title
                )
        );

        valueLabel.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        valueLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        28
                )
        );

        card.add(
                valueLabel,
                BorderLayout.CENTER
        );

        return card;
    }

    private void loadDashboard() {

        DashboardDTO dashboard =
                dashboardController
                        .getIndicators();

        totalFiscalizationsValue.setText(
                String.valueOf(
                        dashboard.getTotalFiscalizations()
                )
        );

        totalGasStationsValue.setText(
                String.valueOf(
                        dashboard.getTotalGasStations()
                )
        );

        totalGasPumpsValue.setText(
                String.valueOf(
                        dashboard.getTotalGasPumps()
                )
        );

        pendingValue.setText(
                String.valueOf(
                        dashboard.getPendingFiscalizations()
                )
        );

        completedValue.setText(
                String.valueOf(
                        dashboard.getCompletedFiscalizations()
                )
        );

        archivedValue.setText(
                String.valueOf(
                        dashboard.getArchivedFiscalizations()
                )
        );
    }
}
package br.com.siam.service;

import br.com.siam.dao.DashboardDAO;
import br.com.siam.dao.impl.DashboardDAOImpl;
import br.com.siam.dto.DashboardDTO;

public class DashboardService {

    private final DashboardDAO dashboardDAO;

    public DashboardService() {

        this.dashboardDAO =
                new DashboardDAOImpl();
    }

    public DashboardDTO getIndicators() {

        return dashboardDAO.getIndicators();
    }
}
package br.com.siam.controller;

import br.com.siam.dto.DashboardDTO;
import br.com.siam.service.DashboardService;

public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController() {

        this.dashboardService =
                new DashboardService();
    }

    public DashboardDTO getIndicators() {

        return dashboardService.getIndicators();
    }
}
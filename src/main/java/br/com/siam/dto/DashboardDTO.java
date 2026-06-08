package br.com.siam.dto;

public class DashboardDTO {

    private Integer totalFiscalizations;

    private Integer totalGasStations;

    private Integer totalGasPumps;

    private Integer pendingFiscalizations;

    private Integer completedFiscalizations;

    private Integer archivedFiscalizations;

    public Integer getTotalFiscalizations() {
        return totalFiscalizations;
    }

    public void setTotalFiscalizations(Integer totalFiscalizations) {
        this.totalFiscalizations = totalFiscalizations;
    }

    public Integer getTotalGasStations() {
        return totalGasStations;
    }

    public void setTotalGasStations(Integer totalGasStations) {
        this.totalGasStations = totalGasStations;
    }

    public Integer getTotalGasPumps() {
        return totalGasPumps;
    }

    public void setTotalGasPumps(Integer totalGasPumps) {
        this.totalGasPumps = totalGasPumps;
    }

    public Integer getPendingFiscalizations() {
        return pendingFiscalizations;
    }

    public void setPendingFiscalizations(Integer pendingFiscalizations) {
        this.pendingFiscalizations = pendingFiscalizations;
    }

    public Integer getCompletedFiscalizations() {
        return completedFiscalizations;
    }

    public void setCompletedFiscalizations(Integer completedFiscalizations) {
        this.completedFiscalizations = completedFiscalizations;
    }

    public Integer getArchivedFiscalizations() {
        return archivedFiscalizations;
    }

    public void setArchivedFiscalizations(Integer archivedFiscalizations) {
        this.archivedFiscalizations = archivedFiscalizations;
    }
}
package br.com.siam.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Fiscalization {

    private Integer id;

    private User user;

    private GasPump gasPump;

    private LocalDateTime fiscalizationDate;

    private String irregularityType;

    private BigDecimal measurementError;

    private String auditStatus;

    private Boolean active;

    public Fiscalization() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public GasPump getGasPump() {
        return gasPump;
    }

    public void setGasPump(GasPump gasPump) {
        this.gasPump = gasPump;
    }

    public LocalDateTime getFiscalizationDate() {
        return fiscalizationDate;
    }

    public void setFiscalizationDate(LocalDateTime fiscalizationDate) {
        this.fiscalizationDate = fiscalizationDate;
    }

    public String getIrregularityType() {
        return irregularityType;
    }

    public void setIrregularityType(String irregularityType) {
        this.irregularityType = irregularityType;
    }

    public BigDecimal getMeasurementError() {
        return measurementError;
    }

    public void setMeasurementError(BigDecimal measurementError) {
        this.measurementError = measurementError;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Boolean getActive() { return active; };

    public void setActive(Boolean active) { this.active = active; }
}
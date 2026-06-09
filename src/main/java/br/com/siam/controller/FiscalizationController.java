package br.com.siam.controller;

import br.com.siam.model.Fiscalization;
import br.com.siam.service.FiscalizationService;

import java.util.List;

public class FiscalizationController {

    private final FiscalizationService fiscalizationService;

    public FiscalizationController() {

        this.fiscalizationService =
                new FiscalizationService();
    }

    public void createFiscalization(
            Fiscalization fiscalization
    ) {

        fiscalizationService.createFiscalization(
                fiscalization
        );
    }

    public List<Fiscalization> getAllFiscalizationsWithInactive() {

        return fiscalizationService
                .getAllFiscalizationsWithInactive();
    }

    public List<Fiscalization> getAllFiscalizations() {

        return fiscalizationService
                .getAllFiscalizations();
    }

    public List<Fiscalization> getMyFiscalizations(
            Integer userId
    ) {

        return fiscalizationService
                .getUserFiscalizations(userId);
    }

    public List<Fiscalization> search(
            String term
    ) {

        return fiscalizationService.search(term);
    }

    public Fiscalization getById(
            Integer fiscalizationId
    ) {

        return fiscalizationService.getById(
                fiscalizationId
        );
    }

    public boolean updateFiscalization(
            Fiscalization fiscalization
    ) {

        return fiscalizationService
                .updateFiscalization(
                        fiscalization
                );
    }

    public void archiveFiscalization(
            Integer fiscalizationId
    ) {

        fiscalizationService
                .archiveFiscalization(
                        fiscalizationId
                );
    }

    public void reactivateFiscalization(
            Integer fiscalizationId
    ) {

        fiscalizationService
                .reactivateFiscalization(
                        fiscalizationId
                );
    }
}
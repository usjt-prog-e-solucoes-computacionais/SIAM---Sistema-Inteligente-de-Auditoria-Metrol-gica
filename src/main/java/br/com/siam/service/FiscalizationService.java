package br.com.siam.service;

import br.com.siam.dao.FiscalizationDAO;
import br.com.siam.dao.impl.FiscalizationDAOImpl;
import br.com.siam.model.Fiscalization;

import java.util.List;

public class FiscalizationService {

    private final FiscalizationDAO fiscalizationDAO;

    public FiscalizationService() {

        this.fiscalizationDAO =
                new FiscalizationDAOImpl();
    }

    public void createFiscalization(
            Fiscalization fiscalization
    ) {

        validate(fiscalization);

        fiscalizationDAO.save(fiscalization);
    }

    public List<Fiscalization> getAllFiscalizations() {

        return fiscalizationDAO.findAll();
    }

    public List<Fiscalization> getUserFiscalizations(
            Integer userId
    ) {

        return fiscalizationDAO.findByUser(userId);
    }

    public List<Fiscalization> search(
            String term
    ) {

        return fiscalizationDAO.search(term);
    }

    private void validate(
            Fiscalization fiscalization
    ) {

        if (fiscalization.getGasPump() == null) {

            throw new IllegalArgumentException(
                    "Bomba obrigatória."
            );
        }

        if (fiscalization.getUser() == null) {

            throw new IllegalArgumentException(
                    "Usuário obrigatório."
            );
        }
    }

    public Fiscalization getById(
            Integer fiscalizationId
    ) {

        return fiscalizationDAO
                .findById(fiscalizationId)
                .orElseThrow(
                        () -> new RuntimeException(
                                "Fiscalização não encontrada."
                        )
                );
    }

    public boolean updateFiscalization(
            Fiscalization fiscalization
    ) {

        return fiscalizationDAO.update(
                fiscalization
        );
    }

    public void archiveFiscalization(
            Integer fiscalizationId
    ) {

        fiscalizationDAO.archive(
                fiscalizationId
        );
    }
}
package br.com.siam.service;

import br.com.siam.dao.GasPumpDAO;
import br.com.siam.dao.impl.GasPumpDAOImpl;
import br.com.siam.model.GasPump;

import java.util.List;

public class GasPumpService {

    private final GasPumpDAO gasPumpDAO;

    public GasPumpService() {

        this.gasPumpDAO =
                new GasPumpDAOImpl();
    }

    public void createGasPump(GasPump gasPump) {

        validate(gasPump);

        gasPumpDAO.save(gasPump);
    }

    public List<GasPump> getAllGasPumps() {

        return gasPumpDAO.findAll();
    }

    public List<GasPump> searchGasPumps(String term) {

        return gasPumpDAO.search(term);
    }

    public List<GasPump> getByGasStation(
            Integer gasStationId
    ) {

        return gasPumpDAO.findByGasStation(
                gasStationId
        );
    }

    private void validate(GasPump gasPump) {

        if (gasPump.getGasStation() == null) {

            throw new IllegalArgumentException(
                    "Selecione um posto."
            );
        }

        if (gasPump.getSerialNumber() == null
                || gasPump.getSerialNumber().isBlank()) {

            throw new IllegalArgumentException(
                    "Número de série obrigatório."
            );
        }

        if (gasPump.getModel() == null
                || gasPump.getModel().isBlank()) {

            throw new IllegalArgumentException(
                    "Modelo obrigatório."
            );
        }
    }

    public GasPump getById(Integer id) {

        return gasPumpDAO
                .findById(id)
                .orElseThrow(
                        () -> new RuntimeException(
                                "Bomba não encontrada."
                        )
                );
    }

    public boolean update(
            GasPump gasPump
    ) {

        return gasPumpDAO.update(
                gasPump
        );
    }

    public void deactivate(
            Integer id
    ) {

        gasPumpDAO.deactivate(id);
    }
}
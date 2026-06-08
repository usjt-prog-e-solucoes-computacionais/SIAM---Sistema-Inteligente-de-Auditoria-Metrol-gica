package br.com.siam.service;

import br.com.siam.dao.GasStationDAO;
import br.com.siam.dao.impl.GasStationDAOImpl;
import br.com.siam.model.GasStation;

import java.util.List;
import java.util.Optional;

public class GasStationService {

    private final GasStationDAO gasStationDAO;

    public GasStationService() {
        this.gasStationDAO = new GasStationDAOImpl();
    }

    public void createGasStation(GasStation gasStation) {

        validateGasStation(gasStation);

        gasStationDAO.save(gasStation);
    }

    public List<GasStation> getAllGasStations() {

        return gasStationDAO.findAll();
    }

    public GasStation getById(
            Integer id
    ) {

        return gasStationDAO
                .findById(id)
                .orElseThrow(
                        () -> new RuntimeException(
                                "Posto não encontrado."
                        )
                );
    }

    public List<GasStation> searchGasStations(String term) {

        return gasStationDAO.search(term);
    }

    private void validateGasStation(GasStation gasStation) {

        if (gasStation.getCorporateName() == null
                || gasStation.getCorporateName().isBlank()) {

            throw new IllegalArgumentException(
                    "Razão social é obrigatória."
            );
        }

        if (gasStation.getCnpj() == null
                || gasStation.getCnpj().isBlank()) {

            throw new IllegalArgumentException(
                    "CNPJ é obrigatório."
            );
        }

        if (gasStation.getAddress() == null
                || gasStation.getAddress().isBlank()) {

            throw new IllegalArgumentException(
                    "Endereço é obrigatório."
            );
        }
    }

    public boolean update(
            GasStation gasStation
    ) {

        return gasStationDAO.update(
                gasStation
        );
    }

    public void deactivate(
            Integer id
    ) {

        gasStationDAO.deactivate(id);
    }
}
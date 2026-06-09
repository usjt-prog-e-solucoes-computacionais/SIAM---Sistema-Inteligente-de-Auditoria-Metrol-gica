package br.com.siam.controller;

import br.com.siam.model.GasStation;
import br.com.siam.service.GasStationService;

import java.util.List;

public class GasStationController {

    private final GasStationService gasStationService;

    public GasStationController() {

        this.gasStationService =
                new GasStationService();
    }

    public void createGasStation(
            String cnpj,
            String corporateName,
            String address
    ) {

        GasStation gasStation =
                new GasStation();

        gasStation.setCnpj(cnpj);

        gasStation.setCorporateName(corporateName);

        gasStation.setAddress(address);

        gasStationService.createGasStation(gasStation);
    }

    public List<GasStation> findAllGasStations() {

        return gasStationService.getAllGasStations();
    }

    public List<GasStation> searchGasStations(String term) {

        return gasStationService.searchGasStations(term);
    }

    public GasStation getById(
            Integer id
    ) {

        return gasStationService.getById(id);
    }

    public boolean update(
            GasStation gasStation
    ) {

        return gasStationService.update(
                gasStation
        );
    }

    public void deactivate(
            Integer id
    ) {

        gasStationService.deactivate(id);
    }
}
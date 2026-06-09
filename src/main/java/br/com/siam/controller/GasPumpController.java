package br.com.siam.controller;

import br.com.siam.model.GasPump;
import br.com.siam.model.GasStation;
import br.com.siam.service.GasPumpService;

import java.util.List;

public class GasPumpController {

    private final GasPumpService gasPumpService;

    public GasPumpController() {

        this.gasPumpService =
                new GasPumpService();
    }

    public void createGasPump(
            GasStation gasStation,
            String serialNumber,
            String model
    ) {

        GasPump gasPump = new GasPump();

        gasPump.setGasStation(gasStation);
        gasPump.setSerialNumber(serialNumber);
        gasPump.setModel(model);

        gasPumpService.createGasPump(gasPump);
    }

    public List<GasPump> getAllGasPumps() {

        return gasPumpService.getAllGasPumps();
    }

    public List<GasPump> searchGasPumps(
            String term
    ) {

        return gasPumpService.searchGasPumps(term);
    }

    public List<GasPump> getByGasStation(
            Integer gasStationId
    ) {

        return gasPumpService.getByGasStation(
                gasStationId
        );
    }

    public GasPump getById(
            Integer id
    ) {

        return gasPumpService.getById(id);
    }

    public boolean update(
            GasPump gasPump
    ) {

        return gasPumpService.update(
                gasPump
        );
    }

    public void deactivate(
            Integer id
    ) {

        gasPumpService.deactivate(id);
    }

    public void reactivate(
            Integer id
    ) {

        gasPumpService.reactivate(id);
    }
}
package br.com.siam.dao;

import br.com.siam.model.GasStation;

import java.util.List;
import java.util.Optional;

public interface GasStationDAO {

    void save(GasStation gasStation);

    List<GasStation> findAll();

    Optional<GasStation> findById(Integer id);

    List<GasStation> search(String term);

    boolean update(GasStation gasStation);

    void deactivate(Integer gasStationId);
}
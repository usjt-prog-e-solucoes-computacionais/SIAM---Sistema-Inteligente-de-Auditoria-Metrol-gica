package br.com.siam.dao;

import br.com.siam.model.GasPump;

import java.util.List;
import java.util.Optional;

public interface GasPumpDAO {

    void save(GasPump gasPump);

    List<GasPump> findAll();

    List<GasPump> search(String term);

    List<GasPump> findByGasStation(Integer gasStationId);

    Optional<GasPump> findById(Integer id);

    boolean update(GasPump gasPump);

    void deactivate(Integer id);

    void reactivate(Integer id);
}
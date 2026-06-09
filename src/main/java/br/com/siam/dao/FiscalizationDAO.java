package br.com.siam.dao;

import br.com.siam.model.Fiscalization;

import java.util.List;
import java.util.Optional;

public interface FiscalizationDAO {

    void save(Fiscalization fiscalization);

    List<Fiscalization> findAll();

    List<Fiscalization> findByUser(Integer userId);

    List<Fiscalization> search(String term);

    Optional<Fiscalization> findById(Integer id);

    boolean update(Fiscalization fiscalization);

    void archive(Integer fiscalizationId);

    void reactivate(Integer fiscalizationId);
}
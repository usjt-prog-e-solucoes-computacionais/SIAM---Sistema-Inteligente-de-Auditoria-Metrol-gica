package br.com.siam.dao;

import br.com.siam.model.User;
import java.util.List;
import java.util.Optional;

public interface UserDAO {
    void insert(User user);

    Optional<User> findById(Integer id);

    Optional<User> findByLogin(String login);

    List<User> findAll();

    boolean update(User user);

    boolean deleteById(Integer id);
}

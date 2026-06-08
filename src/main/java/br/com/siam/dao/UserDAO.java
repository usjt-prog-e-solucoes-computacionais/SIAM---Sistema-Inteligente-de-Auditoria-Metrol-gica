package br.com.siam.dao;

import br.com.siam.model.User;
import java.util.List;
import java.util.Optional;

public interface UserDAO {

    Optional<User> findById(Integer id);

    Optional<User> findByEmailOrRegistration(String identifier);

    List<User> findAll();

    boolean updateProfile(
            Integer userId,
            String name,
            String passwordHash
    );

    void save(User user);

    void deactivate(Integer userId);
}

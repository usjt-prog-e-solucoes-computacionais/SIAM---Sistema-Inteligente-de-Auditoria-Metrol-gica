package br.com.siam.dao;

import br.com.siam.config.DatabaseConnection;
import br.com.siam.model.User;

import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface GenericDAO<T> {
    void insert(T object);

    Optional<T> findById(Integer id);

    List<T> findAll();

    boolean update(T object);

    boolean deleteById(Integer id);

    default void makeConnection(String sql, Function<Statement, Void> resultMapper) {

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            resultMapper.apply(statement);
        } catch (SQLException exception) {
            throw new RuntimeException("Error.", exception);
        }
    };

    default void persist(T object, String sql) {
        makeConnection(sql, statement -> null);
    };

    default List<T> read(Integer id, String sql) {
        makeConnection(sql, statement -> {

        });
    };
}

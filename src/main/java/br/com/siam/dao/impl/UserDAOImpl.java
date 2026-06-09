package br.com.siam.dao.impl;

import br.com.siam.config.DatabaseConnection;
import br.com.siam.dao.UserDAO;
import br.com.siam.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDAOImpl implements UserDAO {

    @Override
    public void save(User user) {
        String sql = "INSERT INTO user (name, login, registration, password_hash, user_type) VALUES (?, ?, ?, ?, ?)";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
                ) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getLogin());
            statement.setString(3, user.getRegistration());
            statement.setString(4, user.getPasswordHash());
            statement.setString(5, user.getUserType());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("No rows affected while inserting user.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException exception) {
            throw new RuntimeException("Error inserting user.", exception);
        }
    }

    @Override
    public Optional<User> findById(Integer id) {
        String sql = "SELECT id, name, login, registration, password_hash, user_type, active FROM user WHERE id = ?";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
                ) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapResultSetToUser(resultSet));
                }
            }
        } catch (SQLException exception) {
            throw new RuntimeException("Error finding user by id.", exception);
        }

        return Optional.empty();
    }

    @Override
    public Optional<User> findByEmailOrRegistration(String identifier) {
        String sql = "SELECT id, name, login, registration, password_hash, user_type, active FROM user WHERE login = ? OR registration = ?";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
                ) {
            statement.setString(1, identifier);
            statement.setString(2, identifier);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapResultSetToUser(resultSet));
                }
            }
        } catch (SQLException exception) {
            throw new RuntimeException("Error finding user by email or registration.", exception);
        }

        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT id, name, login, registration, password_hash, user_type, active FROM user";
        List<User> users = new ArrayList<>();

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
                ) {
            while (resultSet.next()) {
                users.add(mapResultSetToUser(resultSet));
            }
        } catch (SQLException exception) {
            throw new RuntimeException("Error finding all users.", exception);
        }
        return users;
    }

    @Override
    public boolean updateProfile(
            Integer userId,
            String name,
            String passwordHash
    ) {
        String sql = "UPDATE user SET name = ?, password_hash = ? WHERE id = ?";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
                ) {
            statement.setString(1, name);
            statement.setString(2, passwordHash);
            statement.setInt(3, userId);

            return statement.executeUpdate() > 0;

        } catch (SQLException exception) {
            throw new RuntimeException("Error updating user.", exception);
        }
    }

    @Override
    public void deactivate(Integer id) {
        String sql = "UPDATE user SET active = false WHERE id = ?";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
                ) {
            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException exception) {
            throw new RuntimeException("Erro deleting user.", exception);
        }
    }

    @Override
    public void reactivate(Integer id) {
        String sql = "UPDATE user SET active = true WHERE id = ?";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException exception) {
            throw new RuntimeException("Erro reativating user.", exception);
        }
    }

    private User mapResultSetToUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setName(resultSet.getString("name"));
        user.setLogin(resultSet.getString("login"));
        user.setRegistration(resultSet.getString("registration"));
        user.setPasswordHash(resultSet.getString("password_hash"));
        user.setUserType(resultSet.getString("user_type"));
        user.setActive(resultSet.getBoolean("active"));
        return user;
    }
}

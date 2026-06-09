package br.com.siam.dao.impl;

import br.com.siam.config.DatabaseConnection;
import br.com.siam.dao.GasStationDAO;
import br.com.siam.model.GasStation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GasStationDAOImpl implements GasStationDAO {

    private GasStation mapResult(
            ResultSet resultSet
    ) throws SQLException {

        GasStation gasStation =
                new GasStation();

        gasStation.setId(
                resultSet.getInt("id")
        );

        gasStation.setCnpj(
                resultSet.getString("cnpj")
        );

        gasStation.setCorporateName(
                resultSet.getString("corporate_name")
        );

        gasStation.setAddress(
                resultSet.getString("address")
        );

        gasStation.setActive(
                resultSet.getBoolean("active")
        );

        return gasStation;
    }

    @Override
    public void save(GasStation gasStation) {

        String sql = """
                INSERT INTO gas_station (
                    cnpj,
                    corporate_name,
                    address
                )
                VALUES (?, ?, ?)
                """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement preparedStatement =
                        connection.prepareStatement(sql)
        ) {

            preparedStatement.setString(
                    1,
                    gasStation.getCnpj()
            );

            preparedStatement.setString(
                    2,
                    gasStation.getCorporateName()
            );

            preparedStatement.setString(
                    3,
                    gasStation.getAddress()
            );

            preparedStatement.executeUpdate();

        } catch (SQLException exception) {

            exception.printStackTrace();
        }
    }

    @Override
    public List<GasStation> findAll() {

        List<GasStation> gasStations =
                new ArrayList<>();

        String sql = """
                SELECT *
                FROM gas_station
                ORDER BY corporate_name
                """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement preparedStatement =
                        connection.prepareStatement(sql);

                ResultSet resultSet =
                        preparedStatement.executeQuery()
        ) {

            while (resultSet.next()) {

                gasStations.add(
                        mapResult(resultSet)
                );
            }

        } catch (SQLException exception) {

            exception.printStackTrace();
        }

        return gasStations;
    }

    @Override
    public Optional<GasStation> findById(Integer id) {

        String sql = """
                SELECT *
                FROM gas_station
                WHERE id = ?
                """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement preparedStatement =
                        connection.prepareStatement(sql)
        ) {

            preparedStatement.setInt(1, id);

            try (
                    ResultSet resultSet =
                            preparedStatement.executeQuery()
            ) {

                if (resultSet.next()) {

                    return Optional.of(
                            mapResult(resultSet)
                    );
                }
            }

        } catch (SQLException exception) {

            exception.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public List<GasStation> search(String term) {

        List<GasStation> gasStations =
                new ArrayList<>();

        String sql = """
                SELECT *
                FROM gas_station
                WHERE corporate_name LIKE ?
                OR cnpj LIKE ?
                """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement preparedStatement =
                        connection.prepareStatement(sql)
        ) {

            String searchTerm = "%" + term + "%";

            preparedStatement.setString(1, searchTerm);

            preparedStatement.setString(2, searchTerm);

            try (
                    ResultSet resultSet =
                            preparedStatement.executeQuery()
            ) {

                while (resultSet.next()) {

                    gasStations.add(
                            mapResult(resultSet)
                    );
                }
            }

        } catch (SQLException exception) {

            exception.printStackTrace();
        }

        return gasStations;
    }

    @Override
    public boolean update(
            GasStation gasStation
    ) {

        String sql = """
        UPDATE gas_station
        SET
            cnpj = ?,
            corporate_name = ?,
            address = ?
        WHERE id = ?
        """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    gasStation.getCnpj()
            );

            statement.setString(
                    2,
                    gasStation.getCorporateName()
            );

            statement.setString(
                    3,
                    gasStation.getAddress()
            );

            statement.setInt(
                    4,
                    gasStation.getId()
            );

            return statement.executeUpdate() > 0;

        } catch (SQLException exception) {

            throw new RuntimeException(exception);
        }
    }

    @Override
    public void deactivate(
            Integer gasStationId
    ) {

        String deactivateStationSql = """
        UPDATE gas_station
        SET active = false
        WHERE id = ?
        """;

        String deactivatePumpsSql = """
        UPDATE gas_pump
        SET active = false
        WHERE gas_station_id = ?
        """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection()
        ) {

            connection.setAutoCommit(false);

            try (
                    PreparedStatement stationStatement =
                            connection.prepareStatement(
                                    deactivateStationSql
                            );

                    PreparedStatement pumpStatement =
                            connection.prepareStatement(
                                    deactivatePumpsSql
                            )
            ) {

                stationStatement.setInt(
                        1,
                        gasStationId
                );

                stationStatement.executeUpdate();

                pumpStatement.setInt(
                        1,
                        gasStationId
                );

                pumpStatement.executeUpdate();

                connection.commit();

            } catch (SQLException exception) {

                connection.rollback();

                throw exception;
            }

        } catch (SQLException exception) {

            throw new RuntimeException(
                    "Erro ao desativar posto.",
                    exception
            );
        }
    }

    @Override
    public void reactivate(
            Integer gasStationId
    ) {

        String reactivateStationSql = """
        UPDATE gas_station
        SET active = true
        WHERE id = ?
        """;

        String reactivatePumpsSql = """
        UPDATE gas_pump
        SET active = true
        WHERE gas_station_id = ?
        """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection()
        ) {

            connection.setAutoCommit(false);

            try (
                    PreparedStatement stationStatement =
                            connection.prepareStatement(
                                    reactivateStationSql
                            );

                    PreparedStatement pumpStatement =
                            connection.prepareStatement(
                                    reactivatePumpsSql
                            )
            ) {

                stationStatement.setInt(
                        1,
                        gasStationId
                );

                stationStatement.executeUpdate();

                pumpStatement.setInt(
                        1,
                        gasStationId
                );

                pumpStatement.executeUpdate();

                connection.commit();

            } catch (SQLException exception) {

                connection.rollback();

                throw exception;
            }

        } catch (SQLException exception) {

            throw new RuntimeException(
                    "Erro ao reativar posto.",
                    exception
            );
        }
    }
}
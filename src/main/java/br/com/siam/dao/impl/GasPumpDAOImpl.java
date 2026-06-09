package br.com.siam.dao.impl;

import br.com.siam.config.DatabaseConnection;
import br.com.siam.dao.GasPumpDAO;
import br.com.siam.model.GasPump;
import br.com.siam.model.GasStation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GasPumpDAOImpl implements GasPumpDAO {

    @Override
    public void save(GasPump gasPump) {

        String sql = """
                INSERT INTO gas_pump (
                    gas_station_id,
                    serial_number,
                    model
                )
                VALUES (?, ?, ?)
                """;

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(sql)
        ) {

            preparedStatement.setInt(
                    1,
                    gasPump.getGasStation().getId()
            );

            preparedStatement.setString(
                    2,
                    gasPump.getSerialNumber()
            );

            preparedStatement.setString(
                    3,
                    gasPump.getModel()
            );

            preparedStatement.executeUpdate();

        } catch (SQLException exception) {

            throw new RuntimeException(
                    "Erro ao salvar bomba.",
                    exception
            );
        }
    }

    @Override
    public List<GasPump> findAll() {

        List<GasPump> gasPumps = new ArrayList<>();

        String sql = """
                SELECT
                    gp.id,
                    gp.serial_number,
                    gp.model,
                    gs.id AS station_id,
                    gs.corporate_name
                FROM gas_pump gp
                INNER JOIN gas_station gs
                    ON gs.id = gp.gas_station_id
                ORDER BY gp.id DESC
                """;

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(sql);
                ResultSet resultSet =
                        preparedStatement.executeQuery()
        ) {

            while (resultSet.next()) {

                gasPumps.add(mapResult(resultSet));
            }

        } catch (SQLException exception) {

            throw new RuntimeException(exception);
        }

        return gasPumps;
    }

    @Override
    public List<GasPump> search(String term) {

        List<GasPump> gasPumps = new ArrayList<>();

        String sql = """
                SELECT
                    gp.id,
                    gp.serial_number,
                    gp.model,
                    gs.id AS station_id,
                    gs.corporate_name
                FROM gas_pump gp
                INNER JOIN gas_station gs
                    ON gs.id = gp.gas_station_id
                WHERE
                    gp.serial_number LIKE ?
                    OR gp.model LIKE ?
                    OR gs.corporate_name LIKE ?
                """;

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(sql)
        ) {

            String search = "%" + term + "%";

            preparedStatement.setString(1, search);
            preparedStatement.setString(2, search);
            preparedStatement.setString(3, search);

            try (
                    ResultSet resultSet =
                            preparedStatement.executeQuery()
            ) {

                while (resultSet.next()) {

                    gasPumps.add(mapResult(resultSet));
                }
            }

        } catch (SQLException exception) {

            throw new RuntimeException(exception);
        }

        return gasPumps;
    }

    @Override
    public List<GasPump> findByGasStation(Integer gasStationId) {

        List<GasPump> gasPumps = new ArrayList<>();

        String sql = """
                SELECT *
                FROM gas_pump
                WHERE gas_station_id = ?
                """;

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(sql)
        ) {

            preparedStatement.setInt(1, gasStationId);

            try (
                    ResultSet resultSet =
                            preparedStatement.executeQuery()
            ) {

                while (resultSet.next()) {

                    GasPump gasPump = new GasPump();

                    gasPump.setId(resultSet.getInt("id"));
                    gasPump.setSerialNumber(resultSet.getString("serial_number"));
                    gasPump.setModel(resultSet.getString("model"));

                    gasPumps.add(gasPump);
                }
            }

        } catch (SQLException exception) {

            throw new RuntimeException(exception);
        }

        return gasPumps;
    }

    private GasPump mapResult(ResultSet resultSet)
            throws SQLException {

        GasStation gasStation = new GasStation();

        gasStation.setId(
                resultSet.getInt("station_id")
        );

        gasStation.setCorporateName(
                resultSet.getString("corporate_name")
        );

        GasPump gasPump = new GasPump();

        gasPump.setId(
                resultSet.getInt("id")
        );

        gasPump.setSerialNumber(
                resultSet.getString("serial_number")
        );

        gasPump.setModel(
                resultSet.getString("model")
        );

        gasPump.setGasStation(gasStation);

        return gasPump;
    }

    @Override
    public Optional<GasPump> findById(Integer id) {

        String sql = """
            SELECT
                gp.id,
                gp.serial_number,
                gp.model,
                gs.id AS station_id,
                gs.corporate_name
            FROM gas_pump gp
            INNER JOIN gas_station gs
                ON gs.id = gp.gas_station_id
            WHERE gp.id = ?
            AND gp.active = true
            """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, id);

            try (
                    ResultSet resultSet =
                            statement.executeQuery()
            ) {

                if (resultSet.next()) {

                    return Optional.of(
                            mapResult(resultSet)
                    );
                }
            }

        } catch (SQLException exception) {

            throw new RuntimeException(exception);
        }

        return Optional.empty();
    }

    @Override
    public boolean update(
            GasPump gasPump
    ) {

        String sql = """
        UPDATE gas_pump
        SET
            serial_number = ?,
            model = ?
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
                    gasPump.getSerialNumber()
            );

            statement.setString(
                    2,
                    gasPump.getModel()
            );

            statement.setInt(
                    3,
                    gasPump.getId()
            );

            return statement.executeUpdate() > 0;

        } catch (SQLException exception) {

            throw new RuntimeException(exception);
        }
    }

    @Override
    public void deactivate(
            Integer id
    ) {

        String sql = """
        UPDATE gas_pump
        SET active = false
        WHERE id = ?
        """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, id);

            statement.executeUpdate();

        } catch (SQLException exception) {

            throw new RuntimeException(exception);
        }
    }
}
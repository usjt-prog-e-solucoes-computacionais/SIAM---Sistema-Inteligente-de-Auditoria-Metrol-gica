package br.com.siam.dao.impl;

import br.com.siam.config.DatabaseConnection;
import br.com.siam.dao.FiscalizationDAO;
import br.com.siam.model.Fiscalization;
import br.com.siam.model.GasPump;
import br.com.siam.model.GasStation;
import br.com.siam.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FiscalizationDAOImpl implements FiscalizationDAO {

    @Override
    public void save(Fiscalization fiscalization) {

        String sql = """
                INSERT INTO fiscalization
                (
                    user_id,
                    gas_pump_id,
                    fiscalization_date,
                    irregularity_type,
                    measurement_error,
                    audit_status
                )
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement preparedStatement =
                        connection.prepareStatement(sql)
        ) {

            preparedStatement.setInt(
                    1,
                    fiscalization.getUser().getId()
            );

            preparedStatement.setInt(
                    2,
                    fiscalization.getGasPump().getId()
            );

            preparedStatement.setTimestamp(
                    3,
                    Timestamp.valueOf(
                            fiscalization.getFiscalizationDate()
                    )
            );

            preparedStatement.setString(
                    4,
                    fiscalization.getIrregularityType()
            );

            preparedStatement.setBigDecimal(
                    5,
                    fiscalization.getMeasurementError()
            );

            preparedStatement.setString(
                    6,
                    fiscalization.getAuditStatus()
            );

            preparedStatement.executeUpdate();

        } catch (SQLException exception) {

            throw new RuntimeException(
                    "Erro ao salvar fiscalização.",
                    exception
            );
        }
    }

    @Override
    public List<Fiscalization> findAll() {

        List<Fiscalization> fiscalizations =
                new ArrayList<>();

        String sql = """
                SELECT
                    f.*,
                    u.name,
                    gp.serial_number,
                    gs.id AS station_id,
                    gs.corporate_name
                FROM fiscalization f
                INNER JOIN user u
                    ON u.id = f.user_id
                INNER JOIN gas_pump gp
                    ON gp.id = f.gas_pump_id
                INNER JOIN gas_station gs
                    ON gs.id = gp.gas_station_id
                WHERE f.active = true
                ORDER BY f.fiscalization_date DESC
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

                fiscalizations.add(
                        mapResult(resultSet)
                );
            }

        } catch (SQLException exception) {

            throw new RuntimeException(exception);
        }

        return fiscalizations;
    }

    @Override
    public List<Fiscalization> findAllWithInactive() {

        List<Fiscalization> fiscalizations =
                new ArrayList<>();

        String sql = """
                SELECT
                    f.*,
                    u.name,
                    gp.serial_number,
                    gs.id AS station_id,
                    gs.corporate_name
                FROM fiscalization f
                INNER JOIN user u
                    ON u.id = f.user_id
                INNER JOIN gas_pump gp
                    ON gp.id = f.gas_pump_id
                INNER JOIN gas_station gs
                    ON gs.id = gp.gas_station_id
                ORDER BY f.fiscalization_date DESC
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

                fiscalizations.add(
                        mapResult(resultSet)
                );
            }

        } catch (SQLException exception) {

            throw new RuntimeException(exception);
        }

        return fiscalizations;
    }

    @Override
    public List<Fiscalization> findByUser(Integer userId) {

        List<Fiscalization> fiscalizations =
                new ArrayList<>();

        String sql = """
                SELECT
                    f.*,
                    u.name,
                    gp.serial_number,
                    gs.id AS station_id,
                    gs.corporate_name
                FROM fiscalization f
                INNER JOIN user u
                    ON u.id = f.user_id
                INNER JOIN gas_pump gp
                    ON gp.id = f.gas_pump_id
                INNER JOIN gas_station gs
                    ON gs.id = gp.gas_station_id
                WHERE f.user_id = ? AND f.active = true
                ORDER BY f.fiscalization_date DESC
                """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement preparedStatement =
                        connection.prepareStatement(sql)
        ) {

            preparedStatement.setInt(1, userId);

            try (
                    ResultSet resultSet =
                            preparedStatement.executeQuery()
            ) {

                while (resultSet.next()) {

                    fiscalizations.add(
                            mapResult(resultSet)
                    );
                }
            }

        } catch (SQLException exception) {

            throw new RuntimeException(exception);
        }

        return fiscalizations;
    }

    @Override
    public List<Fiscalization> search(String term) {

        List<Fiscalization> fiscalizations =
                new ArrayList<>();

        String sql = """
                SELECT
                    f.*,
                    u.name,
                    gp.serial_number,
                    gs.id AS station_id,
                    gs.corporate_name
                FROM fiscalization f
                INNER JOIN user u
                    ON u.id = f.user_id
                INNER JOIN gas_pump gp
                    ON gp.id = f.gas_pump_id
                INNER JOIN gas_station gs
                    ON gs.id = gp.gas_station_id
                WHERE
                    gp.serial_number LIKE ?
                    OR f.irregularity_type LIKE ?
                """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement preparedStatement =
                        connection.prepareStatement(sql)
        ) {

            String search = "%" + term + "%";

            preparedStatement.setString(1, search);
            preparedStatement.setString(2, search);

            try (
                    ResultSet resultSet =
                            preparedStatement.executeQuery()
            ) {

                while (resultSet.next()) {

                    fiscalizations.add(
                            mapResult(resultSet)
                    );
                }
            }

        } catch (SQLException exception) {

            throw new RuntimeException(exception);
        }

        return fiscalizations;
    }

    private Fiscalization mapResult(ResultSet resultSet)
            throws SQLException {

        User user = new User();
        user.setId(resultSet.getInt("user_id"));
        user.setName(resultSet.getString("name"));

        GasStation gasStation = new GasStation();

        gasStation.setId(
                resultSet.getInt("station_id")
        );

        gasStation.setCorporateName(
                resultSet.getString("corporate_name")
        );

        GasPump gasPump = new GasPump();

        gasPump.setId(
                resultSet.getInt("gas_pump_id")
        );

        gasPump.setSerialNumber(
                resultSet.getString("serial_number")
        );

        gasPump.setGasStation(
                gasStation
        );

        Fiscalization fiscalization =
                new Fiscalization();

        fiscalization.setId(resultSet.getInt("id"));
        fiscalization.setUser(user);
        fiscalization.setGasPump(gasPump);

        fiscalization.setFiscalizationDate(
                resultSet.getTimestamp("fiscalization_date")
                        .toLocalDateTime()
        );

        fiscalization.setIrregularityType(
                resultSet.getString("irregularity_type")
        );

        fiscalization.setMeasurementError(
                resultSet.getBigDecimal("measurement_error")
        );

        fiscalization.setAuditStatus(
                resultSet.getString("audit_status")
        );


        fiscalization.setActive(
                resultSet.getBoolean("active")
        );

        return fiscalization;
    }

    @Override
    public Optional<Fiscalization> findById(Integer id) {

        String sql = """
         SELECT
             f.*,
             u.name,
             gp.serial_number,
             gs.id AS station_id,
             gs.corporate_name
         FROM fiscalization f
         INNER JOIN user u
            ON u.id = f.user_id
         INNER JOIN gas_pump gp
             ON gp.id = f.gas_pump_id
         INNER JOIN gas_station gs
             ON gs.id = gp.gas_station_id
         WHERE f.id = ?
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
            Fiscalization fiscalization
    ) {

        String sql = """
        UPDATE fiscalization
        SET
            irregularity_type = ?,
            measurement_error = ?,
            audit_status = ?
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
                    fiscalization.getIrregularityType()
            );

            statement.setBigDecimal(
                    2,
                    fiscalization.getMeasurementError()
            );

            statement.setString(
                    3,
                    fiscalization.getAuditStatus()
            );

            statement.setInt(
                    4,
                    fiscalization.getId()
            );

            return statement.executeUpdate() > 0;

        } catch (SQLException exception) {

            throw new RuntimeException(exception);
        }
    }

    @Override
    public void archive(
            Integer fiscalizationId
    ) {

        String sql = """
        UPDATE fiscalization
        SET
            active = false,
            archived_at = NOW()
        WHERE id = ?
        """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    fiscalizationId
            );

            statement.executeUpdate();

        } catch (SQLException exception) {

            throw new RuntimeException(exception);
        }
    }

    @Override
    public void reactivate(
            Integer fiscalizationId
    ) {

        String sql = """
        UPDATE fiscalization
        SET active = true
        WHERE id = ?
        """;

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    fiscalizationId
            );

            statement.executeUpdate();

        } catch (SQLException exception) {

            throw new RuntimeException(
                    exception
            );
        }
    }
}
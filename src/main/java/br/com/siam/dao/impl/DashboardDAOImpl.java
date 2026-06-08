package br.com.siam.dao.impl;

import br.com.siam.config.DatabaseConnection;
import br.com.siam.dao.DashboardDAO;
import br.com.siam.dto.DashboardDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DashboardDAOImpl implements DashboardDAO {

    @Override
    public DashboardDTO getIndicators() {

        DashboardDTO dashboardDTO =
                new DashboardDTO();

        try (
                Connection connection =
                        DatabaseConnection.getConnection()
        ) {

            dashboardDTO.setTotalFiscalizations(
                    getCount(
                            connection,
                            "SELECT COUNT(*) FROM fiscalization"
                    )
            );

            dashboardDTO.setTotalGasStations(
                    getCount(
                            connection,
                            "SELECT COUNT(*) FROM gas_station WHERE active = true"
                    )
            );

            dashboardDTO.setTotalGasPumps(
                    getCount(
                            connection,
                            "SELECT COUNT(*) FROM gas_pump WHERE active = true"
                    )
            );

            dashboardDTO.setPendingFiscalizations(
                    getCount(
                            connection,
                            """
                            SELECT COUNT(*)
                            FROM fiscalization
                            WHERE audit_status = 'PENDENTE'
                            """
                    )
            );

            dashboardDTO.setCompletedFiscalizations(
                    getCount(
                            connection,
                            """
                            SELECT COUNT(*)
                            FROM fiscalization
                            WHERE audit_status = 'CONCLUIDA'
                            """
                    )
            );

            dashboardDTO.setArchivedFiscalizations(
                    getCount(
                            connection,
                            """
                            SELECT COUNT(*)
                            FROM fiscalization
                            WHERE active = false
                            """
                    )
            );

        } catch (SQLException exception) {

            throw new RuntimeException(
                    "Erro ao carregar dashboard.",
                    exception
            );
        }

        return dashboardDTO;
    }

    private Integer getCount(
            Connection connection,
            String sql
    ) throws SQLException {

        try (
                PreparedStatement statement =
                        connection.prepareStatement(sql);

                ResultSet resultSet =
                        statement.executeQuery()
        ) {

            if (resultSet.next()) {

                return resultSet.getInt(1);
            }
        }

        return 0;
    }
}
package br.com.siam.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Classe pública criada para realizar a conexão entre o aplicativo e o banco de dados
public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/siam_db";
    private static final String USER = "root";
    private static final String PASSWORD = "desanimaeducacao";

    // Conexão ao banco de dados final, via VPN
    // private static final String URL = "jdbc:mysql://100.75.195.66:3306/siam_db";
    // private static final String USER = "siam_app";
    // private static final String PASSWORD = "siam123";

    // Método para conexão do banco de dados
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

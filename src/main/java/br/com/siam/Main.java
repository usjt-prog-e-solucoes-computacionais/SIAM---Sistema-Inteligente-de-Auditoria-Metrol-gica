package br.com.siam;

import br.com.siam.config.DatabaseConnection;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {

        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn != null) {
                System.out.println("✅ Conectado com sucesso ao MySQL!");
            }
        } catch (Exception e) {
            System.out.println("❌ Erro ao conectar:");
            e.printStackTrace();
        }

    }
}
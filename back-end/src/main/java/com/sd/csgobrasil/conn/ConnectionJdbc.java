package com.sd.csgobrasil.conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionJdbc {

    private static Connection conn;

    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/csgo";
        String username = "root";
        String password = "Kamikaze10*";
        conn = DriverManager.getConnection(url, username, password);
        return conn;
    }

    public static void desconectar() {
        try {
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao fechar conexão com o banco de dados", e);
        }
    }
}

package br.ita.coursera.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Classe utilitária para gerenciar conexões com o banco de dados.
 */
public class DatabaseConnection {
    
    private static String url;
    private static String username;
    private static String password;
    
    static {
        loadProperties();
    }
    
    /**
     * Carrega as propriedades de conexão do arquivo database.properties.
     */
    private static void loadProperties() {
        Properties props = new Properties();
        try (InputStream input = DatabaseConnection.class.getClassLoader()
                .getResourceAsStream("database.properties")) {
            if (input != null) {
                props.load(input);
                url = props.getProperty("db.url");
                username = props.getProperty("db.username");
                password = props.getProperty("db.password");
            } else {
                // Valores padrão caso o arquivo não seja encontrado
                url = "jdbc:postgresql://localhost:5432/coursera";
                username = "postgres";
                password = "postgres";
            }
        } catch (IOException e) {
            // Valores padrão em caso de erro
            url = "jdbc:postgresql://localhost:5432/coursera";
            username = "postgres";
            password = "postgres";
        }
    }
    
    /**
     * Obtém uma nova conexão com o banco de dados.
     * @return Connection objeto de conexão
     * @throws SQLException se houver erro ao conectar
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
    
    /**
     * Fecha a conexão com o banco de dados.
     * @param connection A conexão a ser fechada
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

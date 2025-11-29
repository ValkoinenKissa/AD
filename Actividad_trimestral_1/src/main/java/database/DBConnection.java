package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static Connection connection;

    public static Connection getConnection(){
        if (connection == null){
            createConnection();
        }
        return connection;
    }

    private static void createConnection(){
        String user = "root";
        String passwd = "root";
        String url = "jdbc:mysql://localhost:3306/almacen?allowPublicKeyRetrieval=true&useSSL=false";
        try {
            connection = DriverManager.getConnection(url, user, passwd);
        } catch (SQLException e) {
            System.out.println("Fallo en la conexión de la BBDD");
        }
    }
}

package Clases.ConexionBD;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionMySQL {
    private Connection conexion = null;
    private static ConexionMySQL instancia = null;

    public ConexionMySQL() {
        String cadenaDeConexion = "jdbc:mysql://localhost:3306/bdcolegio?useSSL=false&serverTimezone=UTC";
        String usuario = "root";
        String contraseña = "123456";

        try {
            conexion = DriverManager.getConnection(cadenaDeConexion, usuario, contraseña);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public static ConexionMySQL getInstancia() {
        if (instancia == null) {
            instancia = new ConexionMySQL();
        }
        return instancia;
    }

    public Connection getConexion() {
        return conexion;
    }
}

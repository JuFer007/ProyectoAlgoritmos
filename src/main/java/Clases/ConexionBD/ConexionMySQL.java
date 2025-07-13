package Clases.ConexionBD;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionMySQL {
    private static final String CADENA_CONEXION = "jdbc:mysql://localhost:3306/bdcolegio?useSSL=false&serverTimezone=UTC";
    private static final String USUARIO = "root";
    private static final String CONTRASEÑA = "123456";

    private static ConexionMySQL instancia = null;

    private ConexionMySQL() {
    }

    public static ConexionMySQL getInstancia() {
        if (instancia == null) {
            instancia = new ConexionMySQL();
        }
        return instancia;
    }

    public Connection getConexion() {
        try {
            return DriverManager.getConnection(CADENA_CONEXION, USUARIO, CONTRASEÑA);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}

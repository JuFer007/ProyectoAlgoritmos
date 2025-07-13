package Clases.ConexionBD.Entidades_CRUD;
import Clases.ConexionBD.ConexionMySQL;
import java.sql.*;

public class DAO_Usuario {
    public boolean verificarUsuarioYContraseña(String usuario, String contraseña) {
        String consulta = """
                        SELECT 1 
                        FROM Usuario 
                        WHERE nombreUsuario = ? 
                        AND contraseñaUsuario = ?
                        AND rolUsuario = 'Administrador' 
                        """;

        try (Connection conn = ConexionMySQL.getInstancia().getConexion();
             PreparedStatement stmt = conn.prepareStatement(consulta)) {

            stmt.setString(1, usuario);
            stmt.setString(2, contraseña);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

}
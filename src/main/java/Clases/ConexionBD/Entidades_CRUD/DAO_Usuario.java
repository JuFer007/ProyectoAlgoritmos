package Clases.ConexionBD.Entidades_CRUD;
import Clases.ConexionBD.ConexionMySQL;
import java.sql.*;

public class DAO_Usuario {
    public boolean verificarUsuarioYContraseña(String usuario, String contraseña) {
        String consulta = "SELECT 1 FROM Usuario WHERE nombreUsuario = ? AND contraseñaUsuario = ?";

        try (Connection conn = ConexionMySQL.getInstancia().getConexion();
             PreparedStatement stmt = conn.prepareStatement(consulta)) {

            stmt.setString(1, usuario);
            stmt.setString(2, contraseña);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}
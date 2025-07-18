package Clases.ConexionBD.Entidades_DAO;
import Clases.ClasesPersonas.SesionUsuario;
import Clases.ConexionBD.ConexionMySQL;
import java.sql.*;

public class DAO_Usuario {
    public boolean verificarUsuarioYContraseña(String usuario, String contraseña) {
        String consulta = """
                        SELECT 1 
                        FROM Usuario 
                        WHERE nombreUsuario = ? 
                        AND contraseñaUsuario = ?
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

    public void cargarDatosSesion(String usuario) {
        String consulta = """
            SELECT 
                u.idUsuario,
                u.nombreUsuario,
                u.rolUsuario,
                t.codigoTrabajador
            FROM Usuario u
            LEFT JOIN Trabajador t ON u.idPersona = t.idPersona
            WHERE u.nombreUsuario = ?
        """;

        try (Connection conn = ConexionMySQL.getInstancia().getConexion();
             PreparedStatement stmt = conn.prepareStatement(consulta)) {

            stmt.setString(1, usuario);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    SesionUsuario sesion = SesionUsuario.getInstancia();
                    sesion.setIdUsuario(rs.getInt("idUsuario"));
                    sesion.setNombreUsuario(rs.getString("nombreUsuario"));
                    sesion.setRolUsuario(rs.getString("rolUsuario"));
                    sesion.setCodigoTrabajador(rs.getString("codigoTrabajador"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String obtenerRolUsuario(String usuario) {
        String consulta = """
            SELECT u.rolUsuario
            FROM Usuario u
            LEFT JOIN Trabajador t ON u.idPersona = t.idPersona
            WHERE u.nombreUsuario = ?
        """;

        try (Connection conn = ConexionMySQL.getInstancia().getConexion();
             PreparedStatement stmt = conn.prepareStatement(consulta)) {

            stmt.setString(1, usuario);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    SesionUsuario sesion = SesionUsuario.getInstancia();
                    sesion.setRolUsuario(rs.getString("rolUsuario"));
                }
                return rs.getString("rolUsuario");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
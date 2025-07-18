package Clases.ConexionBD.Entidades_DAO;
import Clases.ClasesPersonas.Apoderado;
import Clases.ConexionBD.ConexionMySQL;
import javafx.scene.control.Alert;
import java.sql.*;
import java.util.ArrayList;

public class DAO_Apoderado {
    private ArrayList<Apoderado> apoderados;

    //Metodo para agregar apoderado
    public void Crear(Apoderado apoderado) {
        String mensaje = "";
        String consulta = "{CALL sp_Apoderado_Insert(?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)}";

        try {
            CallableStatement statement = ConexionMySQL.getInstancia().getConexion().prepareCall(consulta);
            statement.setString(1, apoderado.getDnipersona());
            statement.setString(2, apoderado.getPrimernombre());
            statement.setString(3, apoderado.getSegundonombre());
            statement.setString(4, apoderado.getApellidopaterno());
            statement.setString(5, apoderado.getApellidomaterno());
            statement.setDate(6, (Date) apoderado.getFechanacimiento());
            statement.setString(7, apoderado.getGenero());
            statement.setString(8, apoderado.getCorreoElectronico());
            statement.setString(9, apoderado.getNumeroTelefono());
            statement.setString(10, apoderado.getParentesco_relacion());

            statement.registerOutParameter(11, Types.VARCHAR);
            statement.executeUpdate();

            mensaje = statement.getString(11);
            statement.close();

            mostrarMensaje(mensaje);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Metodo para actualizar apoderado
    public void actualizarApoderado(String dniPersona, String primerNombre, String segundoNombre,
                                    String apellidoPaterno, String apellidoMaterno, Date fechaNacimiento,
                                    String genero, String telefono, String correoElectronico,
                                    String parentesco) {
        String sql = "{CALL ActualizarDatosPersonaYApoderado(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";

        try (Connection con = ConexionMySQL.getInstancia().getConexion();
             CallableStatement stmt = con.prepareCall(sql)) {

            stmt.setString(1, dniPersona);
            stmt.setString(2, primerNombre);
            stmt.setString(3, segundoNombre);
            stmt.setString(4, apellidoPaterno);
            stmt.setString(5, apellidoMaterno);
            stmt.setDate(6, fechaNacimiento);
            stmt.setString(7, genero);
            stmt.setString(8, telefono);
            stmt.setString(9, correoElectronico);
            stmt.setString(10, parentesco);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Metodo para listar apoderados
    public void Listar() {
        apoderados = new ArrayList<>();
        apoderados.clear();

        String consulta = "SELECT persona.DNIpersona, persona.primerNombre, persona.segundoNombre, persona.apellidoMaterno, persona.apellidoPaterno, persona.fechaNacimiento, persona.genero, "
                + "apoderado.correoElectronico, apoderado.numeroTelefono, apoderado.parentesco_relacion "
                + "FROM persona INNER JOIN Apoderado ON persona.idPersona = apoderado.idPersona "
                + "ORDER BY persona.apellidoPaterno";

        try (PreparedStatement comando = ConexionMySQL.getInstancia().getConexion().prepareStatement(consulta);
             ResultSet resultado = comando.executeQuery()) {

            while (resultado.next()) {
                Apoderado apoderado = new Apoderado(
                        resultado.getString("DNIpersona"),
                        resultado.getString("primerNombre"),
                        resultado.getString("segundoNombre"),
                        resultado.getString("apellidoPaterno"),
                        resultado.getString("apellidoMaterno"),
                        resultado.getDate("fechaNacimiento"),
                        resultado.getString("genero"),
                        resultado.getString("correoElectronico"),
                        resultado.getString("numeroTelefono"),
                        resultado.getString("parentesco_relacion")
                );
                apoderados.add(apoderado);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //Buscar apoderado por idApoderado
    public Apoderado buscarPorId(int idApoderado) {
        Apoderado apo = null;

        String sql = """
                        SELECT 
                            a.idApoderado,
                            a.correoElectronico,
                            a.numeroTelefono,
                            a.parentesco_relacion,
                            p.DNIpersona,
                            p.primerNombre,
                            p.segundoNombre,
                            p.apellidoPaterno,
                            p.apellidoMaterno,
                            p.fechaNacimiento,
                            p.genero
                        FROM Apoderado a
                        INNER JOIN Persona p ON a.idPersona = p.idPersona
                        WHERE a.idApoderado = ?
                    """;

        try (Connection conn = ConexionMySQL.getInstancia().getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, idApoderado);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        apo = new Apoderado(
                                rs.getString("DNIpersona"),
                                rs.getString("primerNombre"),
                                rs.getString("segundoNombre"),
                                rs.getString("apellidoPaterno"),
                                rs.getString("apellidoMaterno"),
                                rs.getDate("fechaNacimiento"),
                                rs.getString("genero"),
                                rs.getString("correoElectronico"),
                                rs.getString("numeroTelefono"),
                                rs.getString("parentesco_relacion")
                        );

                        apo.setIdApoderado(rs.getInt("idApoderado"));
                    }
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return apo;
    }


    private void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Mensaje de información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public ArrayList<Apoderado> getApoderados() {
        return apoderados;
    }

    //Obtener datos de apoderado
    public String[] obtenerDatosApoderado(String dniPersona) {
        String sql = "SELECT "
                + "p.DNIpersona, p.primerNombre, p.segundoNombre, p.apellidoPaterno, p.apellidoMaterno, p.fechaNacimiento, p.genero, "
                + "a.numeroTelefono, a.correoElectronico, a.parentesco_relacion "
                + "FROM Persona p "
                + "LEFT JOIN Apoderado a ON p.idPersona = a.idPersona "
                + "WHERE p.DNIpersona = ?";

        String[] datosPersonaYApoderado = new String[10];

        try (Connection con = ConexionMySQL.getInstancia().getConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, dniPersona);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    datosPersonaYApoderado[0] = rs.getString("DNIpersona");
                    datosPersonaYApoderado[1] = rs.getString("primerNombre");
                    datosPersonaYApoderado[2] = rs.getString("segundoNombre");
                    datosPersonaYApoderado[3] = rs.getString("apellidoPaterno");
                    datosPersonaYApoderado[4] = rs.getString("apellidoMaterno");
                    datosPersonaYApoderado[5] = rs.getString("fechaNacimiento");
                    datosPersonaYApoderado[6] = rs.getString("genero");
                    datosPersonaYApoderado[7] = rs.getString("numeroTelefono");
                    datosPersonaYApoderado[8] = rs.getString("correoElectronico");
                    datosPersonaYApoderado[9] = rs.getString("parentesco_relacion");
                } else {
                    datosPersonaYApoderado = new String[]{"No encontrado", "", "", "", "", "", "", "", "", ""};
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return datosPersonaYApoderado;
    }
}

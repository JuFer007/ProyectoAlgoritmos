package Clases.ConexionBD.Entidades_CRUD;
import Clases.ClasesPersonas.Trabajador;
import Clases.ConexionBD.ConexionMySQL;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;

public class DAO_Trabajador {
    private ArrayList<Trabajador> trabajadores = new ArrayList();

    //Metodo para crear trabajador
    public void Crear(Trabajador trabajador) {
        String mensaje = "";
        String consulta = "{CALL sp_Trabajador_Insert(?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)}";

        try {
            CallableStatement statement = ConexionMySQL.getInstancia().getConexion().prepareCall(consulta);
            statement.setString(1, trabajador.getDnipersona());
            statement.setString(2, trabajador.getPrimernombre());
            statement.setString(3, trabajador.getSegundonombre());
            statement.setString(4, trabajador.getApellidopaterno());
            statement.setString(5, trabajador.getApellidomaterno());
            statement.setDate(6, (Date) trabajador.getFechanacimiento());
            statement.setString(7, trabajador.getGenero());
            statement.setString(8, trabajador.getTipoTrabajador());
            statement.setString(9, trabajador.getTurnoAsignado());
            statement.setString(10, trabajador.getCargo());

            statement.registerOutParameter(11, Types.VARCHAR);
            statement.executeUpdate();

            mensaje = statement.getString(11);
            statement.close();

            mostrarMensaje(mensaje);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Metodo para actualizar trabajador
    public void actualizarDatosTrabajador(String DNI, String primerNombre, String segundoNombre,
                                          String apellidoPaterno, String apellidoMaterno, Date fechaNacimiento,
                                          String genero, String tipoTrabajo, String turnoAsignado, String cargo) {

        String sql = "{CALL ActualizarPersonaTrabajador(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";

        try (Connection con = ConexionMySQL.getInstancia().getConexion();
             CallableStatement stmt = con.prepareCall(sql)) {

            stmt.setString(1, DNI);
            stmt.setString(2, primerNombre);
            stmt.setString(3, segundoNombre);
            stmt.setString(4, apellidoPaterno);
            stmt.setString(5, apellidoMaterno);
            stmt.setDate(6, fechaNacimiento);
            stmt.setString(7, genero);
            stmt.setString(8, tipoTrabajo);
            stmt.setString(9, turnoAsignado);
            stmt.setString(10, cargo);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Metodo para listar trabajador
    public void Listar() {
        trabajadores = new ArrayList<>();
        trabajadores.clear();

        String consulta = "SELECT persona.DNIpersona, persona.primerNombre, persona.segundoNombre, persona.apellidoPaterno, persona.apellidoMaterno, persona.fechaNacimiento, persona.genero, " +
                "trabajador.codigoTrabajador, trabajador.tipoTrabajo, trabajador.turnoAsignado, trabajador.cargo " +
                "FROM persona INNER JOIN trabajador ON persona.idPersona = trabajador.idPersona " +
                "ORDER BY persona.apellidoPaterno";

        try {
            PreparedStatement comando = ConexionMySQL.getInstancia().getConexion().prepareStatement(consulta);
            ResultSet resultado = comando.executeQuery();

            while (resultado.next()) {
                Trabajador trabajador = new Trabajador(
                        resultado.getString("DNIpersona"),
                        resultado.getString("primerNombre"),
                        resultado.getString("segundoNombre"),
                        resultado.getString("apellidoPaterno"),
                        resultado.getString("apellidoMaterno"),
                        resultado.getDate("fechaNacimiento"),
                        resultado.getString("genero"),
                        resultado.getString("tipoTrabajo"),
                        resultado.getString("turnoAsignado"),
                        resultado.getString("cargo")
                );

                trabajador.setCodigoTrabajador(resultado.getString("codigoTrabajador"));

                trabajadores.add(trabajador);
            }

            resultado.close();
            comando.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Metodo para Buscar trabajador por codigo
    public Trabajador buscarPorCodigo(String codigoTrabajador) {
        Trabajador trabajador = null;

        String sql = """
        SELECT
            t.idTrabajador,
            t.codigoTrabajador,
            t.idpersona,
            t.tipoTrabajo,
            t.turnoAsignado,
            t.cargo,
            p.DNIpersona,
            p.primerNombre,
            p.segundoNombre,
            p.apellidoPaterno,
            p.apellidoMaterno,
            p.fechaNacimiento,
            p.genero
        FROM Trabajador t
        INNER JOIN Persona p ON t.idPersona = p.idPersona
        WHERE t.codigoTrabajador = ? AND tipoTrabajo = "Administrativo"
    """;

        try (Connection conn = ConexionMySQL.getInstancia().getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, codigoTrabajador);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    trabajador = new Trabajador(
                            rs.getString("DNIpersona"),
                            rs.getString("primerNombre"),
                            rs.getString("segundoNombre"),
                            rs.getString("apellidoPaterno"),
                            rs.getString("apellidoMaterno"),
                            rs.getDate("fechaNacimiento"),
                            rs.getString("genero"),
                            rs.getString("tipoTrabajo"),
                            rs.getString("turnoAsignado"),
                            rs.getString("cargo")
                    );

                    trabajador.setIdTrabajador(rs.getInt("idTrabajador"));
                    trabajador.setCodigoTrabajador(rs.getString("codigoTrabajador"));
                    trabajador.setIdPersona(rs.getInt("idPersona"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return trabajador;
    }


    //Metodo para mostrar mensaje
    private void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Mensaje de información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    //Retornar los trabajadores
    public ArrayList<Trabajador> getTrabajadores() {
        return trabajadores;
    }

    //Obtener los datos del trabajador
    public String[] obtenerDatosTrabajador(String dniPersona) {
        String sql = "SELECT "
                + "p.DNIpersona, p.primerNombre, p.segundoNombre, p.apellidoPaterno, p.apellidoMaterno, p.fechaNacimiento, p.genero, "
                + "t.codigoTrabajador, t.tipoTrabajo, t.turnoAsignado, t.Cargo "
                + "FROM Persona p "
                + "LEFT JOIN Trabajador t ON p.idPersona = t.idPersona "
                + "WHERE p.DNIpersona = ?";

        String[] datosPersonaTrabajador = new String[11];

        try (Connection con = ConexionMySQL.getInstancia().getConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, dniPersona);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    datosPersonaTrabajador[0] = rs.getString("DNIpersona");
                    datosPersonaTrabajador[1] = rs.getString("primerNombre");
                    datosPersonaTrabajador[2] = rs.getString("segundoNombre");
                    datosPersonaTrabajador[3] = rs.getString("apellidoPaterno");
                    datosPersonaTrabajador[4] = rs.getString("apellidoMaterno");
                    datosPersonaTrabajador[5] = rs.getString("fechaNacimiento");
                    datosPersonaTrabajador[6] = rs.getString("genero");
                    datosPersonaTrabajador[7] = rs.getString("codigoTrabajador");
                    datosPersonaTrabajador[8] = rs.getString("tipoTrabajo");
                    datosPersonaTrabajador[9] = rs.getString("turnoAsignado");
                    datosPersonaTrabajador[10] = rs.getString("Cargo");
                } else {
                    datosPersonaTrabajador = new String[]{"No encontrado", "", "", "", "", "", "", "", "", "", ""};
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return datosPersonaTrabajador;
    }
}

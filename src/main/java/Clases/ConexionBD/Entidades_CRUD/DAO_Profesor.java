package Clases.ConexionBD.Entidades_CRUD;
import Clases.ClasesPersonas.Profesor;
import Clases.ConexionBD.ConexionMySQL;
import javafx.scene.control.Alert;
import java.sql.*;
import java.util.ArrayList;

public class DAO_Profesor {
    private ArrayList<Profesor> profesores;

    //Metodo para crear profesores
    public void Crear(Profesor profesor) {
        String mensaje = "";
        String consulta = "{CALL sp_Profesor_Insert(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)}";

        try {
            CallableStatement statement = ConexionMySQL.getInstancia().getConexion().prepareCall(consulta);

            statement.setString(1, profesor.getDnipersona());
            statement.setString(2, profesor.getPrimernombre());
            statement.setString(3, profesor.getSegundonombre());
            statement.setString(4, profesor.getApellidopaterno());
            statement.setString(5, profesor.getApellidomaterno());
            statement.setDate(6, (Date) profesor.getFechanacimiento());
            statement.setString(7, profesor.getGenero());
            statement.setString(8, profesor.getEspecialidad());
            statement.setString(9, profesor.getGradoAcademico());
            statement.setInt(10, profesor.getHorasSemanales());
            statement.setString(11, profesor.getCorreoElectronico());
            statement.setString(12, profesor.getTelefono());

            statement.registerOutParameter(13, Types.VARCHAR);
            statement.executeUpdate();

            mensaje = statement.getString(13);
            statement.close();

            mostrarMensaje(mensaje);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //Metodo para actualizar profesores
    public void Actualizar(String primerNombre, String segundoNombre, String apellidoPaterno,
                           String apellidoMaterno, String genero, String especialidad,
                           String gradoAcademico, int horasSemanales, String correoElectronico,
                           String telefono, String dniPersona) {
        String sql = "{CALL ActualizarProfesor(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";

        try (Connection con = ConexionMySQL.getInstancia().getConexion();
             CallableStatement stmt = con.prepareCall(sql)) {

            stmt.setString(1, dniPersona);
            stmt.setString(2, primerNombre);
            stmt.setString(3, segundoNombre);
            stmt.setString(4, apellidoPaterno);
            stmt.setString(5, apellidoMaterno);
            stmt.setString(6, genero);
            stmt.setString(7, especialidad);
            stmt.setString(8, gradoAcademico);
            stmt.setInt(9, horasSemanales);
            stmt.setString(10, correoElectronico);
            stmt.setString(11, telefono);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Metodo para listar profesores
    public void Listar() {
        profesores = new ArrayList();
        profesores.clear();

        String consulta = "select persona.DNIpersona, persona.primerNombre, persona.segundoNombre, persona.apellidoMaterno, persona.apellidoPaterno, persona.fechaNacimiento, persona.genero,\n" +
                "profesor.codigoProfesor, profesor.especialidad, profesor.idPersona, profesor.gradoAcademico, profesor.horasSemanales, profesor.correoElectronico, profesor.telefono\n" +
                "from persona inner join profesor on persona.idPersona = profesor.idPersona\n" +
                "order by persona.apellidoPaterno";

        try {
            PreparedStatement comando = ConexionMySQL.getInstancia().getConexion().prepareStatement(consulta);
            ResultSet resultado = comando.executeQuery();
            while (resultado.next()) {
                Profesor profesor = new Profesor(
                        resultado.getString("DNIpersona"),
                        resultado.getString("primerNombre"),
                        resultado.getString("segundoNombre"),
                        resultado.getString("apellidoPaterno"),
                        resultado.getString("apellidoMaterno"),
                        resultado.getDate("fechaNacimiento"),
                        resultado.getString("genero"),
                        resultado.getString("codigoProfesor"),
                        resultado.getString("especialidad"),
                        resultado.getString("gradoAcademico"),
                        resultado.getInt("horasSemanales"),
                        resultado.getString("correoElectronico"),
                        resultado.getString("telefono")
                );
                profesores.add(profesor);
            }
            resultado.close();
            comando.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Profesor> getProfesores() {
        return profesores;
    }

    //Metodo para mostrar mensaje
    private void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Mensaje de información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    //Metodo para listar datos de un profesor
    public String[] obtenerDatosProfesor(String DNI) {
        String sql = "SELECT p.DNIpersona, p.primerNombre, p.segundoNombre, p.apellidoPaterno, p.apellidoMaterno, p.fechaNacimiento, p.genero,\n" +
                "pro.especialidad, pro.gradoAcademico, pro.horasSemanales, pro.correoElectronico, pro.Telefono, pro.codigoProfesor \n" +
                "FROM Persona p \n" +
                "inner JOIN Profesor as pro ON p.idPersona = pro.idPersona \n" +
                "WHERE p.DNIpersona = ?";

        String[] datosProfesor = new String[13];

        try (Connection con = ConexionMySQL.getInstancia().getConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, DNI);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    datosProfesor[0] = rs.getString("DNIpersona");
                    datosProfesor[1] = rs.getString("primerNombre");
                    datosProfesor[2] = rs.getString("segundoNombre");
                    datosProfesor[3] = rs.getString("apellidoPaterno");
                    datosProfesor[4] = rs.getString("apellidoMaterno");
                    datosProfesor[5] = rs.getString("fechaNacimiento");
                    datosProfesor[6] = rs.getString("genero");
                    datosProfesor[7] = rs.getString("especialidad");
                    datosProfesor[8] = rs.getString("gradoAcademico");
                    datosProfesor[9] = rs.getString("horasSemanales");
                    datosProfesor[10] = rs.getString("correoElectronico");
                    datosProfesor[11] = rs.getString("Telefono");
                    datosProfesor[12] = rs.getString("codigoProfesor");
                } else {
                    datosProfesor = new String[]{"No encontrado", "", "", "", "", "", "", "", "", "", ""};
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return datosProfesor;
    }

    //Metodo para verificar el DNI del profesor
    public boolean validarDniProfesor(String dni) {
        String sql = "SELECT p.DNIpersona " +
                "FROM Persona p " +
                "INNER JOIN Profesor prof ON p.idPersona = prof.idPersona " +
                "WHERE p.DNIpersona = ?";

        try (Connection con = ConexionMySQL.getInstancia().getConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, dni);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
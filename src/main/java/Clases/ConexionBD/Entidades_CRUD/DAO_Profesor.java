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
    public void Actualizar(Profesor profesor) {
        String mensaje = "";
        String consulta = "{CALL sp_Profesor_Update(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";

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

    //Metodo para eliminar profesor
    public void Eliminar(String DNIprofesor) {
        String mensaje = "";
        String consulta = "{CALL sp_Profesor_Delete(?, ?)}";

        try {
            CallableStatement statement = ConexionMySQL.getInstancia().getConexion().prepareCall(consulta);
            statement.setString(1, DNIprofesor);

            statement.registerOutParameter(2, Types.VARCHAR);
            statement.executeUpdate();

            mensaje = statement.getString(2);
            statement.close();

            mostrarMensaje(mensaje);

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
}

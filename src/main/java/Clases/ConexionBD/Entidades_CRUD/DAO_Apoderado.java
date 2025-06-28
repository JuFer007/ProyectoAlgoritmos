package Clases.ConexionBD.Entidades_CRUD;
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
        String consulta = "{CALL sp_Apoderado_Insert(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";

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

            statement.registerOutParameter(10, Types.VARCHAR);
            statement.executeUpdate();

            mensaje = statement.getString(10);
            statement.close();

            mostrarMensaje(mensaje);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Metodo para actualizar apoderado
    public void Actualizar(Apoderado apoderado) {
        String mensaje = "";
        String consulta = "{CALL sp_Apoderado_Update(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";

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
}

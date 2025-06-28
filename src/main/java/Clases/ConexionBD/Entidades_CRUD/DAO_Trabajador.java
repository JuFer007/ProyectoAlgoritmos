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
        String consulta = "{CALL sp_Trabajador_Insert(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";

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
    public void Actualizar(Trabajador trabajador) {
        String mensaje = "";
        String consulta = "{CALL sp_Trabajador_Update(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";

        try {
            CallableStatement statement = ConexionMySQL.getInstancia().getConexion().prepareCall(consulta);
            statement.setString(1, trabajador.getDnipersona());
            statement.setString(2, trabajador.getPrimernombre());
            statement.setString(3, trabajador.getSegundonombre());
            statement.setString(4, trabajador.getApellidopaterno());
            statement.setString(5, trabajador.getApellidomaterno());
            statement.setDate(6, (Date) trabajador.getFechanacimiento() );
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
}

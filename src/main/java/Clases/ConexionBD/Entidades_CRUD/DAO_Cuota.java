package Clases.ConexionBD.Entidades_CRUD;
import Clases.ConexionBD.ConexionMySQL;
import javafx.scene.control.Alert;
import java.sql.*;
import java.util.ArrayList;

public class DAO_Cuota {
    ArrayList<Object[]> listaPagos;

    public void registrarPago(int idPago) {
        String mensaje = "";
        String sql = "{CALL sp_Pago_Actualizar(?, ?)}";

        try {
            CallableStatement consulta = ConexionMySQL.getInstancia().getConexion().prepareCall(sql);

            consulta.setInt(1, idPago);
            consulta.registerOutParameter(2, java.sql.Types.VARCHAR);
            consulta.execute();
            mensaje = consulta.getString(2);
            consulta.close();

            mostrarMensaje(mensaje);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Mensaje del sistema");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public void eliminarPago(int idPago) {
        String mensaje = "";
        String sql = "{CALL sp_Pago_Eliminar(?, ?)}";

        try {
            CallableStatement cs = ConexionMySQL.getInstancia().getConexion().prepareCall(sql);

            cs.setInt(1, idPago);
            cs.registerOutParameter(2, java.sql.Types.VARCHAR);

            cs.execute();

            mensaje = cs.getString(2);
            cs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        mostrarMensaje(mensaje);
    }

    //Metodo para listar todos los pagos
    public void Listar() {
        listaPagos = new ArrayList<>();
        listaPagos.clear();

        String consulta = "select pago.idPago, alumno.codigoAlumno, persona.primerNombre, persona.segundoNombre, persona.apellidoPaterno, persona.apellidoMaterno,\n" +
                "pago.fechaPago, cuota.estadoCuota, pago.montoPago, pago.metodoPago \n" +
                "from persona inner join alumno on persona.idPersona = alumno.idAlumno inner join matricula on alumno.idAlumno = matricula.idAlumno\n" +
                "inner join cuota on cuota.idMatricula = matricula.idMatricula inner join pago on cuota.idPago = pago.idPago\n" +
                "order by pago.idPago";
        try {
            PreparedStatement comando = ConexionMySQL.getInstancia().getConexion().prepareStatement(consulta);
            ResultSet resultado = comando.executeQuery();

            while (resultado.next()) {
                Object[] fila = new Object[]{
                        resultado.getInt("idPago"),
                        resultado.getString("codigoAlumno"),
                        resultado.getString("primerNombre"),
                        resultado.getString("segundoNombre"),
                        resultado.getString("apellidoPaterno"),
                        resultado.getString("apellidoMaterno"),
                        resultado.getDate("fechaPago"),
                        resultado.getString("estadoCuota"),
                        resultado.getDouble("montoPago"),
                        resultado.getString("metodoPago")
                };

                listaPagos.add(fila);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Object[]> getListaPagos() {
        return listaPagos;
    }

    public void listarPagosFiltrados(String grado, String seccion, String estado) {
        listaPagos = new ArrayList<>();
        String sql = "{CALL sp_ListarCuotasPorGradoSeccionEstado(?, ?, ?)}";

        try {
            CallableStatement cs = ConexionMySQL.getInstancia().getConexion().prepareCall(sql);

            cs.setString(1, (grado == null || grado.trim().isEmpty()) ? null : grado);
            cs.setString(2, (seccion == null || seccion.trim().isEmpty()) ? null : seccion);
            cs.setString(3, (estado == null || estado.trim().isEmpty()) ? null : estado);

            ResultSet resultado = cs.executeQuery();

            while (resultado.next()) {
                Object[] fila = new Object[]{
                        resultado.getInt("idPago"),
                        resultado.getString("codigoAlumno"),
                        resultado.getString("primerNombre"),
                        resultado.getString("segundoNombre"),
                        resultado.getString("apellidoPaterno"),
                        resultado.getString("apellidoMaterno"),
                        resultado.getDate("fechaPago"),
                        resultado.getString("estadoCuota"),
                        resultado.getDouble("montoPago"),
                        resultado.getString("metodoPago")
                };
                listaPagos.add(fila);
            }

            resultado.close();
            cs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

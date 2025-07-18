package Clases.ConexionBD.Entidades_DAO;

import Clases.ConexionBD.ConexionMySQL;
import javafx.scene.control.Alert;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAO_Pagos {
    ArrayList<Object[]> listaCuotas;

    public ArrayList<Object[]> getListaCuotas() {
        return listaCuotas;
    }

    public void registrarPago(int idCuota, String metodo) {
        String mensaje = "";
        String sql = "{CALL sp_RegistrarPago(?, ?,?)}";

        try {
            CallableStatement consulta = ConexionMySQL.getInstancia().getConexion().prepareCall(sql);

            consulta.setInt(1, idCuota);
            consulta.setString(2, metodo);
            consulta.registerOutParameter(3, java.sql.Types.VARCHAR);
            consulta.execute();
            mensaje = consulta.getString(3);
            consulta.close();

            mostrarMensaje(mensaje);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    private void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Mensaje del sistema");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    //Metodo para listar todos los pagos
    public void Listar() {
        listaCuotas = new ArrayList<>();
        listaCuotas.clear();

        String consulta = """
        SELECT 
            pago.idPago, 
            alumno.codigoAlumno, 
            persona.primerNombre, 
            persona.segundoNombre, 
            persona.apellidoPaterno, 
            persona.apellidoMaterno,
            pago.fechaPago, 
            pago.montoPago, 
            pago.metodoPago
        FROM persona
        INNER JOIN alumno ON persona.idPersona = alumno.idPersona
        INNER JOIN matricula ON alumno.idAlumno = matricula.idAlumno
        INNER JOIN cuota ON cuota.idMatricula = matricula.idMatricula
        INNER JOIN pago ON cuota.idPago = pago.idPago
        ORDER BY alumno.codigoAlumno ASC
    """;

        try (PreparedStatement comando = ConexionMySQL.getInstancia().getConexion().prepareStatement(consulta);
             ResultSet resultado = comando.executeQuery()) {

            while (resultado.next()) {
                Object[] fila = new Object[]{
                        resultado.getInt("idPago"),
                        resultado.getString("codigoAlumno"),
                        resultado.getString("primerNombre"),
                        resultado.getString("segundoNombre"),
                        resultado.getString("apellidoPaterno"),
                        resultado.getString("apellidoMaterno"),
                        resultado.getDate("fechaPago"),
                        resultado.getDouble("montoPago"),
                        resultado.getString("metodoPago")
                };

                listaCuotas.add(fila);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void listarPagosFiltrados(String grado, String seccion) {
        listaCuotas = new ArrayList<>();

        String sql = "{CALL sp_ListarPagosPorGradoSeccion(?, ?)}";

        try (CallableStatement cs = ConexionMySQL.getInstancia().getConexion().prepareCall(sql)) {

            cs.setString(1, (grado == null || grado.trim().isEmpty()) ? null : grado);
            cs.setString(2, (seccion == null || seccion.trim().isEmpty()) ? null : seccion);

            try (ResultSet resultado = cs.executeQuery()) {
                while (resultado.next()) {
                    Object[] fila = new Object[]{
                            resultado.getInt("idPago"),
                            resultado.getString("codigoAlumno"),
                            resultado.getString("primerNombre"),
                            resultado.getString("segundoNombre"),
                            resultado.getString("apellidoPaterno"),
                            resultado.getString("apellidoMaterno"),
                            resultado.getDate("fechaPago"),
                            resultado.getDouble("montoPago"),
                            resultado.getString("metodoPago")
                    };
                    listaCuotas.add(fila);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

package Clases.ConexionBD.Entidades_CRUD;
import Clases.ConexionBD.ConexionMySQL;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DAO_Reportes {
    private ArrayList<Object[]> listapagos;

    //Metodo para obtener el total de alumnos
    public static int obtenerTotalAlumnos() {
        int totalAlumnos = 0;
        String consulta = "{CALL sp_Total_Alumnos(?)}";

        try (CallableStatement statement = ConexionMySQL.getInstancia().getConexion().prepareCall(consulta)) {
            statement.registerOutParameter(1, Types.INTEGER);
            statement.execute();
            totalAlumnos = statement.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalAlumnos;
    }

    //Metodo para obtener el total de profesores
    public static int obtenerTotalProfesores() {
        int totalProfesores = 0;
        String consulta = "{CALL sp_Total_Profesores(?)}";

        try (CallableStatement statement = ConexionMySQL.getInstancia().getConexion().prepareCall(consulta)) {
            statement.registerOutParameter(1, Types.INTEGER);
            statement.execute();
            totalProfesores = statement.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalProfesores;
    }

    //Metodo para obtener el total de trabajadores
    public static int obtenerTotalTrabajadores() {
        int totalTrabajadores = 0;
        String consulta = "{CALL sp_Total_Trabajadores(?)}";

        try (CallableStatement statement = ConexionMySQL.getInstancia().getConexion().prepareCall(consulta)) {
            statement.registerOutParameter(1, Types.INTEGER);
            statement.execute();
            totalTrabajadores = statement.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalTrabajadores;
    }

    //Metodo para obtener el total de alumnos por grado
    public Map<String, Integer> alumnosPorGrado() {
        Map<String, Integer> alumnosPorGrado = new HashMap<>();
        String consulta = "{CALL sp_Total_Alumnos_PorGrado()}";

        try {
            CallableStatement callableStatement = ConexionMySQL.getInstancia().getConexion().prepareCall(consulta);
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()) {
                String grado = rs.getString("Grado");
                int total = rs.getInt("TotalAlumnos");
                alumnosPorGrado.put(grado, total);
            }
            rs.close();
            callableStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alumnosPorGrado;
    }

    //Metodo para obtener los trabajadores, padres y profesores
    public Map<String, Integer> resumenPersonal() {
        Map<String, Integer> resumen = new HashMap<>();
        String consulta = "{CALL sp_Resumen_Personal()}";

        try {
            CallableStatement callableStatement = ConexionMySQL.getInstancia().getConexion().prepareCall(consulta);
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()) {
                String categoria = rs.getString("Categoria");
                int cantidad = rs.getInt("Cantidad");
                resumen.put(categoria, cantidad);
            }
            rs.close();
            callableStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resumen;
    }

    //Metodo para obtener el total de apoderados
    public static int obtenerTotalDeApoderados() {
        int totalApoderados = 0;
        String consulta = "{CALL sp_TotalApoderados(?)}";
        try (CallableStatement statement = ConexionMySQL.getInstancia().getConexion().prepareCall(consulta)) {
            statement.registerOutParameter(1, Types.INTEGER);
            statement.execute();
            totalApoderados = statement.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalApoderados;
    }

    //Metodo para obtener los pagos segun el mes
    public void listarPagos(Integer mes) {
        listapagos = new ArrayList<>();
        String sql = "{CALL sp_ListarPagosPorMes(?)}";

        try {
            CallableStatement cs = ConexionMySQL.getInstancia().getConexion().prepareCall(sql);

            if (mes == null) {
                cs.setNull(1, Types.INTEGER);
            } else {
                cs.setInt(1, mes);
            }

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
                        resultado.getString("estadoPago"),
                        resultado.getDouble("montoPago"),
                        resultado.getString("metodoPago")
                };
                listapagos.add(fila);
            }

            resultado.close();
            cs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Object[]> getListapagos() {
        return listapagos;
    }
}

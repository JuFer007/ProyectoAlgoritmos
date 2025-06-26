package Clases.ConexionBD.Entidades_CRUD;
import Clases.ConexionBD.ConexionMySQL;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

public class DAO_Reportes {
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
    public static int obtenerTotalDeAlumnosPorGrado() {
        int totalAlumnosPorGrado = 0;
        String consulta = "{CALL sp_Total_Alumnos_PorGrado(?)}";

        try (CallableStatement statement = ConexionMySQL.getInstancia().getConexion().prepareCall(consulta)) {
            statement.registerOutParameter(1, Types.INTEGER);
            statement.execute();
            totalAlumnosPorGrado = statement.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalAlumnosPorGrado;
    }
}

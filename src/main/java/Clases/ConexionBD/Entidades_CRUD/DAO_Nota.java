package Clases.ConexionBD.Entidades_CRUD;
import Clases.ConexionBD.ConexionMySQL;
import java.sql.*;
import java.util.ArrayList;

public class DAO_Nota {
    ArrayList<Object[]> notas;
    ArrayList<Object[]> datosNotas;

    public void updateNota() {

    }

    public void modificarVariasNotas() {

    }

    public ArrayList<Object[]> listarAlumnosYMatricula() {
        notas = new ArrayList<>();
        notas.clear();

        String sql = "SELECT alumno.codigoAlumno, persona.DNIpersona, persona.primerNombre, persona.segundoNombre, "
                + "persona.apellidoPaterno, persona.apellidoMaterno, matricula.idMatricula "
                + "FROM alumno "
                + "INNER JOIN persona ON alumno.idPersona = persona.idPersona "
                + "INNER JOIN matricula ON matricula.idAlumno = alumno.idAlumno "
                + "ORDER BY alumno.codigoAlumno";

        try (Connection cn = ConexionMySQL.getInstancia().getConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Object[] fila = new Object[7];
                fila[0] = rs.getString("codigoAlumno");
                fila[1] = rs.getString("DNIpersona");
                fila[2] = rs.getString("primerNombre");
                fila[3] = rs.getString("segundoNombre");
                fila[4] = rs.getString("apellidoPaterno");
                fila[5] = rs.getString("apellidoMaterno");
                fila[6] = rs.getInt("idMatricula");

                notas.add(fila);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notas;
    }

    public ArrayList<Object[]> listarNotasPorAlumno(String codigoAlumno) {
        return datosNotas;
    }

    public ArrayList<Object[]> getNotas() {
        return notas;
    }
}

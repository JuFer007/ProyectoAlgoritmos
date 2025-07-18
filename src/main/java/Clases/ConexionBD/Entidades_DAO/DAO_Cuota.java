package Clases.ConexionBD.Entidades_DAO;
import Clases.ConexionBD.ConexionMySQL;

import java.sql.*;
import java.util.ArrayList;

public class DAO_Cuota {
    ArrayList<Object[]> listaCuotas;

    public ArrayList<Object[]> getListaCuotas() {
        return listaCuotas;
    }

    public void listarCuotasConAlumno() {
        listaCuotas = new ArrayList<>();

        String sql = """
                        SELECT 
                            c.idCuota,
                            a.codigoAlumno,
                            p.primerNombre,
                            p.segundoNombre,
                            p.apellidoPaterno,
                            p.apellidoMaterno,
                            c.concepto,
                            c.montoCuota,
                            c.fechaVencimiento,
                            c.estadoCuota
                        FROM Cuota c
                        INNER JOIN Matricula m ON c.idMatricula = m.idMatricula
                        INNER JOIN Alumno a ON m.idAlumno = a.idAlumno
                        INNER JOIN Persona p ON a.idPersona = p.idPersona
                        ORDER BY a.codigoAlumno
                    """;

        try (PreparedStatement ps = ConexionMySQL.getInstancia().getConexion().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Object[] fila = new Object[]{
                        rs.getInt("idCuota"),
                        rs.getString("codigoAlumno"),
                        rs.getString("primerNombre") + " " + rs.getString("segundoNombre"),
                        rs.getString("apellidoPaterno") + " " + rs.getString("apellidoMaterno"),
                        rs.getString("concepto"),
                        rs.getBigDecimal("montoCuota"),
                        rs.getDate("fechaVencimiento"),
                        rs.getString("estadoCuota")
                };
                listaCuotas.add(fila);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //Busqueda de Cuotas filtradas
    public void listarCuotasFiltradas(String grado, String seccion, String estado) {
        listaCuotas = new ArrayList<>();

        String sql = "{CALL sp_ListarCuotasPorGradoSeccionEstado(?, ?, ?)}";

        try (CallableStatement cs = ConexionMySQL.getInstancia().getConexion().prepareCall(sql)) {

            // Asignar parámetros
            cs.setString(1, (grado == null || grado.trim().isEmpty()) ? null : grado);
            cs.setString(2, (seccion == null || seccion.trim().isEmpty()) ? null : seccion);
            cs.setString(3, (estado == null || estado.trim().isEmpty()) ? null : estado);

            // Ejecutar
            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    Object[] fila = new Object[]{
                            rs.getInt("idCuota"),
                            rs.getString("codigoAlumno"),
                            rs.getString("primerNombre") + " " + rs.getString("segundoNombre"),
                            rs.getString("apellidoPaterno") + " " + rs.getString("apellidoMaterno"),
                            rs.getString("concepto"),
                            rs.getBigDecimal("montoCuota"),
                            rs.getDate("fechaVencimiento"),
                            rs.getString("estadoCuota")
                    };
                    listaCuotas.add(fila);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}

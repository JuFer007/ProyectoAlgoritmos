package Clases.ConexionBD.Entidades_DAO;
import Clases.ConexionBD.ConexionMySQL;
import java.sql.*;
import java.util.ArrayList;

public class DAO_Nota {
    ArrayList<Object[]> notasPorEstudiante;

    //Metodo para modificar notas de un alumo
    public void modificarNotasYPromedio(int idMatricula, String nombreCurso, int nota1, int nota2, int nota3) {
        String sql = "{CALL sp_ModificarNotasYPromedio(?, ?, ?, ?, ?)}";

        try (Connection cn = ConexionMySQL.getInstancia().getConexion();
             CallableStatement stmt = cn.prepareCall(sql)) {

            stmt.setInt(1, idMatricula);
            stmt.setString(2, nombreCurso);
            stmt.setInt(3, nota1);
            stmt.setInt(4, nota2);
            stmt.setInt(5, nota3);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Metodo para listar a todos los alumnos por dni profesor
    public ArrayList<Object[]> listarAlumnosParaUnProfesor(String DNIprofesor) {
        notasPorEstudiante = new ArrayList<>();
        notasPorEstudiante.clear();

        String sql = """
            SELECT 
            a.codigoAlumno,
            perAlumno.DNIpersona,
            perAlumno.primerNombre AS alumnoNombre,
            perAlumno.segundoNombre AS alumnoSegundoNombre,
            perAlumno.apellidoPaterno AS alumnoApellidoPaterno,
            perAlumno.apellidoMaterno AS alumnoApellidoMaterno,
            m.idMatricula
            FROM Profesor p
            INNER JOIN Persona perProfesor ON p.idPersona = perProfesor.idPersona
            INNER JOIN AsignacionProfesor pc ON p.idProfesor = pc.idProfesor
            INNER JOIN Curso c ON pc.idCurso = c.idCurso
            INNER JOIN Grado g ON c.idGrado = g.idGrado
            INNER JOIN Matricula m ON m.idGrado = g.idGrado
            INNER JOIN Seccion s ON m.idSeccion = s.idSeccion
            INNER JOIN Alumno a ON m.idAlumno = a.idAlumno
            INNER JOIN Persona perAlumno ON a.idPersona = perAlumno.idPersona
            WHERE perProfesor.DNIpersona = ?
            ORDER BY g.grado, s.seccion, c.nombreCurso, perAlumno.apellidoPaterno
    """;

        try (Connection cn = ConexionMySQL.getInstancia().getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, DNIprofesor);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Object[] fila = new Object[7];
                    fila[0] = rs.getString("codigoAlumno");
                    fila[1] = rs.getString("DNIpersona");
                    fila[2] = rs.getString("alumnoNombre");
                    fila[3] = rs.getString("alumnoSegundoNombre");
                    fila[4] = rs.getString("alumnoApellidoPaterno");
                    fila[5] = rs.getString("alumnoApellidoMaterno");
                    fila[6] = rs.getInt("idMatricula");

                    notasPorEstudiante.add(fila);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return notasPorEstudiante;
    }

    //Metodo para listar los alumnos
    public ArrayList<Object[]> listarAlumnos() {
        notasPorEstudiante = new ArrayList<>();
        notasPorEstudiante.clear();

        String sql = """
            SELECT 
            a.codigoAlumno,
            perAlumno.DNIpersona,
            perAlumno.primerNombre AS alumnoNombre,
            perAlumno.segundoNombre AS alumnoSegundoNombre,
            perAlumno.apellidoPaterno AS alumnoApellidoPaterno,
            perAlumno.apellidoMaterno AS alumnoApellidoMaterno,
            m.idMatricula
            FROM Profesor p
            INNER JOIN Persona perProfesor ON p.idPersona = perProfesor.idPersona
            INNER JOIN AsignacionProfesor pc ON p.idProfesor = pc.idProfesor
            INNER JOIN Curso c ON pc.idCurso = c.idCurso
            INNER JOIN Grado g ON c.idGrado = g.idGrado
            INNER JOIN Matricula m ON m.idGrado = g.idGrado
            INNER JOIN Seccion s ON m.idSeccion = s.idSeccion
            INNER JOIN Alumno a ON m.idAlumno = a.idAlumno
            INNER JOIN Persona perAlumno ON a.idPersona = perAlumno.idPersona
            ORDER BY g.grado, s.seccion, c.nombreCurso, perAlumno.apellidoPaterno
    """;

        try (Connection cn = ConexionMySQL.getInstancia().getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Object[] fila = new Object[7];
                    fila[0] = rs.getString("codigoAlumno");
                    fila[1] = rs.getString("DNIpersona");
                    fila[2] = rs.getString("alumnoNombre");
                    fila[3] = rs.getString("alumnoSegundoNombre");
                    fila[4] = rs.getString("alumnoApellidoPaterno");
                    fila[5] = rs.getString("alumnoApellidoMaterno");
                    fila[6] = rs.getInt("idMatricula");

                    notasPorEstudiante.add(fila);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notasPorEstudiante;
    }


    //Metodo para listar los cursos y notas por alumno
    public ArrayList<Object[]> listarCursosDeAlumno(int idMatricula) {
        ArrayList<Object[]> notasYCursos = new ArrayList<>();

        String consulta = """
                            SELECT
                                c.nombreCurso,
                                n.nota,
                                n.tipoNota
                            FROM DetalleMatricula d
                            INNER JOIN Curso c ON d.idCurso = c.idCurso
                            LEFT JOIN Nota n
                                ON n.idMatricula = d.idMatricula
                                AND n.idCurso = d.idCurso
                            WHERE d.idMatricula = ?
                            ORDER BY c.nombreCurso, n.tipoNota
                        """;

        try (Connection cn = ConexionMySQL.getInstancia().getConexion();
             PreparedStatement ps = cn.prepareStatement(consulta)) {

            ps.setInt(1, idMatricula);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Object[] fila = new Object[3];
                    fila[0] = rs.getString("nombreCurso");
                    fila[1] = rs.getInt("nota");
                    fila[2] = rs.getString("tipoNota");

                    notasYCursos.add(fila);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return notasYCursos;
    }


    //Metodo para listar cursos de un alumno
    public ArrayList<String> listarCursos(int numMatricula) {
        ArrayList<String> cursos = new ArrayList<>();

        String consulta = """
                            SELECT c.nombreCurso
                            FROM DetalleMatricula d
                            INNER JOIN Curso c ON d.idCurso = c.idCurso
                            WHERE d.idMatricula = ?
                        """;

        try (Connection cn = ConexionMySQL.getInstancia().getConexion();
             PreparedStatement ps = cn.prepareStatement(consulta)) {

            ps.setInt(1, numMatricula);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    cursos.add(rs.getString("nombreCurso"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cursos;
    }


    //Metodo para listar notas de un solo curso y matricula
    public ArrayList<Object[]> listarNotasDeUnSoloCurso(int idMatricula, String nombreCurso) {
        ArrayList<Object[]> notasCursos = new ArrayList<>();

        String consulta = """
                                SELECT 
                                    c.nombreCurso, 
                                    n.nota, 
                                    n.tipoNota 
                                FROM Nota n
                                INNER JOIN Matricula m ON n.idMatricula = m.idMatricula
                                INNER JOIN Curso c ON n.idCurso = c.idCurso
                                WHERE m.idMatricula = ? AND c.nombreCurso = ?
                          """;

        try (Connection cn = ConexionMySQL.getInstancia().getConexion();
             PreparedStatement ps = cn.prepareStatement(consulta)) {

            ps.setInt(1, idMatricula);
            ps.setString(2, nombreCurso);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Object[] fila = new Object[3];
                    fila[0] = rs.getString("nombreCurso");
                    fila[1] = rs.getString("nota");
                    fila[2] = rs.getString("tipoNota");
                    notasCursos.add(fila);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notasCursos;
    }


    //Metodo para listar alumnos segun su grado y sección
    public ArrayList<Object[]> alumnosPorGradoYSeccion(String grado, String seccion) {
        ArrayList<Object[]> alumnos = new ArrayList<>();
        alumnos.clear();

        String sql = "SELECT alumno.codigoAlumno, persona.DNIpersona, persona.primerNombre, persona.segundoNombre, "
                + "persona.apellidoPaterno, persona.apellidoMaterno, matricula.idMatricula "
                + "FROM alumno "
                + "INNER JOIN persona ON alumno.idPersona = persona.idPersona "
                + "INNER JOIN matricula ON matricula.idAlumno = alumno.idAlumno "
                + "INNER JOIN grado ON matricula.idGrado = grado.idGrado "
                + "INNER JOIN seccion ON matricula.idSeccion = seccion.idSeccion "
                + "WHERE grado.grado = ? AND seccion.seccion = ? "
                + "ORDER BY alumno.codigoAlumno";

        try (Connection cn = ConexionMySQL.getInstancia().getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, grado);
            ps.setString(2, seccion);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Object[] fila = new Object[7];
                    fila[0] = rs.getString("codigoAlumno");
                    fila[1] = rs.getString("DNIpersona");
                    fila[2] = rs.getString("primerNombre");
                    fila[3] = rs.getString("segundoNombre");
                    fila[4] = rs.getString("apellidoPaterno");
                    fila[5] = rs.getString("apellidoMaterno");
                    fila[6] = rs.getInt("idMatricula");

                    alumnos.add(fila);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return alumnos;
    }

    //Metodo para listar alumnos por grado
    public ArrayList<Object[]> alumnosPorGrado(String grado) {
        ArrayList<Object[]> alumnos = new ArrayList<>();
        alumnos.clear();

        // Consulta SQL que selecciona las columnas necesarias
        String sql = "SELECT alumno.codigoAlumno, persona.DNIpersona, persona.primerNombre, persona.segundoNombre, "
                + "persona.apellidoPaterno, persona.apellidoMaterno, matricula.idMatricula "
                + "FROM alumno "
                + "INNER JOIN persona ON alumno.idPersona = persona.idPersona "
                + "INNER JOIN matricula ON matricula.idAlumno = alumno.idAlumno "
                + "INNER JOIN grado ON matricula.idGrado = grado.idGrado "
                + "INNER JOIN seccion ON matricula.idSeccion = seccion.idSeccion "
                + "WHERE grado.grado = ? "
                + "ORDER BY alumno.codigoAlumno";

        try (Connection cn = ConexionMySQL.getInstancia().getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            // Asignamos el parámetro de la consulta
            ps.setString(1, grado);

            // Ejecutamos la consulta
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Object[] fila = new Object[7];
                    fila[0] = rs.getString("codigoAlumno");
                    fila[1] = rs.getString("DNIpersona");
                    fila[2] = rs.getString("primerNombre");
                    fila[3] = rs.getString("segundoNombre");
                    fila[4] = rs.getString("apellidoPaterno");
                    fila[5] = rs.getString("apellidoMaterno");
                    fila[6] = rs.getInt("idMatricula");

                    alumnos.add(fila);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return alumnos;
    }

    //Metodo para obtener las notas de un curso específico de un alumno
    public ArrayList<Integer> obtenerNotasDeCurso(int idMatricula, String nombreCurso) {
        ArrayList<Integer> notas = new ArrayList<>();

        String sql = "SELECT n.nota "
                + "FROM Nota n "
                + "INNER JOIN Matricula m ON n.idMatricula = m.idMatricula "
                + "INNER JOIN Curso c ON n.idCurso = c.idCurso "
                + "WHERE m.idMatricula = ? AND c.nombreCurso = ?";

        try (Connection cn = ConexionMySQL.getInstancia().getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, idMatricula);
            ps.setString(2, nombreCurso);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int nota = rs.getInt("nota");
                    notas.add(nota);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notas;
    }

    //Metodo para obtener libreta de notas
    public ArrayList<Object[]> obtenerNotasDeCursos(int idMatricula) {
        ArrayList<Object[]> notasCursos = new ArrayList<>();

        String sql = "SELECT c.nombreCurso, "
                + "(SELECT nota FROM Nota WHERE idMatricula = ? AND idCurso = c.idCurso AND tipoNota = 'Parcial' ORDER BY idNota LIMIT 1) AS 'Primera Nota Parcial', "
                + "(SELECT nota FROM Nota WHERE idMatricula = ? AND idCurso = c.idCurso AND tipoNota = 'Parcial' ORDER BY idNota LIMIT 1 OFFSET 1) AS 'Segunda Nota Parcial', "
                + "(SELECT nota FROM Nota WHERE idMatricula = ? AND idCurso = c.idCurso AND tipoNota = 'Parcial' ORDER BY idNota LIMIT 1 OFFSET 2) AS 'Tercera Nota Parcial', "
                + "(SELECT nota FROM Nota WHERE idMatricula = ? AND idCurso = c.idCurso AND tipoNota = 'Promedio' LIMIT 1) AS 'Promedio' "
                + "FROM Curso c "
                + "WHERE c.idGrado = (SELECT idGrado FROM Matricula WHERE idMatricula = ?) "
                + "ORDER BY c.nombreCurso;";

        try (Connection cn = ConexionMySQL.getInstancia().getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idMatricula);
            ps.setInt(2, idMatricula);
            ps.setInt(3, idMatricula);
            ps.setInt(4, idMatricula);
            ps.setInt(5, idMatricula);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Object[] notaCurso = new Object[5];
                    notaCurso[0] = rs.getString("nombreCurso");
                    notaCurso[1] = rs.getInt("Primera Nota Parcial");
                    notaCurso[2] = rs.getInt("Segunda Nota Parcial");
                    notaCurso[3] = rs.getInt("Tercera Nota Parcial");
                    notaCurso[4] = rs.getInt("Promedio");

                    notasCursos.add(notaCurso);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return notasCursos;
    }
}

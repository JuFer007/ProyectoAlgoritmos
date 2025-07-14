package Clases.ConexionBD.Entidades_CRUD;
import Clases.ClasesPersonas.Alumno;
import Clases.ConexionBD.ConexionMySQL;
import javafx.scene.control.Alert;
import java.sql.*;
import java.util.ArrayList;

public class DAO_Alumno {
    private ArrayList<Alumno> alumnos;

    //Metodo para creaar alumno
    public void Crear(Alumno alumno, String DNIApoderado) {
        String mensaje = "";
        String consulta = "{CALL sp_Alumno_Insert(?, ?, ?, ?, ?, ?, ?, ?, ?)}";

        try {
            CallableStatement statement = ConexionMySQL.getInstancia().getConexion().prepareCall(consulta);
            statement.setString(1, alumno.getDnipersona());
            statement.setString(2, alumno.getPrimernombre());
            statement.setString(3, alumno.getSegundonombre());
            statement.setString(4, alumno.getApellidopaterno());
            statement.setString(5, alumno.getApellidomaterno());
            statement.setDate(6, (Date) alumno.getFechanacimiento());
            statement.setString(7, alumno.getGenero());
            statement.setString(8, DNIApoderado);

            statement.registerOutParameter(9, Types.VARCHAR);
            statement.executeUpdate();

            mensaje = statement.getString(9);
            statement.close();

            mostrarMensaje(mensaje);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //Metodo para actualizar datos de un alumno
    public void Actualizar(String primerNombre, String segundoNombre, String apellidoPaterno,
                           String apellidoMaterno, String genero, String dniPersona) {
        String sql = "UPDATE Persona p "
                + "LEFT JOIN Alumno a ON p.idPersona = a.idPersona "
                + "INNER JOIN Apoderado ap ON a.idApoderado = ap.idApoderado "
                + "INNER JOIN Persona pA ON ap.idPersona = pA.idPersona "
                + "SET "
                + "p.primerNombre = ?, "
                + "p.segundoNombre = ?, "
                + "p.apellidoPaterno = ?, "
                + "p.apellidoMaterno = ?, "
                + "p.genero = ? "
                + "WHERE p.DNIpersona = ?";

        try (Connection con = ConexionMySQL.getInstancia().getConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, primerNombre);
            stmt.setString(2, segundoNombre);
            stmt.setString(3, apellidoPaterno);
            stmt.setString(4, apellidoMaterno);
            stmt.setString(5, genero);
            stmt.setString(6, dniPersona);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Metodo para obtener los alumnos
    public void Listar() {
        alumnos = new ArrayList<>();
        alumnos.clear();

        String consulta = "select persona.DNIpersona, persona.primerNombre, persona.segundoNombre, persona.apellidoMaterno, persona.apellidoPaterno, persona.fechaNacimiento, persona.genero, alumno.codigoAlumno, alumno.idApoderado  from persona inner join Alumno on persona.idPersona = alumno.idPersona\n" +
                "order by persona.apellidoPaterno";
        try {
            PreparedStatement comando = ConexionMySQL.getInstancia().getConexion().prepareStatement(consulta);
            ResultSet resultado = comando.executeQuery();
            while (resultado.next()) {
                Alumno alumno = new Alumno(
                        resultado.getString("DNIpersona"),
                        resultado.getString("primerNombre"),
                        resultado.getString("segundoNombre"),
                        resultado.getString("apellidoPaterno"),
                        resultado.getString("apellidoMaterno"),
                        resultado.getDate("fechaNacimiento"),
                        resultado.getString("genero"),
                        resultado.getString("codigoAlumno"),
                        resultado.getInt("idApoderado")
                );
                alumnos.add(alumno);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Retonar la lista de alumnos
    public ArrayList<Alumno> getAlumnos() {
        return alumnos;
    }

    //Metodo para listar alumnos por grado
    public ArrayList<Alumno> alumnosPorGrado(String grado) {
        alumnos = new ArrayList<>();
        alumnos.clear();

        try {
            String sql = "{CALL BuscarAlumnosPorGrado(?)}";
            PreparedStatement ps = ConexionMySQL.getInstancia().getConexion().prepareCall(sql);
            ps.setString(1, grado);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Alumno alumno = new Alumno(
                        rs.getString("DNIpersona"),
                        rs.getString("primerNombre"),
                        rs.getString("segundoNombre"),
                        rs.getString("apellidoPaterno"),
                        rs.getString("apellidoMaterno"),
                        rs.getDate("fechaNacimiento"),
                        rs.getString("genero"),
                        rs.getString("codigoAlumno"),
                        rs.getInt("idApoderado")
                );
                alumnos.add(alumno);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return alumnos;
    }

    //Metodo para listar alumnos por grado y seccion
    public ArrayList<Alumno> alumnosPorGradoYSeccion(String grado, String seccion) {
        alumnos = new ArrayList<>();
        alumnos.clear();

        try {
            String sql = "{CALL BuscarAlumnosPorGradoYSeccion(?, ?)}";
            PreparedStatement ps = ConexionMySQL.getInstancia().getConexion().prepareCall(sql);
            ps.setString(1, grado);
            ps.setString(2, seccion);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Alumno alumno = new Alumno(
                        rs.getString("DNIpersona"),
                        rs.getString("primerNombre"),
                        rs.getString("segundoNombre"),
                        rs.getString("apellidoPaterno"),
                        rs.getString("apellidoMaterno"),
                        rs.getDate("fechaNacimiento"),
                        rs.getString("genero"),
                        rs.getString("codigoAlumno"),
                        rs.getInt("idApoderado")
                );
                alumnos.add(alumno);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return alumnos;
    }

    //Metodo para recibir los datos de un alumno por su codigoAlumno
    public Alumno buscarPorCodigo(String codigo) {
        Alumno alumno = null;

        String sql = """
                        SELECT 
                            a.idAlumno,
                            a.codigoAlumno,
                            a.idPersona,
                            a.idApoderado,
                            p.DNIpersona,
                            p.primerNombre,
                            p.segundoNombre,
                            p.apellidoPaterno,
                            p.apellidoMaterno,
                            p.fechaNacimiento,
                            p.genero
                        FROM Alumno a
                        INNER JOIN Persona p ON a.idPersona = p.idPersona
                        WHERE a.codigoAlumno = ?
                    """;

        try (Connection conn = ConexionMySQL.getInstancia().getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, codigo);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    alumno = new Alumno(
                            rs.getString("DNIpersona"),
                            rs.getString("primerNombre"),
                            rs.getString("segundoNombre"),
                            rs.getString("apellidoPaterno"),
                            rs.getString("apellidoMaterno"),
                            rs.getDate("fechaNacimiento"),
                            rs.getString("genero"),
                            rs.getString("codigoAlumno"),
                            rs.getInt("idApoderado")
                    );

                    alumno.setIdAlumno(rs.getInt("idAlumno"));
                    alumno.setIdPersona(rs.getInt("idPersona"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return alumno;
    }


    //Metodo para mostrar mensaje
    private void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Mensaje de información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    //Metodo para obtener datos del alumno
    public String[] obtenerDatosAlumno(String codigoAlumno) {
        String sql = "SELECT "
                + "CONCAT(persona.primerNombre, ' ', persona.segundoNombre, ' ', persona.apellidoPaterno, ' ', persona.apellidoMaterno) AS Estudiante, "
                + "alumno.codigoAlumno AS Código, "
                + "grado.grado AS Grado, "
                + "seccion.seccion AS Sección, "
                + "añoEscolar.añoEscolar AS `Año Académico` "
                + "FROM Alumno alumno "
                + "JOIN Persona persona ON alumno.idPersona = persona.idPersona "
                + "JOIN Matricula matricula ON alumno.idAlumno = matricula.idAlumno "
                + "JOIN Grado grado ON matricula.idGrado = grado.idGrado "
                + "JOIN Seccion seccion ON matricula.idSeccion = seccion.idSeccion "
                + "JOIN AñoEscolar añoEscolar ON matricula.idAñoE = añoEscolar.idAñoE "
                + "WHERE alumno.codigoAlumno = ?";
        String[] datosAlumno = new String[5];

        try {
            Connection con = ConexionMySQL.getInstancia().getConexion();
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, codigoAlumno);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                datosAlumno[0] = rs.getString("Estudiante");
                datosAlumno[1] = rs.getString("Código");
                datosAlumno[2] = rs.getString("Grado");
                datosAlumno[3] = rs.getString("Sección");
                datosAlumno[4] = rs.getString("Año Académico");
            } else {
                datosAlumno = new String[]{"No encontrado", "", "", "", ""};
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return datosAlumno;
    }

    //Metodo para listar los datos del alumno en el formulario
    public String[] obtenerDatosDeAlumno(String dniPersona) {
        String sql = "SELECT "
                + "p.idPersona, p.DNIpersona, p.primerNombre, p.segundoNombre, p.apellidoPaterno, p.apellidoMaterno, p.fechaNacimiento, p.genero, "
                + "pA.DNIpersona AS ApoderadoDNI, a.codigoAlumno, a.idApoderado "
                + "FROM Persona p "
                + "LEFT JOIN Alumno a ON p.idPersona = a.idPersona "
                + "INNER JOIN Apoderado ap ON a.idApoderado = ap.idApoderado "
                + "INNER JOIN Persona pA ON ap.idPersona = pA.idPersona "
                + "WHERE p.DNIpersona = ?";
        String[] datosPersonaAlumnoApoderado = new String[9];

        try {
            Connection con = ConexionMySQL.getInstancia().getConexion();
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, dniPersona);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                datosPersonaAlumnoApoderado[0] = rs.getString("idPersona");
                datosPersonaAlumnoApoderado[1] = rs.getString("DNIpersona");
                datosPersonaAlumnoApoderado[2] = rs.getString("primerNombre") + " " + rs.getString("segundoNombre");
                datosPersonaAlumnoApoderado[3] = rs.getString("apellidoPaterno") + " " + rs.getString("apellidoMaterno");
                datosPersonaAlumnoApoderado[4] = rs.getString("fechaNacimiento");
                datosPersonaAlumnoApoderado[5] = rs.getString("genero");

                datosPersonaAlumnoApoderado[6] = rs.getString("codigoAlumno") != null ? rs.getString("codigoAlumno") : "No disponible";
                datosPersonaAlumnoApoderado[7] = rs.getString("idApoderado") != null ? rs.getString("idApoderado") : "No disponible";

                datosPersonaAlumnoApoderado[8] = rs.getString("ApoderadoDNI") != null ? rs.getString("ApoderadoDNI") : "No disponible";
            } else {
                datosPersonaAlumnoApoderado = new String[]{"No encontrado", "", "", "", "", "", "", "", ""};
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return datosPersonaAlumnoApoderado;
    }
}

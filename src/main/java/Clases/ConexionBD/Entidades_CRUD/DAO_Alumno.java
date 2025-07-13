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
    public void Actualizar(Alumno alumno, String DNIApoderado) {
        String mensaje = "";
        String consulta = "{CALL sp_Alumno_Update(?, ?, ?, ?, ?, ?, ?, ?, ?)}";

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

        // Crear un array para almacenar los datos
        String[] datosAlumno = new String[5];

        try {
            Connection con = ConexionMySQL.getInstancia().getConexion();
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, codigoAlumno);  // Se asume que este valor es proporcionado de manera correcta

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
}

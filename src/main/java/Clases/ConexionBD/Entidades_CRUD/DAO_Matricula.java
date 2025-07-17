package Clases.ConexionBD.Entidades_CRUD;
import Clases.ClasesGestionEscolar.Matricula;
import Clases.ConexionBD.ConexionMySQL;
import javafx.scene.control.Alert;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAO_Matricula {
    private ArrayList<Matricula> listaMatricula = new ArrayList<Matricula>();

    //Metodo para nueva matricula
    public void crear(Matricula matricula) {
        String mensaje="";
        String consulta= "{CALL sp_Matricula_Insert(?,?,?,?,?,?,?)}";
        try {
            CallableStatement Statement = ConexionMySQL.getInstancia().getConexion().prepareCall(consulta);
            Statement.setString(1,matricula.getCodigoAlumno());
            Statement.setString(2,matricula.getGrado());
            Statement.setString(3,matricula.getSeccion());
            Statement.setString(4,matricula.getAñoE());
            Statement.setString(5,matricula.getTrabajador());
            Statement.setString(6,matricula.getEstadoMatricula());

            Statement.registerOutParameter(7, Types.VARCHAR);
            Statement.executeUpdate();

            mensaje=Statement.getString(7);

            mostrarMensaje(mensaje);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //Metodo para renovacion de matricula
    public void renovar(String codigoAlumno, String añoE, String codigoTrabajador, String estadoMatricula) {
        String mensaje="";
        String consulta= "{CALL sp_Matricula_Renovar(?, ?, ?, ?, ?)}";
        try{
            CallableStatement statement = ConexionMySQL.getInstancia().getConexion().prepareCall(consulta);
            statement.setString(1,codigoAlumno);
            statement.setString(2,añoE);
            statement.setString(3,codigoTrabajador);
            statement.setString(4,estadoMatricula);

            statement.registerOutParameter(5, Types.VARCHAR);
            statement.executeUpdate();

            mensaje=statement.getString(5);
            mostrarMensaje(mensaje);

        }catch (Exception e){
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

    //Metodo para listar la informacion de los matriculados
    public String[][] obtenerDatosAlumnos(String DNI) {
        String sql = "SELECT a.codigoAlumno, " +
                "perAlumno.primerNombre AS alumnoNombre, " +
                "perAlumno.segundoNombre AS alumnoSegundoNombre, " +
                "perAlumno.apellidoPaterno AS alumnoApellidoPaterno, " +
                "perAlumno.apellidoMaterno AS alumnoApellidoMaterno, " +
                "g.grado, " +
                "s.seccion, " +
                "c.nombreCurso, perAlumno.DNIpersona as DNIAlumno " +
                "FROM Profesor p " +
                "INNER JOIN Persona perProfesor ON p.idPersona = perProfesor.idPersona " +
                "INNER JOIN AsignacionProfesor pc ON p.idProfesor = pc.idProfesor " +
                "INNER JOIN Curso c ON pc.idCurso = c.idCurso " +
                "INNER JOIN Grado g ON c.idGrado = g.idGrado " +
                "INNER JOIN Matricula m ON m.idGrado = g.idGrado " +
                "INNER JOIN Seccion s ON m.idSeccion = s.idSeccion " +
                "INNER JOIN Alumno a ON m.idAlumno = a.idAlumno " +
                "INNER JOIN Persona perAlumno ON a.idPersona = perAlumno.idPersona " +
                "WHERE perProfesor.DNIpersona = ? " +
                "ORDER BY g.grado, s.seccion, c.nombreCurso, perAlumno.apellidoPaterno";

        List<String[]> listaAlumnos = new ArrayList<>();

        try (Connection con = ConexionMySQL.getInstancia().getConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, DNI);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String[] datosAlumno = new String[9];
                    datosAlumno[0] = rs.getString("codigoAlumno");
                    datosAlumno[1] = rs.getString("alumnoNombre");
                    datosAlumno[2] = rs.getString("alumnoSegundoNombre");
                    datosAlumno[3] = rs.getString("alumnoApellidoPaterno");
                    datosAlumno[4] = rs.getString("alumnoApellidoMaterno");
                    datosAlumno[5] = rs.getString("grado");
                    datosAlumno[6] = rs.getString("seccion");
                    datosAlumno[7] = rs.getString("nombreCurso");
                    datosAlumno[8] = rs.getString("DNIAlumno");
                    listaAlumnos.add(datosAlumno);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String[][] datosAlumnos = new String[listaAlumnos.size()][9];
        for (int i = 0; i < listaAlumnos.size(); i++) {
            datosAlumnos[i] = listaAlumnos.get(i);
        }
        return datosAlumnos;
    }
}

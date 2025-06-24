package Clases.ConexionBD.Entidades_CRUD;
import Clases.ClasesPersonas.Alumno;
import Clases.ConexionBD.ConexionMySQL;
import Clases.ConexionBD.DAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAO_Alumno implements DAO {
    private ArrayList<Alumno> alumnos;

    @Override
    public void Crear() {
    }

    @Override
    public void Actualizar() {

    }

    @Override
    public void Eliminar() {

    }

    @Override
    public void Listar() {
        alumnos = new ArrayList<>();
        alumnos.clear();

        String consulta = "select persona.DNIpersona, persona.primerNombre, persona.segundoNombre, persona.apellidoMaterno, persona.apellidoPaterno, persona.fechaNacimiento, persona.genero, alumno.codigoAlumno, alumno.idPersona, alumno.idApoderado  from persona inner join Alumno on persona.idPersona = alumno.idAlumno\n" +
                "order by persona.apellidoPaterno ";
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
                        resultado.getInt("idPersona"),
                        resultado.getInt("idApoderado")
                );
                alumnos.add(alumno);
            }
            resultado.close();
            comando.close();
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
                        rs.getInt("idPersona"),
                        rs.getInt("idApoderado")
                );
                alumnos.add(alumno);
            }

            rs.close();
            ps.close();
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
                        rs.getInt("idPersona"),
                        rs.getInt("idApoderado")
                );
                alumnos.add(alumno);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return alumnos;
    }
}

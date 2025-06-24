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

    public ArrayList<Alumno> getAlumnos() {
        return alumnos;
    }
}

package Clases.ConexionBD.Entidades_CRUD;

import Clases.ClasesGestionEscolar.Matricula;
import Clases.ConexionBD.ConexionMySQL;
import javafx.scene.control.Alert;

import java.sql.CallableStatement;
import java.sql.Types;
import java.util.ArrayList;

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

}

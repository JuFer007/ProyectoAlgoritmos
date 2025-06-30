package Forms;
import Clases.ConexionBD.Entidades_CRUD.DAO_Nota;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import java.util.ArrayList;

public class fmrNotas {
    public void initialize() {
        configurarTablaAlumno();
        configurarTablaCurso();
    }

    //Cajas de texto
    @FXML private TextField cajaBusqueda;
    @FXML private TextField nota1;
    @FXML private TextField nota2;
    @FXML private TextField nota3;
    @FXML private TextField promedio;

    //Tabla y Columnas
    //Primera tabla
    @FXML private TableView<Object[]> tablaAlumno;
    @FXML private TableColumn<Object[], String> columCodigo;
    @FXML private TableColumn<Object[], String> DNI;
    @FXML private TableColumn<Object[], String> Nombre;
    @FXML private TableColumn<Object[], String> columapellidoP;
    @FXML private TableColumn<Object[], String> columapellidoM;
    @FXML private TableColumn<Object[], String> columNumMatricula;

    //Segunda tabla
    @FXML private TableView<Object[]> tablaCursos;
    @FXML private TableColumn<Object[], String> codigoCurso;
    @FXML private TableColumn<Object[], String> curso;
    @FXML private TableColumn<Object[], String> Columnota;
    @FXML private TableColumn<Object[], String> columTipoNota;

    //Botones
    @FXML private Button btnRegistrar;
    @FXML private Button btnModificar;

    //Metodo para listar a los estudiantes
    private void listarDatosEstudiantes() {

    }

    //Metodo para configurar la tabla de alumno
    private void configurarTablaAlumno() {
        columCodigo.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue()[0])));
        DNI.setCellValueFactory(cellData -> new SimpleStringProperty((String) cellData.getValue()[1]));
        Nombre.setCellValueFactory(cellData -> new SimpleStringProperty((String) cellData.getValue()[2]));
        columapellidoP.setCellValueFactory(cellData -> new SimpleStringProperty((String) cellData.getValue()[4]));
        columapellidoM.setCellValueFactory(cellData -> new SimpleStringProperty((String) cellData.getValue()[5]));
        columNumMatricula.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue()[6])));

        listarDatosEstudiantes();
    }

    //Metodo para configurar la tabla de cursos
    private void configurarTablaCurso() {
        codigoCurso.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue()[0])));
        curso.setCellValueFactory(cellData -> new SimpleStringProperty((String) cellData.getValue()[1]));
        Columnota.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue()[3])));
        columTipoNota.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue()[3])));
    }

    //Metodo para registrar nota
    private void registrarNota() {


    }

    //Metodo para registrar varias notas
    private void registarVariasNotas() {

    }
}

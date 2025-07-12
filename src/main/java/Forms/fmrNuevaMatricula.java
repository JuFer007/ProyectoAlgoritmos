package Forms;

import Clases.ClasesPersonas.Alumno;
import Clases.ClasesPersonas.Apoderado;
import Clases.ConexionBD.Entidades_CRUD.DAO_Alumno;
import Clases.ConexionBD.Entidades_CRUD.DAO_Apoderado;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;

import java.time.LocalDate;


public class fmrNuevaMatricula {
    @FXML
    public void initialize() {
        configuracionCombobox();
        soloLectura();
        deshabilitarCamposRenovacion();
        buscarAlumno();
    }

    //Seccion alumno
    @FXML private TextField codigoAlumno;
    @FXML private TextField primerNombreAlumno;
    @FXML private TextField segundoNombreAlumno;
    @FXML private TextField apellidomaternoAlumno;
    @FXML private TextField apellidoPaternoAlumno;
    @FXML private TextField dniAlumno;
    @FXML private DatePicker fechaNacimientoAlumno;
    @FXML private TextField generoAlumno;

    //Seccion apoderado
    @FXML private TextField nombresApoderado;
    @FXML private TextField apellidosApoderado;

    //Seccion de matricula
    @FXML private ComboBox<String> comboGrados ;
    @FXML private ComboBox<String> comboSeccion;
    @FXML private ComboBox<String> comboAños;
    @FXML private DatePicker fechaMatricula;

    //Seccion responsable
    @FXML private TextField codigoResponsable;
    @FXML private TextField nombresResponsable;
    @FXML private TextField apellidosResponsable;
    @FXML private CheckBox renovacionMatricula;

    //Botones de Accion
    @FXML private Button BtnRealizarMatricula;
    @FXML private Button btnResumenMatricula;

    private void configuracionCombobox() {
        ObservableList<String> grados = FXCollections.observableArrayList("Primer", "Segundo", "Tercer", "Cuarto", "Quinto");
        ObservableList<String> seccion = FXCollections.observableArrayList("A", "B", "C");
        ObservableList<String> años = FXCollections.observableArrayList("2025","2026");
        comboGrados.setItems(grados);
        comboSeccion.setItems(seccion);
        comboAños.setItems(años);
    }

    private void soloLectura() {
        primerNombreAlumno.setEditable(false);
        primerNombreAlumno.setMouseTransparent(true);
        segundoNombreAlumno.setEditable(false);
        segundoNombreAlumno.setMouseTransparent(true);
        apellidoPaternoAlumno.setEditable(false);
        apellidoPaternoAlumno.setMouseTransparent(true);
        apellidomaternoAlumno.setEditable(false);
        apellidoPaternoAlumno.setMouseTransparent(true);
        dniAlumno.setEditable(false);
        dniAlumno.setMouseTransparent(true);
        generoAlumno.setEditable(false);
        generoAlumno.setMouseTransparent(true);
        nombresApoderado.setEditable(false);
        nombresApoderado.setMouseTransparent(true);
        apellidosApoderado.setEditable(false);
        apellidosApoderado.setMouseTransparent(true);
        nombresResponsable.setEditable(false);
        nombresResponsable.setMouseTransparent(true);
        apellidosResponsable.setEditable(false);
        apellidosResponsable.setMouseTransparent(true);
        fechaMatricula.setValue(LocalDate.now());
        fechaNacimientoAlumno.setEditable(false);
        fechaNacimientoAlumno.setMouseTransparent(true);
        fechaMatricula.setEditable(false);
        fechaMatricula.setMouseTransparent(true);
    }

    private void deshabilitarCamposRenovacion() {
        renovacionMatricula.setOnAction(e -> {
                boolean action = renovacionMatricula.isSelected();
                comboGrados.getSelectionModel().clearSelection();
                comboSeccion.getSelectionModel().clearSelection();
                comboAños.getSelectionModel().clearSelection();
                comboGrados.setMouseTransparent(action);
                comboSeccion.setMouseTransparent(action);
                comboAños.setMouseTransparent(action);
        });
    }

    private void mostrarDatosAlumno(){
        String codigo = codigoAlumno.getText().trim();
        DAO_Alumno daoAlumno = new DAO_Alumno();
        DAO_Apoderado daoApoderado = new DAO_Apoderado();
        if (codigo.isEmpty()){
            mostrarError("Debe ingresar el codigo de alumno");
        }

        Alumno alumno = daoAlumno.buscarPorCodigo(codigo);
        if (alumno == null){
            mostrarError("No se encontro el alumno");
            limpiarCampos();
            return;
        }


        //mostrar los datos Alumno
        primerNombreAlumno.setText(alumno.getPrimernombre());
        segundoNombreAlumno.setText(alumno.getSegundonombre());
        apellidoPaternoAlumno.setText(alumno.getApellidopaterno());
        apellidomaternoAlumno.setText(alumno.getApellidomaterno());
        dniAlumno.setText(alumno.getDnipersona());
        generoAlumno.setText(alumno.getGenero());

        //BuscarApoderado
        Apoderado apoderado = daoApoderado.buscarPorId(alumno.getIdApoderado());
        if (apoderado == null){
            mostrarError("No se encontro el apoderado");
        }else {
            nombresApoderado.setText(apoderado.getPrimernombre()+" "+apoderado.getSegundonombre());
            apellidosApoderado.setText(apoderado.getApellidopaterno()+apoderado.getApellidomaterno());
        }

    }

    private void buscarAlumno(){
        codigoAlumno.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                mostrarDatosAlumno();
            }
        });
    }


    private void limpiarCampos() {
        codigoAlumno.clear();
        primerNombreAlumno.setText("");
        segundoNombreAlumno.setText("");
        apellidoPaternoAlumno.setText("");
        apellidomaternoAlumno.setText("");
        dniAlumno.setText("");
        fechaNacimientoAlumno.setValue(null);
        generoAlumno.setText("");
        nombresApoderado.setText("");
        apellidosApoderado.setText("");
        comboGrados.getSelectionModel().clearSelection();
        comboSeccion.getSelectionModel().clearSelection();
        comboAños.getSelectionModel().clearSelection();
        fechaMatricula.setValue(null);
        codigoResponsable.clear();
        nombresResponsable.setText("");
        apellidosResponsable.setText("");
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}

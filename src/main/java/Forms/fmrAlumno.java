package Forms;
import Clases.ClasesPersonas.Alumno;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;

public class fmrAlumno {
    //Constructor
    @FXML
    public void initialize() {
        configuracionCombox();
    }

    //Cajas de texto del form
    @FXML private TextField cajaBusqueda;
    @FXML private TextField cajaDNI;
    @FXML private TextField cajaPrimerNombre;
    @FXML private TextField cajaSegundoNombre;
    @FXML private TextField cajaApellidoPaterno;
    @FXML private TextField cajaApellidoMaterno;
    @FXML private TextField cajaGenero;

    //ComboBox de para filtros de busqueda
    @FXML private ComboBox<String> comboGradoB;
    @FXML private ComboBox<String> comboSeccionB;

    //ComboBox para insercion de nuevo alumno
    @FXML private ComboBox<String> comboGrado;
    @FXML private ComboBox<String> comboSeccion;
    @FXML private ComboBox<String> comboApoderado;

    //Fecha nacimiento
    @FXML private DatePicker cajaFechaNacimiento;

    //Botones
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnModificiar;
    @FXML private Button btnAgregarApoderado;

    //Tabla de alumnos
    @FXML private TableView<Alumno> tablaAlumnos;

    //Validacion de campos para registro nuevo alumno
    private boolean validarCamposDeIngreso() {
        String mensaje = "";

        //Validar DNI
        if (cajaDNI.getText().isEmpty()) {
            mensaje += "El campo DNI no puede estar vacío.\n";
        } else if (!cajaDNI.getText().matches("\\d{8}")) {
            mensaje += "El DNI debe tener exactamente 8 dígitos.\n";
        }

        //Validar nombres
        if (cajaPrimerNombre.getText().isEmpty() || cajaSegundoNombre.getText().isEmpty()) {
            mensaje += "Los nombres no pueden estar vacíos.\n";
        } else if (!cajaPrimerNombre.getText().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+") ||
                !cajaSegundoNombre.getText().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            mensaje += "Los nombres solo pueden contener letras.\n";
        }

        //Validar apellidos
        if (cajaApellidoPaterno.getText().isEmpty() || cajaApellidoMaterno.getText().isEmpty()) {
            mensaje += "Los apellidos no pueden estar vacíos.\n";
        } else if (!cajaApellidoPaterno.getText().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+") ||
                !cajaApellidoMaterno.getText().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            mensaje += "Los apellidos solo pueden contener letras.\n";
        }

        //Validar género
        if (cajaGenero.getText().isEmpty()) {
            mensaje += "El campo género es obligatorio.\n";
        } else if (!cajaGenero.getText().equalsIgnoreCase("masculino") &&
                !cajaGenero.getText().equalsIgnoreCase("femenino")) {
            mensaje += "El género debe ser 'Masculino' o 'Femenino'.\n";
        }

        //Muestra mensaje de errores en validacion
        if (!mensaje.isEmpty()) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Validación de campos");
            alerta.setHeaderText("Se encontraron errores:");
            alerta.setContentText(mensaje);
            alerta.showAndWait();
            return false;
        }
        return true;
    }

    //Configuracion de los comboBox
    private void configuracionCombox() {
        ObservableList<String> grados = FXCollections.observableArrayList("Primer Grado", "Segundo Grado", "Tercer Grado", "Cuarto Grado", "Quinto Grado");
        ObservableList<String> secciones = FXCollections.observableArrayList("A", "B", "C");

        //Asignacion de valores del list de tipo String
        comboGradoB.setItems(grados);
        comboGrado.setItems(grados);
        comboSeccionB.setItems(secciones);
        comboSeccion.setItems(secciones);
    }

    //Validar fecha de nacimiento
    private Date valorFechaNacimiento() {
        if (cajaFechaNacimiento.getValue() == null) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Validación");
            alerta.setHeaderText("Fecha de nacimiento requerida");
            alerta.setContentText("Debe seleccionar una fecha de nacimiento.");
            alerta.showAndWait();
            return null;
        } else {
            LocalDate fechaLocal = cajaFechaNacimiento.getValue();
            Date fechaConvertida = (Date) Date.from(fechaLocal.atStartOfDay(ZoneId.systemDefault()).toInstant());
            return fechaConvertida;
        }
    }

    private void LimpiarCampos() {
        cajaDNI.clear();
        cajaPrimerNombre.clear();
        cajaSegundoNombre.clear();
        cajaApellidoPaterno.clear();
        cajaApellidoMaterno.clear();
        cajaGenero.clear();
        cajaFechaNacimiento.setValue(null);
        comboGrado.getSelectionModel().clearSelection();
        comboSeccion.getSelectionModel().clearSelection();
        comboApoderado.getSelectionModel().clearSelection();
    }

    //Agregar alumno
    private void agregarAlumno() {
        if (!validarCamposDeIngreso()) {
            return;
        }

        Date fechaNacimiento = valorFechaNacimiento();
        if (fechaNacimiento == null) {
            return;
        }

        String DNI = cajaDNI.getText();
        String PrimerNombre = cajaPrimerNombre.getText();
        String SegundoNombre = cajaSegundoNombre.getText();
        String ApellidoPaterno = cajaApellidoPaterno.getText();
        String ApellidoMaterno = cajaApellidoMaterno.getText();
        String Genero = cajaGenero.getText();
        String FechaNacimiento = cajaFechaNacimiento.getValue().toString();
        String grado = comboGrado.getSelectionModel().getSelectedItem().toString();
        String seccion = comboSeccion.getSelectionModel().getSelectedItem().toString();
        String Apoderado = comboApoderado.getSelectionModel().getSelectedItem().toString();

        // FALTA TERMINAR
    }
}

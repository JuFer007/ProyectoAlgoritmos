package Forms;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class fmrTrabajador {
    //Constructor
    @FXML
    private void initialize() {
        configuracionCombox();
    }

    @FXML
    private TextField cajaBusqueda;

    // Tabla
    @FXML
    private TableView<?> tablaProfesores;

    // Campos de texto
    @FXML
    private TextField cajaDNI;

    @FXML
    private TextField cajaPrimerNombre;

    @FXML
    private TextField cajaSegundoNombre;

    @FXML
    private TextField cajaApellidoPaterno;

    @FXML
    private TextField cajaApellidoMaterno;

    @FXML
    private TextField cajaGenero;

    // Fecha de nacimiento
    @FXML
    private DatePicker cajaFechaNacimiento;

    // Combos
    @FXML
    private ComboBox<String> cajaTipoTrabajo;

    @FXML
    private ComboBox<String> cajaTurnoAsignado;

    // Botones
    @FXML
    private Button btnNuevo;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnModificar;

    //Metodo para validar campos
    private boolean validarCampos() {
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
        } else if (!cajaPrimerNombre.getText().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+") || !cajaSegundoNombre.getText().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            mensaje += "Los nombres solo pueden contener letras.\n";
        }

        //Validar apellidos
        if (cajaApellidoPaterno.getText().isEmpty() || cajaApellidoPaterno.getText().isEmpty()) {
            mensaje += "Los apellidos no pueden estar vacíos.\n";
        } else if (!cajaApellidoPaterno.getText().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+") ||
                !cajaApellidoPaterno.getText().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
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
    private void configuracionCombox() {
        ObservableList<String> tipoDeTrabajo = FXCollections.observableArrayList("Administrativo", "Servicios Educativos", "Seguridad", "Servicios Generales", "Apoyo Psicologico");
        ObservableList<String> turnoAsignado = FXCollections.observableArrayList( "Mañana", "Tarde", "Noche");

        //Asignacion de valores del list de tipo String
        cajaTipoTrabajo.setItems(tipoDeTrabajo);
        cajaTurnoAsignado.setItems(turnoAsignado);
    }
}

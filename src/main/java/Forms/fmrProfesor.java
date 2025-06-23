package Forms;

import Clases.ClasesPersonas.Profesor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;

public class fmrProfesor {
    @FXML
    private void initializable(){
        configuracionCombox();
    }

    //Campo de búsqueda
    @FXML
    private TextField cajaBusqueda;

    //Tabla de profesores
    @FXML
    private TableView<Profesor> tablaProfesores;

    //Campos de texto
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

    // ComboBoxes
    @FXML
    private ComboBox<String> comboEpecialidad;

    @FXML
    private ComboBox<String> comboGradoAcademico;

    // Selector de fecha
    @FXML
    private DatePicker cajaFechaNacimiento;

    // Botones
    @FXML
    private Button btnNuevo;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnModificiar;

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
        ObservableList<String> gradoAcademico = FXCollections.observableArrayList("Bachiller", "Linciado", "Magister", "Doctorado");
        ObservableList<String> especialidad = FXCollections.observableArrayList( "Aritmética", "Arte", "Ciencia y Tecnología", "Comunicación", "Educación Física", "Física", "Historia", "Inglés", "Literatura", "Matemática", "Química", "Religión", "Álgebra");

        //Asignacion de valores del list de tipo String
        comboGradoAcademico.setItems(gradoAcademico);
        comboEpecialidad.setItems(especialidad);
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
        comboEpecialidad.getSelectionModel().clearSelection();
        comboGradoAcademico.getSelectionModel().clearSelection();
    }
}

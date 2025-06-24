package Forms;
import Clases.ClasesPersonas.Alumno;
import Clases.ConexionBD.Entidades_CRUD.DAO_Alumno;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
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
        ConfigurarTabla();
        listarAlumos();
    }

    //Columnas de la tabla
    @FXML private TableColumn<Object[], String> codigoAlumno;
    @FXML private TableColumn<Object[], String> DNIAlumno;
    @FXML private TableColumn<Object[], String> nombreA;
    @FXML private TableColumn<Object[], String> apellidoPaternoA;
    @FXML private TableColumn<Object[], String> apellidoMaternoA;
    @FXML private TableColumn<Object[], Integer> edadA;
    @FXML private TableColumn<Object[], String> generoA;

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
    @FXML private TableView<Object[]> tablaAlumnos;

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

    //Metodo para configurar la tabla
    private void ConfigurarTabla() {
        codigoAlumno.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[0].toString()));
        DNIAlumno.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[1].toString()));
        nombreA.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[2].toString()));
        apellidoPaternoA.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[3].toString()));
        apellidoMaternoA.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[4].toString()));
        edadA.setCellValueFactory(data -> new SimpleIntegerProperty((int) data.getValue()[5]).asObject());
        generoA.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[6].toString()));

        //Evitar que se muevan las columnas
        for (TableColumn<?, ?> col : tablaAlumnos.getColumns()) {
            col.setReorderable(false);
        }

        //Evitar el scroll horizontal
        tablaAlumnos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    //Metodo para listar los alumnos
    private void listarAlumos() {
        DAO_Alumno daoAlumno = new DAO_Alumno();
        daoAlumno.Listar();

        ObservableList<Object[]> datosAlumno = FXCollections.observableArrayList();
        for (Alumno a : daoAlumno.getAlumnos()) {
            datosAlumno.add(a.convertir());
        }
        tablaAlumnos.setItems(datosAlumno);
    }
}

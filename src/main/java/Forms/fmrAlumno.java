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
import java.util.ArrayList;

public class fmrAlumno {
    @FXML
    public void initialize() {
        configuracionCombox();
        ConfigurarTabla();
        listarAlumos();
        cajaBusqueda.textProperty().addListener((obs, oldText, newText) -> aplicarFiltrosCombinados());
        comboGradoB.valueProperty().addListener((obs, oldVal, newVal) -> aplicarFiltrosCombinados());
        comboSeccionB.valueProperty().addListener((obs, oldVal, newVal) -> aplicarFiltrosCombinados());
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
    @FXML private TextField DNIapoderado;

    //ComboBox de para filtros de busqueda
    @FXML private ComboBox<String> comboGradoB;
    @FXML private ComboBox<String> comboSeccionB;

    //ComboBox para insercion de nuevo alumno
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
        ObservableList<String> grados = FXCollections.observableArrayList("Primer", "Segundo", "Tercer", "Cuarto", "Quinto");
        ObservableList<String> secciones = FXCollections.observableArrayList("A", "B", "C");
        comboGradoB.setItems(grados);
        comboSeccionB.setItems(secciones);
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
        comboApoderado.getSelectionModel().clearSelection();
    }

    //Agregar alumno
    public void agregarAlumno() {
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
        Date FechaNacimiento = Date.valueOf(cajaFechaNacimiento.getValue().toString());
        String dniApoderado = DNIapoderado.getText();

        Alumno alumno = new Alumno(DNI, PrimerNombre, SegundoNombre, ApellidoPaterno, ApellidoMaterno, FechaNacimiento, Genero);
        DAO_Alumno daoAlumno = new DAO_Alumno();
        daoAlumno.Crear(alumno, dniApoderado);

        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Información");
        alerta.setHeaderText("Alumno registrado correctamente");
        alerta.showAndWait();

        LimpiarCampos();
        listarAlumos();
    }

    //Modificar alumno
    public void modificarAlumno() {
        //Obtener fila seleccionada
        Object[] filaSeleccionada = tablaAlumnos.getSelectionModel().getSelectedItem();

        if (filaSeleccionada == null) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Selección Requerida");
            alerta.setHeaderText("No se ha seleccionado ningún alumno");
            alerta.setContentText("Por favor, seleccione un alumno para modificar.");
            alerta.showAndWait();
            return;
        }

        String dniAlumno = filaSeleccionada[1].toString();
        cajaDNI.setText(dniAlumno);
        cajaDNI.setEditable(false);

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
        Date FechaNacimiento = Date.valueOf(cajaFechaNacimiento.getValue().toString());
        String dniApoderado = DNIapoderado.getText();

        Alumno alumno = new Alumno(DNI, PrimerNombre, SegundoNombre, ApellidoPaterno, ApellidoMaterno, FechaNacimiento, Genero);
        DAO_Alumno daoAlumno = new DAO_Alumno();

        daoAlumno.Actualizar(alumno, dniApoderado);

        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Información");
        alerta.setHeaderText("Datos actualizados correctamente");
        alerta.showAndWait();

        LimpiarCampos();
        listarAlumos();
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

    //Metodo para aplicar filtros de busqueda y los comboBox
    private void aplicarFiltrosCombinados() {
        String texto = cajaBusqueda.getText();
        String grado = comboGradoB.getValue();
        String seccion = comboSeccionB.getValue();

        DAO_Alumno dao = new DAO_Alumno();
        ObservableList<Object[]> resultados = FXCollections.observableArrayList();
        ArrayList<Alumno> lista;

        if (grado != null && seccion != null) {
            lista = dao.alumnosPorGradoYSeccion(grado, seccion);
        } else if (grado != null) {
            lista = dao.alumnosPorGrado(grado);
        } else {
            dao.Listar();
            lista = dao.getAlumnos();
        }

        for (Alumno alumno : lista) {
            String nombreCompleto = (alumno.getPrimernombre() + " " +
                    alumno.getSegundonombre() + " " +
                    alumno.getApellidopaterno() + " " +
                    alumno.getApellidomaterno()).toLowerCase();

            if (texto == null || texto.isEmpty() || nombreCompleto.contains(texto.toLowerCase())) {
                resultados.add(alumno.convertir());
            }
        }
        tablaAlumnos.setItems(resultados);
    }
}

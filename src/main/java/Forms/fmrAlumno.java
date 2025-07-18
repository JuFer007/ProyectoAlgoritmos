package Forms;
import Clases.ClasesPersonas.Alumno;
import Clases.ConexionBD.Entidades_DAO.DAO_Alumno;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
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
        tablaAlumnos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                String dniSeleccionado = (String) tablaAlumnos.getSelectionModel().getSelectedItem()[1];
                cargarDatosAlumno(dniSeleccionado);
            }
        });
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

    //Fecha nacimiento
    @FXML private DatePicker cajaFechaNacimiento;

    //Botones
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnModificiar;
    @FXML private Button btnAgregarApoderado;

    //Tabla de alumnos
    @FXML private TableView<Object[]> tablaAlumnos;

    //Anchor Pane principal
    @FXML AnchorPane AnchorPanePrincipal;

    //Metodo para redigirir formulario
    @FXML
    public void redegirirFormularioApoderado() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Formularios/GestionPersonas/Apoderado.fxml"));
        Parent root = loader.load();
        AnchorPanePrincipal.getChildren().setAll(root);
        AnchorPanePrincipal.setTopAnchor(root, 0.0);
        AnchorPanePrincipal.setBottomAnchor(root, 0.0);
        AnchorPanePrincipal.setLeftAnchor(root, 0.0);
        AnchorPanePrincipal.setRightAnchor(root, 0.0);
    }

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
        ObservableList<String> grados = FXCollections.observableArrayList("Todos", "Primer", "Segundo", "Tercer", "Cuarto", "Quinto");
        ObservableList<String> secciones = FXCollections.observableArrayList("Todos", "A", "B", "C");
        comboGradoB.setItems(grados);
        comboSeccionB.setItems(secciones);

        comboGradoB.getSelectionModel().select(0);
        comboSeccionB.getSelectionModel().select(0);
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
            Date fechaConvertida = java.sql.Date.valueOf(fechaLocal);
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
        DNIapoderado.clear();
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

    //Metodo para modificar alumno
    public void modificarAlumno() {
        if (tablaAlumnos.getSelectionModel().getSelectedItem() == null) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Advertencia");
            alerta.setHeaderText("No se ha seleccionado ninguna fila");
            alerta.setContentText("Por favor, selecciona un alumno de la tabla para poder modificar.");
            alerta.showAndWait();
            return;
        }

        if (!validarCamposDeIngreso()) {
            return;
        }

        String dniPersona = cajaDNI.getText();
        String primerNombre = cajaPrimerNombre.getText();
        String segundoNombre = cajaSegundoNombre.getText();
        String apellidoPaterno = cajaApellidoPaterno.getText();
        String apellidoMaterno = cajaApellidoMaterno.getText();
        String genero = cajaGenero.getText();

        DAO_Alumno daoAlumno = new DAO_Alumno();
        daoAlumno.Actualizar(primerNombre, segundoNombre, apellidoPaterno, apellidoMaterno, genero, dniPersona);

        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Información");
        alerta.setHeaderText("Datos de alumno modificados correctamente");
        alerta.showAndWait();

        LimpiarCampos();
        listarAlumos();
    }

    //Metodo para cargar los datos del alumno
    private void cargarDatosAlumno(String dni) {
        LimpiarCampos();
        DAO_Alumno daoAlumno = new DAO_Alumno();
        String[] datos = daoAlumno.obtenerDatosDeAlumno(dni);
        if (datos[0] != null && !datos[0].equals("No encontrado")) {
            cajaDNI.setText(datos[1]);
            cajaPrimerNombre.setText(datos[2].split(" ")[0]);
            cajaSegundoNombre.setText(datos[2].split(" ").length > 1 ? datos[2].split(" ")[1] : "");
            cajaApellidoPaterno.setText(datos[3].split(" ")[0]);
            cajaApellidoMaterno.setText(datos[3].split(" ").length > 1 ? datos[3].split(" ")[1] : "");
            cajaGenero.setText(datos[5]);

            if (datos[4] != null) {
                cajaFechaNacimiento.setValue(java.time.LocalDate.parse(datos[4]));
            }
            DNIapoderado.setText(datos[8]);
        }
        cajaDNI.setEditable(false);
        cajaFechaNacimiento.setEditable(false);
        DNIapoderado.setEditable(false);
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

        if ("Todos".equals(grado) || "Todos".equals(seccion)) {
            dao.Listar();
            lista = dao.getAlumnos();
        } else if (grado != null && seccion != null) {
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

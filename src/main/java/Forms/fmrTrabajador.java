package Forms;
import Clases.ClasesPersonas.Trabajador;
import Clases.ConexionBD.Entidades_CRUD.DAO_Trabajador;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;

public class fmrTrabajador {
    @FXML
    private void initialize() {
        configuracionCombox();
        ConfigurarTabla();
        listarTrabajadores();
        cajaBusqueda.textProperty().addListener((observable, oldValue, newValue) -> {buscarProfesor(newValue);});
    }

    //Campo de búsqueda
    @FXML private TextField cajaBusqueda;

    //Tabla de trabajadores
    @FXML private TableView<Object[]> tablaTrabajador;

    //Columnas de la tabla
    @FXML private TableColumn<Object[], String> codigoTrabajador;
    @FXML private TableColumn<Object[], String> DNITrabajador;
    @FXML private TableColumn<Object[], String> nombreT;
    @FXML private TableColumn<Object[], String> apellidoPatT;
    @FXML private TableColumn<Object[], String> apellidoMatT;
    @FXML private TableColumn<Object[], String> tipoTrabajo;
    @FXML private TableColumn<Object[], String> cargo;

    //Campos de insercion
    @FXML private TextField cajaDNI;
    @FXML private TextField cajaPrimerNombre;
    @FXML private TextField cajaSegundoNombre;
    @FXML private TextField cajaApellidoPaterno;
    @FXML private TextField cajaApellidoMaterno;
    @FXML private TextField cajaGenero;
    @FXML private DatePicker cajaFechaNacimiento;

    //Campos de combox
    @FXML private ComboBox<String> cajaTipoTrabajo;
    @FXML private ComboBox<String> cajaTurnoAsignado;
    @FXML private ComboBox<String> cargoAsignado;

    //Botonoes
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnModificiar;

    private boolean validarCamposDeIngreso() {
        String mensaje = "";

        //Validacion de DNI
        if (cajaDNI.getText().isEmpty()) {
            mensaje += "El campo DNI no puede estar vacío.\n";
        } else if (!cajaDNI.getText().matches("\\d{8}")) {
            mensaje += "El DNI debe tener exactamente 8 dígitos.\n";
        }

        //Validar primer nombre y segundo nombre
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

        //Validar tipo de trabajo
        if (cajaTipoTrabajo.getValue() == null) {
            mensaje += "El campo tipo de trabajo es obligatorio.\n";
        }

        //Validar turno asignado
        if (cajaTurnoAsignado.getValue() == null) {
            mensaje += "El campo turno asignado es obligatorio.\n";
        }

        //Validar cargo seleccionado
        if (cargoAsignado.getValue() == null) {
            mensaje += "El campo de cargos es obligatorio.\n";
        }

        //Validar fecha de nacimiento
        if (cajaFechaNacimiento.getValue() == null) {
            mensaje += "La fecha de nacimiento es obligatoria.\n";
        }

        // Muestra mensaje de errores en validación
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


    //Metodo para configurar los combox
    private void configuracionCombox() {
        ObservableList<String> tipoDeTrabajo = FXCollections.observableArrayList("Administrativo", "Servicios Educativos", "Seguridad", "Servicios Generales", "Apoyo Psicologico");
        ObservableList<String> turnoAsignado = FXCollections.observableArrayList( "Mañana", "Tarde", "Noche");
        ObservableList<String> cargos = FXCollections.observableArrayList("Vigilante", "Secretaria", "Secretario", "Auxiliar Administrativo", "Bibliotecario", "Psicologo", "Psicologa", "Personal de Limpieza");

        //Asignacion de valores del list de tipo String
        cajaTipoTrabajo.setItems(tipoDeTrabajo);
        cajaTurnoAsignado.setItems(turnoAsignado);
        cargoAsignado.setItems(cargos);
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

    //Limpiar campos
    private void LimpiarCampos() {
        cajaDNI.clear();
        cajaPrimerNombre.clear();
        cajaSegundoNombre.clear();
        cajaApellidoPaterno.clear();
        cajaApellidoMaterno.clear();
        cajaGenero.clear();
        cajaFechaNacimiento.setValue(null);
        cajaTipoTrabajo.getSelectionModel().clearSelection();
        cajaTurnoAsignado.getSelectionModel().clearSelection();
        cargoAsignado.getSelectionModel().clearSelection();
    }

    //Modificar trabajador
    public void modificarTrabajador() {
        Object[] filaSeleccionada = tablaTrabajador.getSelectionModel().getSelectedItem();

        if (filaSeleccionada == null) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Selección Requerida");
            alerta.setHeaderText("No se ha seleccionado ningún trabajador");
            alerta.setContentText("Por favor, seleccione un trabajador para modificar.");
            alerta.showAndWait();
            return;
        }

        String dniTrabajador = filaSeleccionada[1].toString();
        cajaDNI.setText(dniTrabajador);
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
        String TipoTrabajo = cajaTipoTrabajo.getValue();
        String TurnoAsignado = cajaTurnoAsignado.getValue();
        String Cargo = cargoAsignado.getValue();

        Trabajador trabajador = new Trabajador(DNI, PrimerNombre, SegundoNombre, ApellidoPaterno, ApellidoMaterno, FechaNacimiento, Genero, TipoTrabajo, TurnoAsignado, Cargo);

        DAO_Trabajador daoTrabajador = new DAO_Trabajador();
        daoTrabajador.Actualizar(trabajador);

        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Información");
        alerta.setHeaderText("Datos actualizados correctamente");
        alerta.showAndWait();

        LimpiarCampos();
        listarTrabajadores();
    }

    //Crear trabajador
    public void insertarTrabajador() {
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
        String TipoTrabajo = cajaTipoTrabajo.getValue();
        String TurnoAsignado = cajaTurnoAsignado.getValue();
        String Cargo = cargoAsignado.getValue();

        Trabajador trabajador = new Trabajador(DNI, PrimerNombre, SegundoNombre, ApellidoPaterno, ApellidoMaterno, FechaNacimiento, Genero, TipoTrabajo, TurnoAsignado, Cargo);

        DAO_Trabajador daoTrabajador = new DAO_Trabajador();
        daoTrabajador.Crear(trabajador);

        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Información");
        alerta.setHeaderText("Trabajador registrado correctamente");
        alerta.showAndWait();

        LimpiarCampos();
        listarTrabajadores();
    }

    //Métodos para configurar la tabla
    private void ConfigurarTabla() {
        codigoTrabajador.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[0].toString()));
        DNITrabajador.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[1].toString()));
        nombreT.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[2].toString()));
        apellidoPatT.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[3].toString()));
        apellidoMatT.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[4].toString()));
        tipoTrabajo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[5].toString()));
        cargo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[6].toString()));

        for (TableColumn<?, ?> col : tablaTrabajador.getColumns()) {
            col.setReorderable(false);
        }

        tablaTrabajador.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    //Metodo para listar trabajadores en la tabla
    private void listarTrabajadores() {
        DAO_Trabajador daoTrabajador = new DAO_Trabajador();
        daoTrabajador.Listar();

        ObservableList<Object[]> datosTrabajador = FXCollections.observableArrayList();
        for (Trabajador t : daoTrabajador.getTrabajadores()) {
            datosTrabajador.add(t.convertir());
        }
        tablaTrabajador.setItems(datosTrabajador);
    }

    //Metodo de busqueda dinamica
    private void buscarProfesor(String textoIngresado) {
        DAO_Trabajador daoTrabajador = new DAO_Trabajador();
        daoTrabajador.Listar();

        ObservableList<Object[]> resultadoFiltrados = FXCollections.observableArrayList();

        for (Trabajador trabajador : daoTrabajador.getTrabajadores()) {
            Object[] datos = trabajador.Convertir();

            String nombreCompleto = (trabajador.getPrimernombre() + " " + trabajador.getSegundonombre() + " " + trabajador.getApellidopaterno() + " " + trabajador.getApellidomaterno()).toLowerCase();

            if (textoIngresado == null || textoIngresado.isEmpty() || nombreCompleto.contains(textoIngresado.toLowerCase())) {
                resultadoFiltrados.add(datos);
            }
        }
        tablaTrabajador.setItems(resultadoFiltrados);
    }
}

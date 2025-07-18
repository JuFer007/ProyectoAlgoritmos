package Forms;
import Clases.ClasesPersonas.Apoderado;
import Clases.ConexionBD.Entidades_DAO.DAO_Apoderado;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.Date;
import java.time.LocalDate;

public class fmrApoderado {
    @FXML
    public void initialize() {
        configurarComboBox();
        configurarTabla();
        listarApoderados();
        busqueda.textProperty().addListener((observable, oldValue, newValue) -> {buscarApoderado();});
        tablaApoderados.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                String dniSeleccionado = (String) tablaApoderados.getSelectionModel().getSelectedItem()[0];
                cargarDatosApoderado(dniSeleccionado);
            }
        });
    }

    //Columnas de la tabla
    @FXML private TableColumn<Object[], String> columDNI;
    @FXML private TableColumn<Object[], String> columNombre;
    @FXML private TableColumn<Object[], String > columApellidoP;
    @FXML private TableColumn<Object[], String> columnApellidoM;
    @FXML private TableColumn<Object[], String> columTelef;
    @FXML private TableColumn<Object[], String> columCorreoE;

    //Caja de busqeuda dinamica
    @FXML private TextField busqueda;

    //Tabla de apoderados
    @FXML private TableView<Object[]> tablaApoderados;

    //Cajas de ingreso de datos
    @FXML private TextField cajaDNI;
    @FXML private TextField cajaPrimerNombre;
    @FXML private TextField cajaSegundoNombre;
    @FXML private TextField cajaApellidoP;
    @FXML private TextField cajaApellidoM;
    @FXML private TextField cajaCorreoE;
    @FXML private TextField cajaTelefono;
    @FXML private TextField cajaGenero;

    //ComboBox para ingreso de datos
    @FXML private ComboBox<String> cajaParentesco;

    //Caja de fecha de nacimiento
    @FXML private DatePicker cajaFechaNac;

    //Botones de interaccion
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnModificar;

    //Validacion de campos para registro nuevo apoderado
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
        if (cajaApellidoM.getText().isEmpty() || cajaApellidoP.getText().isEmpty()) {
            mensaje += "Los apellidos no pueden estar vacíos.\n";
        } else if (!cajaApellidoP.getText().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+") ||
                !cajaApellidoM.getText().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
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

    //Configuracion de los combobox
    private void configurarComboBox() {
        ObservableList<String> parentesco = FXCollections.observableArrayList("Padre", "Madre", "Tutor legal");
        cajaParentesco.setItems(parentesco);
    }

    //Validar fecha de nacimiento
    private Date valorFechaNacimiento() {
        if (cajaFechaNac.getValue() == null) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Validación");
            alerta.setHeaderText("Fecha de nacimiento requerida");
            alerta.setContentText("Debe seleccionar una fecha de nacimiento.");
            alerta.showAndWait();
            return null;
        } else {
            LocalDate fechaLocal = cajaFechaNac.getValue();
            Date fechaConvertida = java.sql.Date.valueOf(fechaLocal);
            return fechaConvertida;
        }
    }

    //Metodo para cargar datos en las cajas de texto
    private void cargarDatosApoderado(String dniApoderado) {
        DAO_Apoderado daoApoderado = new DAO_Apoderado();

        String[] datos = daoApoderado.obtenerDatosApoderado(dniApoderado);

        cajaDNI.setText(datos[0]);
        cajaPrimerNombre.setText(datos[1]);
        cajaSegundoNombre.setText(datos[2]);
        cajaApellidoP.setText(datos[3]);
        cajaApellidoM.setText(datos[4]);
        cajaGenero.setText(datos[6]);

        cajaTelefono.setText(datos[7]);
        cajaCorreoE.setText(datos[8]);
        cajaParentesco.setValue(datos[9]);

        if (datos[5] != null) {
            cajaFechaNac.setValue(java.time.LocalDate.parse(datos[5]));
        }
        cajaDNI.setEditable(false);
        cajaFechaNac.setEditable(false);
    }

    private void LimpiarCampos() {
        cajaDNI.clear();
        cajaPrimerNombre.clear();
        cajaSegundoNombre.clear();
        cajaApellidoM.clear();
        cajaApellidoP.clear();
        cajaGenero.clear();
        cajaCorreoE.clear();
        cajaTelefono.clear();
        cajaFechaNac.setValue(null);
        cajaParentesco.getSelectionModel().clearSelection();
    }

    //Agregar un nuevo apoderado
    public void agregarApoderado() {
        if(!validarCamposDeIngreso()) {
            return;
        }

        Date fechaNacimiento = valorFechaNacimiento();
        if (fechaNacimiento == null) {
            return;
        }

        String dni = cajaDNI.getText();
        String primerNombre = cajaPrimerNombre.getText();
        String segundoNombre = cajaSegundoNombre.getText();
        String apellidoM = cajaApellidoM.getText();
        String apellidoP = cajaApellidoP.getText();
        String genero = cajaGenero.getText();
        Date fecha = fechaNacimiento;
        String parentesco = cajaParentesco.getSelectionModel().getSelectedItem();
        String telefono = cajaTelefono.getText();
        String correoE = cajaCorreoE.getText();

        Apoderado apoderado = new Apoderado(dni, primerNombre, segundoNombre, apellidoP, apellidoM, fecha, genero, correoE, telefono, parentesco);
        DAO_Apoderado daoApoderado = new DAO_Apoderado();
        daoApoderado.Crear(apoderado);

        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Información");
        alerta.setHeaderText("Apoderado registrado correctamente");
        alerta.showAndWait();

        LimpiarCampos();
        listarApoderados();
    }

    //Actualizar datos del apoderado
    public void actualizarApoderado() {
        Object[] filaSeleccionada = tablaApoderados.getSelectionModel().getSelectedItem();

        if (filaSeleccionada == null) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Selección Requerida");
            alerta.setHeaderText("No se ha seleccionado ningún apoderado");
            alerta.setContentText("Por favor, seleccione un apoderado para modificar.");
            alerta.showAndWait();
            return;
        }

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
        String ApellidoPaterno = cajaApellidoP.getText();
        String ApellidoMaterno = cajaApellidoM.getText();
        String Genero = cajaGenero.getText();
        Date FechaNacimiento = Date.valueOf(cajaFechaNac.getValue().toString());
        String Parentesco = cajaParentesco.getValue();
        String Telefono = cajaTelefono.getText();
        String CorreoE = cajaCorreoE.getText();

        DAO_Apoderado daoApoderado = new DAO_Apoderado();
        daoApoderado.actualizarApoderado(DNI, PrimerNombre, SegundoNombre, ApellidoPaterno, ApellidoMaterno, FechaNacimiento, Genero, CorreoE, Telefono, Parentesco);

        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Información");
        alerta.setHeaderText("Datos actualizados correctamente");
        alerta.showAndWait();

        LimpiarCampos();
        listarApoderados();
    }

    //Metodo para configurar la tabla
    private void configurarTabla() {
        columDNI.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty((String) cellData.getValue()[0]));
        columNombre.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty((String) cellData.getValue()[1]));
        columApellidoP.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty((String) cellData.getValue()[2]));
        columnApellidoM.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty((String) cellData.getValue()[3]));
        columTelef.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty((String) cellData.getValue()[5]));
        columCorreoE.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty((String) cellData.getValue()[4]));

        for (TableColumn<?, ?> col : tablaApoderados.getColumns()) {
            col.setReorderable(false);
        }

        tablaApoderados.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void listarApoderados() {
        DAO_Apoderado daoApoderado = new DAO_Apoderado();
        daoApoderado.Listar();

        ObservableList<Object[]> datosApoderado = FXCollections.observableArrayList();
        for (Apoderado a : daoApoderado.getApoderados()) {
            datosApoderado.add(a.convertir());
        }
        tablaApoderados.setItems(datosApoderado);
    }

    //Filtro de busqueda
    private void buscarApoderado() {
        String textoIngresado = busqueda.getText().toLowerCase();

        DAO_Apoderado daoApoderado = new DAO_Apoderado();
        daoApoderado.Listar();

        ObservableList<Object[]> resultadoFiltrados = FXCollections.observableArrayList();

        for (Apoderado apoderado : daoApoderado.getApoderados()) {
            Object[] datos = apoderado.convertir();

            String nombreCompleto = (
                    apoderado.getPrimernombre() + " " +
                            apoderado.getSegundonombre() + " " +
                            apoderado.getApellidopaterno() + " " +
                            apoderado.getApellidomaterno()
            ).toLowerCase();

            if (textoIngresado.isEmpty() || nombreCompleto.contains(textoIngresado)) {
                resultadoFiltrados.add(datos);
            }
        }
        tablaApoderados.setItems(resultadoFiltrados);
    }
}

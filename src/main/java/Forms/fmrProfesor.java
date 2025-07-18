package Forms;
import Clases.ClasesPersonas.Profesor;
import Clases.ConexionBD.Entidades_DAO.DAO_Profesor;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.Date;
import java.time.LocalDate;

public class fmrProfesor {
    @FXML
    private void initialize(){
        configuracionCombox();
        configurarTablaProfesores();
        listarProfesores();
        cajaBusqueda.textProperty().addListener((obs, oldText, newText) -> buscarProfesor(newText));
        tablaProfesores.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                String dniProfesorSeleccionado = (String) tablaProfesores.getSelectionModel().getSelectedItem()[1];
                cargarDatosProfesor(dniProfesorSeleccionado);
            }
        });
    }

    //Columnas de la tabla
    @FXML private TableColumn<Object[], String> codigoProf;
    @FXML private TableColumn<Object[], String> dniProf;
    @FXML private TableColumn<Object[], String> nombreProf;
    @FXML private TableColumn<Object[], String> apellidoPaternoProf;
    @FXML private TableColumn<Object[], String> apellidoMaternoProf;
    @FXML private TableColumn<Object[], String> especialidadProf;
    @FXML private TableColumn<Object[], String> generoP;

    //Campo de búsqueda
    @FXML
    private TextField cajaBusqueda;

    //Tabla de profesores
    @FXML
    private TableView<Object[]> tablaProfesores;

    //Campos de texto
    @FXML private TextField cajaDNI;
    @FXML private TextField cajaPrimerNombre;
    @FXML private TextField cajaSegundoNombre;
    @FXML private TextField cajaApellidoPaterno;
    @FXML private TextField cajaApellidoMaterno;
    @FXML private TextField cajaGenero;
    @FXML private TextField cajaHorasSem;
    @FXML private TextField cajaTelefono;
    @FXML private TextField cajaCorreoE;

    // ComboBoxes
    @FXML private ComboBox<String> comboEpecialidad;
    @FXML private ComboBox<String> comboGradoAcademico;

    // Selector de fecha
    @FXML private DatePicker cajaFechaNacimiento;

    // Botones
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnModificiar;

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

        // Validar horas semanales
        if (cajaHorasSem.getText().isEmpty()) {
            mensaje += "El campo horas semanales no puede estar vacío.\n";
        } else {
            try {
                int horas = Integer.parseInt(cajaHorasSem.getText());

                if (horas < 12 || horas > 24) {
                    mensaje += "Las horas semanales deben estar entre 12 y 24 horas.\n";
                }
            } catch (NumberFormatException e) {
                mensaje += "Las horas semanales deben ser un número entero válido.\n";
            }
        }

        // Validar teléfono
        if (cajaTelefono.getText().isEmpty()) {
            mensaje += "El campo teléfono no puede estar vacío.\n";
        } else if (!cajaTelefono.getText().matches("\\d{9}")) {
            mensaje += "El teléfono debe tener exactamente 9 dígitos.\n";
        }

        // Validar correo electrónico
        if (cajaCorreoE.getText().isEmpty()) {
            mensaje += "El campo correo electrónico no puede estar vacío.\n";
        } else if (!cajaCorreoE.getText().matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
            mensaje += "El correo electrónico no tiene un formato válido.\n";
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
            Date fechaConvertida = java.sql.Date.valueOf(fechaLocal);
            return fechaConvertida;
        }
    }

    //Metodo para limpiar campos
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
        cajaHorasSem.clear();
        cajaCorreoE.clear();
        cajaTelefono.clear();
    }

    //Configurar los campos de la tabla
    private void configurarTablaProfesores() {
        codigoProf.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[0].toString()));
        dniProf.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[1].toString()));
        nombreProf.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[2].toString()));
        apellidoPaternoProf.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[3].toString()));
        apellidoMaternoProf.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[4].toString()));
        especialidadProf.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[5].toString()));
        generoP.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[6].toString()));

        //Evitar que se muevan las columnas
        for (TableColumn<?, ?> col : tablaProfesores.getColumns()) {
            col.setReorderable(false);
        }

        //Evitar el scroll horizontal
        tablaProfesores.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    //Metodo para listar profesores
    private void listarProfesores() {
        DAO_Profesor dao = new DAO_Profesor();
        dao.Listar();

        ObservableList<Object[]> datos = FXCollections.observableArrayList();
        for (Profesor prof : dao.getProfesores()) {
            datos.add(prof.Convertir());
        }

        tablaProfesores.setItems(datos);
    }

    //Mwtodo para cargar los datos del profesor seleccionado en las cajas de texto
    private void cargarDatosProfesor(String dniProfesor) {
        LimpiarCampos();
        DAO_Profesor daoProfesor = new DAO_Profesor();

        String[] datos = daoProfesor.obtenerDatosProfesor(dniProfesor);

        if (datos[0] != null && !datos[0].equals("No encontrado")) {
            cajaDNI.setText(datos[0]);
            cajaPrimerNombre.setText(datos[1]);
            cajaSegundoNombre.setText(datos[2]);
            cajaApellidoPaterno.setText(datos[3]);
            cajaApellidoMaterno.setText(datos[4]);
            cajaGenero.setText(datos[6]);

            if (datos[6] != null) {
                cajaFechaNacimiento.setValue(java.time.LocalDate.parse(datos[5]));
            }

            comboEpecialidad.setValue(datos[7]);
            comboGradoAcademico.setValue(datos[8]);

            cajaHorasSem.setText(datos[9]);
            cajaTelefono.setText(datos[11]);
            cajaCorreoE.setText(datos[10]);

            cajaDNI.setEditable(false);
            cajaFechaNacimiento.setEditable(false);
            cajaTelefono.setEditable(false);
            cajaCorreoE.setEditable(false);
        }
    }

    //Nuevo profesor
    public void nuevoProfesor() {
        if (!validarCampos()) {
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
        String especialidad = comboEpecialidad.getValue();
        String gradoAcademico = comboGradoAcademico.getValue();
        int horasSemanales = Integer.parseInt(cajaHorasSem.getText());
        String telefono  = cajaTelefono.getText();
        String correoE = cajaCorreoE.getText();

        Profesor profesor = new Profesor(DNI, PrimerNombre, SegundoNombre, ApellidoPaterno, ApellidoMaterno, FechaNacimiento, Genero, especialidad, gradoAcademico, horasSemanales, correoE, telefono);
        DAO_Profesor dao = new DAO_Profesor();
        dao.Crear(profesor);

        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Información");
        alerta.setHeaderText("Profesor registrado correctamente");
        alerta.showAndWait();

        LimpiarCampos();
        listarProfesores();
    }

    //MOodificar profesor
    public void actualizarProfesor() {
        if (tablaProfesores.getSelectionModel().getSelectedItem() == null) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Selección Requerida");
            alerta.setHeaderText("No se ha seleccionado ningún profesor");
            alerta.setContentText("Por favor, seleccione un profesor para modificar.");
            alerta.showAndWait();
            return;
        }

        String dniProfesor = cajaDNI.getText();
        String primerNombre = cajaPrimerNombre.getText();
        String segundoNombre = cajaSegundoNombre.getText();
        String apellidoPaterno = cajaApellidoPaterno.getText();
        String apellidoMaterno = cajaApellidoMaterno.getText();
        String genero = cajaGenero.getText();
        String especialidad = comboEpecialidad.getValue();
        String gradoAcademico = comboGradoAcademico.getValue();
        int horasSemanales = Integer.parseInt(cajaHorasSem.getText());
        String telefono = cajaTelefono.getText();
        String correoElectronico = cajaCorreoE.getText();

        DAO_Profesor daoProfesor = new DAO_Profesor();
        daoProfesor.Actualizar(primerNombre, segundoNombre, apellidoPaterno, apellidoMaterno, genero, especialidad,
                gradoAcademico, horasSemanales, correoElectronico, telefono, dniProfesor);

        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Información");
        alerta.setHeaderText("Datos de profesor actualizados correctamente");
        alerta.showAndWait();
        LimpiarCampos();
        listarProfesores();
    }


    //Metodo de busqueda dinamica
    private void buscarProfesor(String textoIngresado) {
        DAO_Profesor daoProfesor = new DAO_Profesor();
        daoProfesor.Listar();
        ObservableList<Object[]> resultadoFiltrados = FXCollections.observableArrayList();

        for (Profesor profesor : daoProfesor.getProfesores()) {
            Object[] datos = profesor.Convertir();

            String nombreCompleto = (profesor.getPrimernombre() + " " + profesor.getSegundonombre() + " " + profesor.getApellidopaterno() + " " + profesor.getApellidomaterno()).toLowerCase();

            if (textoIngresado == null || textoIngresado.isEmpty() || nombreCompleto.contains(textoIngresado.toLowerCase())) {
                resultadoFiltrados.add(datos);
            }
        }
        tablaProfesores.setItems(resultadoFiltrados);
    }
}

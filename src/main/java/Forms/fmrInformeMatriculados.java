package Forms;
import Clases.ClasesPersonas.SesionUsuario;
import Clases.ConexionBD.Entidades_DAO.DAO_Matricula;
import Clases.ConexionBD.Entidades_DAO.DAO_Profesor;
import Clases.ConexionBD.Entidades_DAO.DAO_Trabajador;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class fmrInformeMatriculados {
    String rolUsuario = SesionUsuario.getInstancia().getRolUsuario();
    String dniprofesor = SesionUsuario.getInstancia().getDNIusuario();

    @FXML
    public void initialize() {
        configurarTabla();
        configurarComboBox();

        if (rolUsuario.equals("Profesor")) {
            cargarDatos();
            cajaDNIdocente.setEditable(false);
        } else {
            btnCargarDatos.setOnAction(event -> cargarDatos());
        }
    }

    //Variables de campos
    @FXML private TextField cajaNombres;
    @FXML private TextField cajaCodigoDocente;
    @FXML private TextField cajaApellidos;
    @FXML private TextField cajaGradoAcademico;
    @FXML private TextField cajaEspecialidad;
    @FXML private TextField cajaDNI;
    @FXML private TextField cajaDNIdocente;

    //Imagen de docente
    @FXML private ImageView imagenProfesor;

    //Tabla de alumnos
    @FXML private TableView<Object[]> tablaAlumnosMatriculados;
    @FXML private TableColumn<Object[], String> columnaCodigo;
    @FXML private TableColumn<Object[], String> columnaDNI;
    @FXML private TableColumn<Object[], String> columnaNombres;
    @FXML private TableColumn<Object[], String> columnaApellidos;
    @FXML private TableColumn<Object[], String> columnaAsignatura;
    @FXML private TableColumn<Object[], String> columnaGradoYSeccion;

    //Combo Box de filtros
    @FXML private ComboBox comboGrado;
    @FXML private ComboBox comboSeccion;

    //Boton para realizar una nueva consulta
    @FXML private Button btnCargarDatos;

    //Carga de los comboBox
    private void configurarComboBox(){
        ObservableList<String> Grados = FXCollections.observableArrayList("Todos", "Primer", "Segundo", "Tercer", "Cuarto", "Quinto");
        ObservableList<String> Secciones = FXCollections.observableArrayList("Todos", "A", "B", "C");

        comboGrado.setItems(Grados);
        comboSeccion.setItems(Secciones);
        comboGrado.getSelectionModel().select(0);
        comboSeccion.getSelectionModel().select(0);
    }

    //Metodo para validad DNI
    private boolean validarDNIdocente(String dni){
        DAO_Profesor dao = new DAO_Profesor();
        boolean esDniValido = dao.validarDniProfesor(dni);

        if (!esDniValido) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("El DNI ingresado no pertenece a un profesor");
            alert.setContentText("Por favor ingrese un DNI válido, el ingresado no pertenece a un profesor");
            alert.showAndWait();
        }
        return esDniValido;
    }

    //Metodo para listar los datos del docente
    private void listarDatos(String DNI) {
        cajaNombres.clear();
        cajaCodigoDocente.clear();
        cajaApellidos.clear();
        cajaGradoAcademico.clear();
        cajaEspecialidad.clear();
        cajaDNI.clear();
        cajaDNIdocente.clear();

        DAO_Profesor daoProfesor = new DAO_Profesor();

        String[] datos = daoProfesor.obtenerDatosProfesor(DNI);

        if (datos[0] != null && validarDNIdocente(datos[0])) {
            cajaDNI.setText(datos[0]);
            cajaNombres.setText(datos[1] + " " + datos[2]);
            cajaApellidos.setText(datos[3] + " " + datos[4]);
            cajaEspecialidad.setText(datos[7]);
            cajaGradoAcademico.setText(datos[8]);
            cajaCodigoDocente.setText(datos[12]);

            String genero= datos[6];
            cambiarImagen(genero);
        }
        cajaNombres.setEditable(false);
        cajaApellidos.setEditable(false);
        cajaEspecialidad.setEditable(false);
        cajaGradoAcademico.setEditable(false);
        cajaCodigoDocente.setEditable(false);
    }

    //Carga de datos
    public void cargarDatos() {
        if (rolUsuario.equals("Profesor")) {
            listarDatos(dniprofesor);
            cargarDatosAlumnos(dniprofesor);
            comboGrado.setOnAction(event -> cargarDatosFiltrados(dniprofesor));
            comboSeccion.setOnAction(event ->  cargarDatosFiltrados(dniprofesor));
        } else {
            String dniDocente = cajaDNIdocente.getText();

            if (dniDocente.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Advertencia");
                alert.setHeaderText("DNI del docente vacío");
                alert.setContentText("Por favor, ingrese el DNI del docente para continuar.");
                alert.showAndWait();
            }
        }
    }

    //Metodo para configurar tabla
    private void configurarTabla() {

        tablaAlumnosMatriculados.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        columnaCodigo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[0].toString()));
        columnaDNI.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[1].toString()));
        columnaNombres.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[2].toString()));
        columnaApellidos.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[3].toString()));
        columnaAsignatura.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[4].toString()));
        columnaGradoYSeccion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[5].toString()));
    }

    //Metodo para cambiar la imagen del docente
    private void cambiarImagen(String genero){
        String imagenRuta = "";

        if ("masculino".equalsIgnoreCase(genero)) {
            imagenRuta = "https://i.imgur.com/dq30HLu.png";
        } else if ("femenino".equalsIgnoreCase(genero)) {
            imagenRuta = "https://i.imgur.com/9lALKND.png";
        } else {
            imagenRuta = "https://i.imgur.com/dq30HLu.png";
        }

        Image imagen = new Image(imagenRuta);
        imagenProfesor.setImage(imagen);
    }

    //Metodo alumnos de una tabla
    private void cargarDatosAlumnos(String DNIdocente) {
        DAO_Matricula daoMatricula = new DAO_Matricula();
        String[][] listaAlumnos = daoMatricula.obtenerDatosAlumnos(DNIdocente);

        ObservableList<Object[]> alumnos = FXCollections.observableArrayList();

        for (String[] datosAlumno : listaAlumnos) {
            String alumnoNombre = datosAlumno[1] + " " + datosAlumno[2];
            String alumnoApellido = datosAlumno[3] + " " + datosAlumno[4];
            String gradoSeccion = datosAlumno[5] + " " + datosAlumno[6];
            String nombreCurso = datosAlumno[7];
            String codigoAlumno = datosAlumno[0];
            String dniAlumno = datosAlumno[8];

            alumnos.add(new Object[] {codigoAlumno, dniAlumno, alumnoNombre, alumnoApellido, nombreCurso, gradoSeccion});
        }
        tablaAlumnosMatriculados.setItems(alumnos);
    }

    //Limpiar campos
    public void LimpiarCampos(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText("Campos restablecidos");
        alert.setContentText("Los campos se han restablecido, vuelva ingresar otro DNI para cargar los datos");
        alert.showAndWait();

        cajaNombres.clear();
        cajaCodigoDocente.clear();
        cajaApellidos.clear();
        cajaGradoAcademico.clear();
        cajaEspecialidad.clear();
        cajaDNI.clear();
        cajaDNIdocente.clear();
        tablaAlumnosMatriculados.getItems().clear();
        imagenProfesor.setImage(null);
    }

    //Cargar Datos Filtrados
    private void cargarDatosFiltrados(String DNIdocente) {
        String gradoSeleccionado = comboGrado.getValue() != null ? comboGrado.getValue().toString() : "Todos";
        String seccionSeleccionada = comboSeccion.getValue() != null ? comboSeccion.getValue().toString() : "Todos";

        String gradoFiltro = gradoSeleccionado.equalsIgnoreCase("Todos") ? "" : gradoSeleccionado;
        String seccionFiltro = seccionSeleccionada.equalsIgnoreCase("Todos") ? "" : seccionSeleccionada;

        DAO_Matricula daoMatricula = new DAO_Matricula();
        String[][] listaAlumnos = daoMatricula.obtenerDatosAlumnos(DNIdocente);

        ObservableList<Object[]> alumnos = FXCollections.observableArrayList();

        for (String[] datosAlumno : listaAlumnos) {
            String alumnoNombre = datosAlumno[1] + " " + datosAlumno[2];
            String alumnoApellido = datosAlumno[3] + " " + datosAlumno[4];
            String grado = datosAlumno[5];
            String seccion = datosAlumno[6];
            String gradoSeccion = grado + " " + seccion;
            String nombreCurso = datosAlumno[7];
            String codigoAlumno = datosAlumno[0];
            String dniAlumno = datosAlumno[8];

            boolean cumpleFiltro = true;

            if (!gradoFiltro.isEmpty() && !grado.equalsIgnoreCase(gradoFiltro)) {
                cumpleFiltro = false;
            }

            if (!seccionFiltro.isEmpty() && !seccion.equalsIgnoreCase(seccionFiltro)) {
                cumpleFiltro = false;
            }

            if (cumpleFiltro) {
                alumnos.add(new Object[]{
                        codigoAlumno,
                        dniAlumno,
                        alumnoNombre,
                        alumnoApellido,
                        nombreCurso,
                        gradoSeccion
                });
            }
        }

        tablaAlumnosMatriculados.setItems(alumnos);
    }
}

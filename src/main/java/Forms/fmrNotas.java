package Forms;
import Clases.ClasesGestionEscolar.LibretaNotasPDF;
import Clases.ConexionBD.Entidades_DAO.DAO_Nota;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;

public class fmrNotas {
    public void initialize() {
        configurarTablaAlumno();
        configurarComboGradoYSeccion();
        listarAlumnos();

        comboBoxGrado.setOnAction(event -> aplicarFiltrosCombinados());
        comboBoxSeccion.setOnAction(event -> aplicarFiltrosCombinados());

        cajaBusqueda.textProperty().addListener((observable, oldValue, newValue) -> aplicarFiltrosCombinados());

        configurarTablaCursosYNotas();
        tablaAlumno.setOnMouseClicked(event -> {
            int idMatricula = numeroMatricula();
            if (idMatricula != -1) {
                listarCursosEnComboBox(idMatricula);
                listarCursos(idMatricula);
            }
        });
        comboBoxCursos.setOnAction(event -> {
            String cursoSeleccionado = (String) comboBoxCursos.getValue();
            int idMatricula = numeroMatricula();
            listarNotasDeUnSoloCurso(idMatricula, cursoSeleccionado);
            listarNotasEnLosCampos(idMatricula, cursoSeleccionado);
        });
        btnRegistrar.setOnAction(event -> modificarNotas());
        btnImprimirBoleta.setOnAction(event -> generarLibretaDeNotas());
    }

    //ComboBox
    @FXML private ComboBox comboBoxCursos;
    @FXML private ComboBox comboBoxGrado;
    @FXML private ComboBox comboBoxSeccion;

    //Cajas de texto
    @FXML private TextField cajaBusqueda;
    @FXML private TextField nota1;
    @FXML private TextField nota2;
    @FXML private TextField nota3;
    @FXML private TextField promedio;

    //Tabla y Columnas
    //Primera tabla
    @FXML private TableView<Object[]> tablaAlumno;
    @FXML private TableColumn<Object[], String> columCodigo;
    @FXML private TableColumn<Object[], String> DNI;
    @FXML private TableColumn<Object[], String> Nombre;
    @FXML private TableColumn<Object[], String> columapellidoP;
    @FXML private TableColumn<Object[], String> columapellidoM;
    @FXML private TableColumn<Object[], String> columNumMatricula;

    //Tabla y columnas
    //Segunda tabla
    @FXML private TableView<Object[]> tablaCursos;
    @FXML private TableColumn<Object[], String> curso;
    @FXML private TableColumn<Object[], String> Columnota;
    @FXML private TableColumn<Object[], String> columTipoNota;

    //Botones
    @FXML private Button btnRegistrar;
    @FXML private Button btnImprimirBoleta;

    //Metodo para configurar la tabla de alumno
    private void configurarTablaAlumno() {
        columCodigo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[0].toString()));
        DNI.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[1].toString()));
        Nombre.setCellValueFactory(data -> {
            String primerNombre = data.getValue()[2].toString();
            String segundoNombre = data.getValue()[3].toString();
            return new SimpleStringProperty(primerNombre + " " + segundoNombre);
        });
        columapellidoP.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[4].toString()));
        columapellidoM.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[5].toString()));
        columNumMatricula.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[6].toString()));

        for (TableColumn<?, ?> col : tablaAlumno.getColumns()) {
            col.setReorderable(false);
        }

        tablaAlumno.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    //Metodo para listar alumnos
    private void listarAlumnos() {
        DAO_Nota dao = new DAO_Nota();
        ArrayList<Object[]> alumnos = dao.listarAlumnos();
        ObservableList<Object[]> observableList = FXCollections.observableArrayList(alumnos);

        tablaAlumno.setItems(observableList);
    }

    //Metodo par configurar la tabla de cursos
    private void configurarTablaCursosYNotas() {
        curso.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[0].toString()));
        Columnota.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[1].toString()));
        columTipoNota.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[2].toString()));

        for (TableColumn<?, ?> col : tablaCursos.getColumns()) {
            col.setReorderable(false);
        }
        tablaCursos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    //Metodo para listar los cursos y notas de un alumno
    private void listarCursos(int idMatricula) {
        if (idMatricula > 0) {
            DAO_Nota dao = new DAO_Nota();
            ArrayList<Object[]> cursosNotas = dao.listarCursosDeAlumno(idMatricula);

            ObservableList<Object[]> observableList = FXCollections.observableArrayList(cursosNotas);

            tablaCursos.setItems(observableList);
        }
    }

    //Metodo para obtener el idMatricula de la tabla de alumnos
    private int numeroMatricula() {
        Object[] filaSeleccionada = tablaAlumno.getSelectionModel().getSelectedItem();

        if (filaSeleccionada != null) {
            return Integer.parseInt(filaSeleccionada[6].toString());
        } else {
            return -1;
        }
    }

    //Metodo para configurar el comboBox
    private void listarCursosEnComboBox(int idMatricula) {
        DAO_Nota dao = new DAO_Nota();
        ArrayList<String> cursos = dao.listarCursos(idMatricula);

        ObservableList<String> observableCursos = FXCollections.observableArrayList("Todos");
        observableCursos.addAll(cursos);

        comboBoxCursos.setItems(observableCursos);
    }

    private void listarNotasDeUnSoloCurso(int idMatricula, String nombreCurso) {
        DAO_Nota dao = new DAO_Nota();

        if (nombreCurso != null && !nombreCurso.equals("Todos")) {
            ArrayList<Object[]> cursosNotas = dao.listarNotasDeUnSoloCurso(idMatricula, nombreCurso);
            ObservableList<Object[]> observableList = FXCollections.observableArrayList(cursosNotas);
            tablaCursos.setItems(observableList);
        } else {
            ArrayList<Object[]> cursosNotas = dao.listarCursosDeAlumno(idMatricula);
            ObservableList<Object[]> observableList = FXCollections.observableArrayList(cursosNotas);
            tablaCursos.setItems(observableList);
        }
    }

    //Metodo para configurar los comboBox de grado y seccion
    private void configurarComboGradoYSeccion() {
        ObservableList<String> grados = FXCollections.observableArrayList("Todos","Primer", "Segundo", "Tercer", "Cuarto", "Quinto");
        ObservableList<String> secciones = FXCollections.observableArrayList("Todos","A", "B", "C");
        comboBoxGrado.setItems(grados);
        comboBoxSeccion.setItems(secciones);

        comboBoxGrado.getSelectionModel().select(0);
        comboBoxSeccion.getSelectionModel().select(0);
    }

    //Metodo para aplicar filtros de busqueda y los comboBox
    private void aplicarFiltrosCombinados() {
        String texto = cajaBusqueda.getText();
        String grado = (String) comboBoxGrado.getValue();
        String seccion = (String) comboBoxSeccion.getValue();

        DAO_Nota dao = new DAO_Nota();

        ObservableList<Object[]> resultados = FXCollections.observableArrayList();
        ArrayList<Object[]> lista;

        if (grado != "Todos" && seccion != "Todos") {
            lista = dao.alumnosPorGradoYSeccion(grado, seccion);
        } else if (grado != "Todos") {
            lista = dao.alumnosPorGrado(grado);
        } else {
            dao.listarAlumnos();
            lista = dao.listarAlumnos();
        }

        for (Object[] alumnoData : lista) {
            String nombreCompleto = (alumnoData[2] + " " + alumnoData[3] + " " + alumnoData[4] + " " + alumnoData[5]).toLowerCase();

            if (texto == null || texto.isEmpty() || nombreCompleto.contains(texto.toLowerCase())) {
                resultados.add(alumnoData);
            }
        }
        tablaAlumno.setItems(resultados);
    }

    //Metodo para cargar las notas en las cajas de texto
    private void listarNotasEnLosCampos(int idMatricula, String nombreCurso) {
        DAO_Nota dao = new DAO_Nota();
        if (nombreCurso != null && !nombreCurso.equals("Todos")) {
            ArrayList<Integer> notasYPromedio = dao.obtenerNotasDeCurso(idMatricula, nombreCurso);

            nota1.clear();
            nota2.clear();
            nota3.clear();
            promedio.clear();

            if (notasYPromedio.size() > 0) {
                nota1.setText(String.valueOf(notasYPromedio.get(0)));
            }
            if (notasYPromedio.size() > 1) {
                nota2.setText(String.valueOf(notasYPromedio.get(1)));
            }
            if (notasYPromedio.size() > 2) {
                nota3.setText(String.valueOf(notasYPromedio.get(2)));
            }

            if (notasYPromedio.size() > 3) {
                promedio.setText(String.valueOf(notasYPromedio.get(3)));
                promedio.setEditable(false);
            }
        }
    }

    //Metodo para registrar notas
    public void modificarNotas() {
        if (nota1.getText().isEmpty() || nota2.getText().isEmpty() || nota3.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campos Vacíos");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, complete todos los campos de notas antes de continuar.");
            alert.showAndWait();
            return;
        }


        int idMatricula = numeroMatricula();

        String nombreCurso = (String) comboBoxCursos.getValue();

        try {
            int nuevaNota1 = Integer.parseInt(nota1.getText());
            int nuevaNota2 = Integer.parseInt(nota2.getText());
            int nuevaNota3 = Integer.parseInt(nota3.getText());

            DAO_Nota dao = new DAO_Nota();
            dao.modificarNotasYPromedio(idMatricula, nombreCurso, nuevaNota1, nuevaNota2, nuevaNota3);

            listarNotasDeUnSoloCurso(idMatricula, nombreCurso);
            listarNotasEnLosCampos(idMatricula, nombreCurso);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    //Metodo para generar la libreta de notas de un alumno
    public void generarLibretaDeNotas() {
        Object[] filaSeleccionada = tablaAlumno.getSelectionModel().getSelectedItem();

        if (filaSeleccionada == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Selección de Fila");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, seleccione una fila de la tabla.");
            alert.showAndWait();
            return;
        }
        try {
            int idMatricula = Integer.parseInt(filaSeleccionada[6].toString());
            String codigoAlumno = (String) filaSeleccionada[0];

            LibretaNotasPDF libretaNotasPDF = new LibretaNotasPDF(codigoAlumno, idMatricula);
            libretaNotasPDF.generarLibretaPDF(codigoAlumno, idMatricula);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmación de libreta de notas");
            alert.setHeaderText(null);
            alert.setContentText("La libreta de notas se ha generado de manera exitosa");
            alert.showAndWait();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}

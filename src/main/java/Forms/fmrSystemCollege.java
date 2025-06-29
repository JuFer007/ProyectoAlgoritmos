package Forms;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.input.MouseEvent;
import java.io.IOException;

public class fmrSystemCollege extends Application {
    private double x = 0;
    private double y = 0;

    @FXML
    private void initialize() throws IOException {
        formularioBienvenida();

    };

    @FXML
    private AnchorPane AnchorPanePrincipal;
    @FXML
    private Button btnCerrar;
    @FXML
    private Button btnCerrarSesion;

    //Metodo para cerrar la ventana
    @FXML
    private void cerrarVentana(ActionEvent event) {
        Stage stage = (Stage) btnCerrar.getScene().getWindow();
        stage.close();
    }

    //Metodo cerrar sesion y volver al form de inicio
    @FXML
    private void cerrarSesion(ActionEvent event) throws IOException {
        Stage stageAactual = (Stage) btnCerrarSesion.getScene().getWindow();
        stageAactual.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Formularios/Principales/IncioSesion.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stageAactual.setScene(scene);
        stageAactual.show();
    }

    //Metodos para abrir los diferentes formularios
    @FXML
    private void formularioAlumno() throws IOException {
        cargarFormulario("/Formularios/GestionPersonas/Alumno.fxml");
    }

    @FXML
    private void formularioProfesor() throws IOException {
        cargarFormulario("/Formularios/GestionPersonas/Profesor.fxml");
    }

    @FXML
    private void formularioApoderado() throws IOException {
        cargarFormulario("/Formularios/GestionPersonas/Apoderado.fxml");
    }

    @FXML
    private void formularioTrabajador() throws IOException {
        cargarFormulario("/Formularios/GestionPersonas/Trabajador.fxml");
    }

    @FXML
    private void formularioRenovarMatricula() throws IOException {
        cargarFormulario("/Formularios/Matricula/RenovarMatricula.fxml");
    }

    @FXML
    private void formularioNuevaMatricula() throws IOException {
        cargarFormulario("/Formularios/Matricula/NuevaMatricula.fxml");
    }

    @FXML
    private void formularioNotas() throws IOException {
        cargarFormulario("/Formularios/Notas/Notas.fxml");
    }

    @FXML
    private void formularioPagos() throws IOException {
        cargarFormulario("/Formularios/Pagos/Pagos.fxml");
    }

    @FXML
    private void formularioReporteGeneral() throws IOException {
        cargarFormulario("/Formularios/Reportes/ReporteGeneral.fxml");
    }

    @FXML
    private void formularioReportePensiones() throws IOException {
        cargarFormulario("/Formularios/Reportes/ReportePensiones.fxml");
    }

    @FXML
    private void formularioBienvenida() throws IOException {
        cargarFormulario("/Formularios/Principales/Bienvenida.fxml");
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Formularios/Principales/SystemCollege.fxml"));

        Scene scene = new Scene(root);

        root.setOnMousePressed((MouseEvent event) -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });

        root.setOnMouseDragged((MouseEvent event) -> {
            primaryStage.setX(event.getScreenX() - x);
            primaryStage.setY(event.getScreenY() - y);

            primaryStage.setOpacity(0.8);
        });

        root.setOnMouseReleased((MouseEvent event) -> {
            primaryStage.setOpacity(1);
        });

        primaryStage.initStyle(StageStyle.UNDECORATED);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //Metodo para cargar los formulario en el anchor pane
    private void cargarFormulario(String rutaFormXML) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFormXML));
        Parent root = loader.load();
        AnchorPanePrincipal.getChildren().setAll(root);
        AnchorPanePrincipal.setTopAnchor(root, 0.0);
        AnchorPanePrincipal.setBottomAnchor(root, 0.0);
        AnchorPanePrincipal.setLeftAnchor(root, 0.0);
        AnchorPanePrincipal.setRightAnchor(root, 0.0);
    }
}
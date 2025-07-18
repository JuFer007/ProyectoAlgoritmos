package Forms;
import Clases.ClasesPersonas.SesionUsuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;

public class fmrSystemCollege {
    //Menus principales
    @FXML private Menu MenuGestionPersonas;
    @FXML private Menu menuMatricula;
    @FXML private Menu menuNotas;
    @FXML private Menu menuPagos;
    @FXML private Menu menuReportes;

    //Submenus
    @FXML private MenuItem menuInformacionMatriculados;
    @FXML private MenuItem menuReporteGeneral;
    @FXML private MenuItem menuRenovarMatricularAlumno;

    @FXML
    private void initialize() throws IOException {
        formularioBienvenida();
    };

    @FXML
    public AnchorPane AnchorPanePrincipal;
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
        SesionUsuario.getInstancia().cerrarSesion();
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
    private void formularioInformacionMatriculados() throws IOException {
        cargarFormulario("/Formularios/Matricula/InformeMatriculados.fxml");
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
    private void formularioMatricula() throws IOException {
        cargarFormulario("/Formularios/Matricula/Matricula.fxml");
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
    private void formularioCuotas() throws IOException {
        cargarFormulario("/Formularios/Pagos/Cuotas.fxml");
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

    //Metodo para mostrar menus segun el rol de usuario
    public void mostrarMenuPorUsuario(String rolUsuario) {
        switch (rolUsuario.toLowerCase()) {
            case "administrador":
            break;
            case "profesor":
                MenuGestionPersonas.setDisable(true);
                menuRenovarMatricularAlumno.setDisable(true);
                menuPagos.setDisable(true);
            break;
            case "secretario":
                menuInformacionMatriculados.setDisable(true);
            break;
            default:
                MenuGestionPersonas.setDisable(true);
                menuMatricula.setDisable(true);
                menuNotas.setDisable(true);
                menuPagos.setDisable(true);
                menuReportes.setDisable(true);
            break;
        }
    }
}
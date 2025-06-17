package Forms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class fmrIncioSesion {
    @FXML
    private Button btnCerrarVentana;

    @FXML
    private Button btnRegistrarse;

    @FXML
    private TextField txtNombreUsuario;

    @FXML
    private PasswordField txtContraseña;

    @FXML
    private Button BtnIniciarSesion;

    //Metodo para abrir el formulario de registro
    @FXML
    private void registrarse(ActionEvent event) throws IOException {
        Stage stageAactual = (Stage) btnRegistrarse.getScene().getWindow();
        stageAactual.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Formularios/Principales/RegistroUsuario.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stageAactual.setScene(scene);
        stageAactual.show();
    }

    //Metodo para cerrar la ventana
    @FXML
    private void cerrarVentana(ActionEvent event) {
        Stage stage = (Stage) btnCerrarVentana.getScene().getWindow();
        stage.close();
    }

    //Metodo para el boton de iniciar sesion
    @FXML
    private void iniciarSesion(ActionEvent event) throws IOException {
        Stage stageAactual = (Stage) btnRegistrarse.getScene().getWindow();
        stageAactual.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Formularios/Principales/SystemCollege.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stageAactual.setScene(scene);
        stageAactual.show();
    }
}

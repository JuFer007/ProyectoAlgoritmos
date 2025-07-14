package Forms;
import Clases.ConexionBD.Entidades_CRUD.DAO_Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class fmrIncioSesion {
    @FXML
    private Button btnCerrarVentana;

    @FXML
    private TextField txtNombreUsuario;

    @FXML
    private PasswordField txtContraseña;

    @FXML
    private Button BtnIniciarSesion;

    //Metodo para cerrar la ventana
    @FXML
    private void cerrarVentana(ActionEvent event) {
        Stage stage = (Stage) btnCerrarVentana.getScene().getWindow();
        stage.close();
    }

    //Metodo para el boton de iniciar sesion
    @FXML
    private void iniciarSesion(ActionEvent event) throws IOException {
        String usuario = txtNombreUsuario.getText();
        String contraseña = txtContraseña.getText();

        if (usuario.isEmpty() || contraseña.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText("Campos incompletos");
            alert.setContentText("Por favor, complete todos los campos.");
            alert.showAndWait();
            return;
        }

        DAO_Usuario usuarioDAO = new DAO_Usuario();

        boolean esValido = usuarioDAO.verificarUsuarioYContraseña(usuario, contraseña);

        if (esValido) {
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setTitle("Éxito");
           alert.setHeaderText("¡Bienvenido!");
           alert.setContentText("Usuario y contraseña correctos.");
           alert.showAndWait();

           Stage stageAactual = (Stage) BtnIniciarSesion.getScene().getWindow();
           stageAactual.close();

           FXMLLoader loader = new FXMLLoader(getClass().getResource("/Formularios/Principales/SystemCollege.fxml"));
           Parent root = loader.load();
           Scene scene = new Scene(root);
           stageAactual.setScene(scene);
           stageAactual.show();
        } else {
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Error");
           alert.setHeaderText("¡Credenciales incorrectas!");
           alert.setContentText("El nombre de usuario o la contraseña no son correctos.");
           alert.showAndWait();
        }
    }
}

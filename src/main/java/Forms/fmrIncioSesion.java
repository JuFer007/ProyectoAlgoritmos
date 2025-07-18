package Forms;
import Clases.ClasesPersonas.SesionUsuario;
import Clases.ClasesPersonas.Usuarios;
import Clases.ConexionBD.Entidades_DAO.DAO_Profesor;
import Clases.ConexionBD.Entidades_DAO.DAO_Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import java.io.IOException;

public class fmrIncioSesion {
    @FXML
    private void initialize() {
        txtNombreUsuario.setOnKeyPressed(event -> {
            try {
                handleEnter(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        txtContraseña.setOnKeyPressed(event -> {
            try {
                handleEnter(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

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
           usuarioDAO.cargarDatosSesion(usuario);
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

           String rolUsuario = usuarioDAO.obtenerRolUsuario(usuario);
           String DNIusuario = new DAO_Profesor().obtenerDNIprofesor(contraseña, usuario);

           SesionUsuario sesion = SesionUsuario.getInstancia();
           sesion.setRolUsuario(rolUsuario);
           sesion.setDNIusuario(DNIusuario);

           if (rolUsuario != null) {
               fmrSystemCollege fmrSystemCollege = loader.getController();
               fmrSystemCollege.mostrarMenuPorUsuario(rolUsuario);
           } else {
               Alert alerta = new Alert(Alert.AlertType.ERROR);
               alerta.setTitle("Error");
               alerta.setHeaderText("¡Rol se usuario no encontrado!");
               alerta.setContentText("Vuelva a iniciar sesión");
               alerta.showAndWait();
           }
        } else {
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Error");
           alert.setHeaderText("¡Credenciales incorrectas!");
           alert.setContentText("El nombre de usuario o la contraseña no son correctos.");
           alert.showAndWait();
        }
    }

    //Metodo para activar el inicio de sesion por enter
    @FXML
    private void handleEnter(KeyEvent event) throws IOException {
        if (event.getCode().toString().equals("ENTER")) {
            iniciarSesion(new ActionEvent());
        }
    }
}

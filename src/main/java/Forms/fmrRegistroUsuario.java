package Forms;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class fmrRegistroUsuario {
    @FXML
    private Button btnCerrar;

    //Metodo para cerrar la ventana
    @FXML
    private void cerrarVentana(ActionEvent event) {
        Stage stage = (Stage) btnCerrar.getScene().getWindow();
        stage.close();
    }

}

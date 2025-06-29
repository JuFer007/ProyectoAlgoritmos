package Forms;
import Clases.ConexionBD.Entidades_CRUD.DAO_Reportes;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.util.ArrayList;

public class fmrReportePensiones {
    //Tabla de pagos
    @FXML private TableView<Object[]> tablaPagos;

    //Columnas de la tabla
    @FXML private TableColumn<Object[], String> columCodigo;
    @FXML private TableColumn<Object[], String> columAlumno;
    @FXML private TableColumn<Object[], String> columFecha;
    @FXML private TableColumn<Object[], Double> columMonto;
    @FXML private TableColumn<Object[], String> columMetodo;
    @FXML private TableColumn<Object[], String> columEstado;

    //Botones
    @FXML private Button btnAvanzar;
    @FXML private Button btnRetroceder;
    @FXML private Button btnEliminar;

    //Variables
    private int mesActual = 1;
    DAO_Reportes dao = new DAO_Reportes();

    @FXML
    private void initialize() {
        configurarColumnasTabla();
        cargarPagos();
    }

    //Metodo para cargar los pagos segun el mes y año
    public void cargarPagos() {
        dao.listarPagos(mesActual);
        ArrayList<Object[]> lista = dao.getListapagos();

        tablaPagos.getItems().clear();
        tablaPagos.getItems().addAll(lista);
    }

    //Metodo para configurar la tabla y columnas
    private void configurarColumnasTabla() {
        columCodigo.setCellValueFactory(data -> new SimpleStringProperty((String) data.getValue()[1]));

        columAlumno.setCellValueFactory(data -> new SimpleStringProperty(
                (String) data.getValue()[2] + " " +
                        (String) data.getValue()[3] + " " +
                        (String) data.getValue()[4] + " " +
                        (String) data.getValue()[5]
        ));

        columFecha.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[6].toString()));
        columEstado.setCellValueFactory(data -> new SimpleStringProperty((String) data.getValue()[7]));
        columMonto.setCellValueFactory(data -> new SimpleObjectProperty<>((Double) data.getValue()[8]));
        columMetodo.setCellValueFactory(data -> new SimpleStringProperty((String) data.getValue()[9]));
    }

    //Metodo para avanzar
    public void avanzarMes() {
        if (mesActual < 12) {
            mesActual++;
            cargarPagos();
        }
    }

    //Metodo para retrocedes
    public void retrocederMes() {
        if (mesActual > 1) {
            mesActual--;
            cargarPagos();
        }
    }
}

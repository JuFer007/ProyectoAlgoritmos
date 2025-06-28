package Forms;
import Clases.ConexionBD.Entidades_CRUD.DAO_Pago;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import java.util.ArrayList;

public class fmrPagos {
    @FXML
    private void initialize() {
        configurarComboBox();
        configurarTablaPagos();
        listarPagos();

        comboGradoB.setOnAction(e -> filtrarPagos());
        comboSeccionB.setOnAction(e -> filtrarPagos());
        comboEstado.setOnAction(e -> filtrarPagos());
    }

    //Cajas de busqueda y comboBox
    @FXML private javafx.scene.control.TextField cajaBusqueda;
    @FXML private javafx.scene.control.ComboBox<String> comboGradoB;
    @FXML private javafx.scene.control.ComboBox<String> comboSeccionB;
    @FXML private javafx.scene.control.ComboBox<String> comboEstado;

    //Botones
    @FXML private javafx.scene.control.Button btnRegistrarPago;
    @FXML private javafx.scene.control.Button btnImprimirComprobante;
    @FXML private javafx.scene.control.Button btnAnularPago;

    //Tablas y columnas
    @FXML private Object tablaPagos;
    @FXML private Object columCodigo;
    @FXML private Object columNombreP;
    @FXML private Object columApellidoP;
    @FXML private Object columFechaP;
    @FXML private Object columEstado;
    @FXML private Object columMonto;
    @FXML private Object columMetodo;

    //Metodo configurar la tabla de los pagos
    private void configurarTablaPagos() {
        javafx.scene.control.TableView<Object[]> tabla = (javafx.scene.control.TableView<Object[]>) tablaPagos;

        javafx.scene.control.TableColumn<Object[], String> codigo = (javafx.scene.control.TableColumn<Object[], String>) columCodigo;
        javafx.scene.control.TableColumn<Object[], String> nombre = (javafx.scene.control.TableColumn<Object[], String>) columNombreP;
        javafx.scene.control.TableColumn<Object[], String> apellido = (javafx.scene.control.TableColumn<Object[], String>) columApellidoP;
        javafx.scene.control.TableColumn<Object[], String> fecha = (javafx.scene.control.TableColumn<Object[], String>) columFechaP;
        javafx.scene.control.TableColumn<Object[], String> estado = (javafx.scene.control.TableColumn<Object[], String>) columEstado;
        javafx.scene.control.TableColumn<Object[], Double> monto = (javafx.scene.control.TableColumn<Object[], Double>) columMonto;
        javafx.scene.control.TableColumn<Object[], String> metodo = (javafx.scene.control.TableColumn<Object[], String>) columMetodo;

        codigo.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty((String) data.getValue()[1]));
        nombre.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty((String) data.getValue()[2]));
        apellido.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
                (String) data.getValue()[4] + " " + (String) data.getValue()[5]));
        fecha.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue()[6].toString()));
        estado.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty((String) data.getValue()[7]));
        monto.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>((Double) data.getValue()[8]));
        metodo.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty((String) data.getValue()[9]));
    }

    //Metodo para listar pagos
    private void listarPagos() {
        DAO_Pago dao = new DAO_Pago();
        dao.Listar();
        ArrayList<Object[]> lista = dao.getListaPagos();

        javafx.scene.control.TableView<Object[]> tabla = (javafx.scene.control.TableView<Object[]>) tablaPagos;
        tabla.getItems().clear();
        tabla.getItems().addAll(lista);
    }

    //Metodo para registrar un pago
    public void registrarPago() {
        javafx.scene.control.TableView<Object[]> tabla = (javafx.scene.control.TableView<Object[]>) tablaPagos;
        Object[] filaSeleccionada = tabla.getSelectionModel().getSelectedItem();

        if (filaSeleccionada == null) {
            Alert advertencia = new Alert(Alert.AlertType.WARNING);
            advertencia.setTitle("Advertencia");
            advertencia.setHeaderText(null);
            advertencia.setContentText("Debe seleccionar un pago en la tabla.");
            advertencia.showAndWait();
            return;
        }

        String estado = ((String) filaSeleccionada[7]).trim();

        if (!estado.equalsIgnoreCase("Pendiente") && !estado.equalsIgnoreCase("Vencido")) {
            Alert info = new Alert(Alert.AlertType.INFORMATION);
            info.setTitle("Estado no válido");
            info.setHeaderText(null);
            info.setContentText("Solo se pueden registrar pagos que estén en estado 'Pendiente' o 'Vencido'.");
            info.showAndWait();
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmación");
        confirm.setHeaderText("¿Desea registrar este pago?");
        confirm.setContentText("Se marcará como 'Pagado' con la fecha actual del sistema.");

        if (confirm.showAndWait().get() == javafx.scene.control.ButtonType.OK) {
            int idPago = (int) filaSeleccionada[0];

            DAO_Pago dao = new DAO_Pago();
            dao.registrarPago(idPago);
            listarPagos();
        }
    }

    //Metodo para eliminar un pago
    public void eliminarPago() {
        javafx.scene.control.TableView<Object[]> tabla = (javafx.scene.control.TableView<Object[]>) tablaPagos;
        Object[] filaSeleccionada = tabla.getSelectionModel().getSelectedItem();

        if (filaSeleccionada == null) {
            Alert advertencia = new Alert(Alert.AlertType.WARNING);
            advertencia.setTitle("Advertencia");
            advertencia.setHeaderText(null);
            advertencia.setContentText("Debe seleccionar un pago en la tabla.");
            advertencia.showAndWait();
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmación");
        confirm.setHeaderText("¿Está seguro de eliminar este pago?");
        confirm.setContentText("Esta acción no se puede deshacer.");

        if (confirm.showAndWait().get() == javafx.scene.control.ButtonType.OK) {
            int idPago = (int) filaSeleccionada[0];
            DAO_Pago dao = new DAO_Pago();
            dao.eliminarPago(idPago);
            listarPagos();
        }
    }

    //Metodo para configurar los comboBox
    private void configurarComboBox() {
        ObservableList<String> grados = FXCollections.observableArrayList("Primer", "Segundo", "Tercer", "Cuarto", "Quinto");
        ObservableList<String> secciones = FXCollections.observableArrayList("A", "B", "C");
        ObservableList<String> estados = FXCollections.observableArrayList("Pagado", "Pendiente", "Vencido");

        comboGradoB.setItems(grados);
        comboSeccionB.setItems(secciones);
        comboEstado.setItems(estados);

        comboGradoB.getSelectionModel().clearSelection();
        comboSeccionB.getSelectionModel().clearSelection();
        comboEstado.getSelectionModel().clearSelection();
    }

    public void filtrarPagos() {
        String grado = comboGradoB.getValue();
        String seccion = comboSeccionB.getValue();
        String estado = comboEstado.getValue();

        DAO_Pago dao = new DAO_Pago();
        dao.listarPagosFiltrados(grado, seccion, estado);

        ArrayList<Object[]> lista = dao.getListaPagos();

        javafx.scene.control.TableView<Object[]> tabla = (javafx.scene.control.TableView<Object[]>) tablaPagos;
        tabla.getItems().clear();
        tabla.getItems().addAll(lista);
    }
}

package Forms;
import Clases.ClasesFinanzas.ComprobantePagoPDF;
import Clases.ConexionBD.Entidades_CRUD.DAO_Cuota;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import java.sql.Date;
import java.text.SimpleDateFormat;
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
        DAO_Cuota dao = new DAO_Cuota();
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

            DAO_Cuota dao = new DAO_Cuota();
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
            DAO_Cuota dao = new DAO_Cuota();
            dao.eliminarPago(idPago);
            listarPagos();
        }
    }

    //Metodo para configurar los comboBox
    private void configurarComboBox() {
        ObservableList<String> grados = FXCollections.observableArrayList("Todos", "Primer", "Segundo", "Tercer", "Cuarto", "Quinto");
        ObservableList<String> secciones = FXCollections.observableArrayList("Todos", "A", "B", "C");
        ObservableList<String> estados = FXCollections.observableArrayList("Todos", "Pagado", "Pendiente", "Vencido");

        comboGradoB.setItems(grados);
        comboSeccionB.setItems(secciones);
        comboEstado.setItems(estados);

        comboGradoB.getSelectionModel().clearSelection();
        comboSeccionB.getSelectionModel().clearSelection();
        comboEstado.getSelectionModel().clearSelection();
    }

    //Metodo para filtrar los pagos
    public void filtrarPagos() {
        String grado = comboGradoB.getValue();
        String seccion = comboSeccionB.getValue();
        String estado = comboEstado.getValue();

        DAO_Cuota dao = new DAO_Cuota();

        if ("Todos".equals(grado)){
            grado = null;
        }
        if ("Todos".equals(seccion)) {
            seccion = null;
        }
        if ("Todos".equals(estado)) {
            estado = null;
        }

        dao.listarPagosFiltrados(grado, seccion, estado);

        ArrayList<Object[]> lista = dao.getListaPagos();

        javafx.scene.control.TableView<Object[]> tabla = (javafx.scene.control.TableView<Object[]>) tablaPagos;
        tabla.getItems().clear();
        tabla.getItems().addAll(lista);
    }

    //Metodo para imprimir el comprobante
    public void imprimiComprobante() {
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

        String estado = (String) filaSeleccionada[7];
        if (!estado.equalsIgnoreCase("Pagado")) {
            Alert info = new Alert(Alert.AlertType.INFORMATION);
            info.setTitle("Estado no válido");
            info.setHeaderText(null);
            info.setContentText("Solo se pueden imprimir comprobantes para pagos con estado 'Pagado'.");
            info.showAndWait();
            return;
        }

        String codigoAlumno = String.valueOf(filaSeleccionada[1]);

        String fechaPago = String.valueOf(filaSeleccionada[6]);

        String metodoPago = (String) filaSeleccionada[9];
        Double montoPago = (Double) filaSeleccionada[8];

        ComprobantePagoPDF comprobantePagoPDF = new ComprobantePagoPDF(codigoAlumno, fechaPago, metodoPago, montoPago);

        comprobantePagoPDF.generarComprobante();

        Alert success = new Alert(Alert.AlertType.INFORMATION);
        success.setTitle("Comprobante Generado");
        success.setHeaderText(null);
        success.setContentText("El comprobante de pago ha sido generado exitosamente.");
        success.showAndWait();
    }
}

package Forms;
import Clases.ClasesFinanzas.ComprobantePagoPDF;
import Clases.ConexionBD.Entidades_CRUD.DAO_Cuota;
import Clases.ConexionBD.Entidades_CRUD.DAO_Pagos;
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
    }

    //Cajas de busqueda y comboBox
    @FXML private javafx.scene.control.ComboBox<String> comboGradoB;
    @FXML private javafx.scene.control.ComboBox<String> comboSeccionB;

    //Botones
    @FXML private javafx.scene.control.Button btnImprimirComprobante;

    //Tablas y columnas
    @FXML private Object tablaPagos;
    @FXML private Object columCodigo;
    @FXML private Object columNombreP;
    @FXML private Object columApellidoP;
    @FXML private Object columFechaP;
    @FXML private Object columMonto;
    @FXML private Object columMetodo;

    //Metodo configurar la tabla de los pagos
    private void configurarTablaPagos() {
        javafx.scene.control.TableView<Object[]> tabla = (javafx.scene.control.TableView<Object[]>) tablaPagos;

        javafx.scene.control.TableColumn<Object[], String> codigo = (javafx.scene.control.TableColumn<Object[], String>) columCodigo;
        javafx.scene.control.TableColumn<Object[], String> nombre = (javafx.scene.control.TableColumn<Object[], String>) columNombreP;
        javafx.scene.control.TableColumn<Object[], String> apellido = (javafx.scene.control.TableColumn<Object[], String>) columApellidoP;
        javafx.scene.control.TableColumn<Object[], String> fecha = (javafx.scene.control.TableColumn<Object[], String>) columFechaP;
        javafx.scene.control.TableColumn<Object[], Double> monto = (javafx.scene.control.TableColumn<Object[], Double>) columMonto;
        javafx.scene.control.TableColumn<Object[], String> metodo = (javafx.scene.control.TableColumn<Object[], String>) columMetodo;

        codigo.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty((String) data.getValue()[1]));
        nombre.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty((String) data.getValue()[2]));
        apellido.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
                (String) data.getValue()[4] + " " + (String) data.getValue()[5]));
        fecha.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue()[6].toString()));
        monto.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>((Double) data.getValue()[7]));
        metodo.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty((String) data.getValue()[8]));
    }

    //Metodo para listar pagos
    private void listarPagos() {
        DAO_Pagos dao = new DAO_Pagos();
        dao.Listar();
        ArrayList<Object[]> lista = dao.getListaCuotas();

        javafx.scene.control.TableView<Object[]> tabla = (javafx.scene.control.TableView<Object[]>) tablaPagos;
        tabla.getItems().clear();
        tabla.getItems().addAll(lista);
    }

    //Metodo para configurar los comboBox
    private void configurarComboBox() {
        ObservableList<String> grados = FXCollections.observableArrayList("Todos", "Primer", "Segundo", "Tercer", "Cuarto", "Quinto");
        ObservableList<String> secciones = FXCollections.observableArrayList("Todos", "A", "B", "C");

        comboGradoB.setItems(grados);
        comboSeccionB.setItems(secciones);

        comboGradoB.getSelectionModel().select(0);
        comboSeccionB.getSelectionModel().select(0);
    }

    //Metodo para filtrar los pagos
    public void filtrarPagos() {
        String grado = comboGradoB.getValue();
        String seccion = comboSeccionB.getValue();

        DAO_Pagos dao = new DAO_Pagos();

        if ("Todos".equals(grado)){
            grado = null;
        }
        if ("Todos".equals(seccion)) {
            seccion = null;
        }

        dao.listarPagosFiltrados(grado, seccion);

        ArrayList<Object[]> lista = dao.getListaCuotas();

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


        String codigoAlumno = String.valueOf(filaSeleccionada[1]);

        String fechaPago = String.valueOf(filaSeleccionada[6]);

        String metodoPago = (String) filaSeleccionada[8];
        Double montoPago = (Double) filaSeleccionada[7];

        ComprobantePagoPDF comprobantePagoPDF = new ComprobantePagoPDF(codigoAlumno, fechaPago, metodoPago, montoPago);

        comprobantePagoPDF.generarComprobante();

        Alert success = new Alert(Alert.AlertType.INFORMATION);
        success.setTitle("Comprobante Generado");
        success.setHeaderText(null);
        success.setContentText("El comprobante de pago ha sido generado exitosamente.");
        success.showAndWait();
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
            DAO_Pagos dao = new DAO_Pagos();
            dao.eliminarPago(idPago);
            listarPagos();
        }
    }
}

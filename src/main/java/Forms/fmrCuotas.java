package Forms;

import Clases.ConexionBD.Entidades_CRUD.DAO_Cuota;
import Clases.ConexionBD.Entidades_CRUD.DAO_Pagos;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class fmrCuotas {
    @FXML
    private void initialize() {
        configurarComboBox();
        soloLectura();
        configurarTablaCuotas();
        listarCuotas();
        barrabusqueda.textProperty().addListener((obs, oldText, newText) -> filtroCuotas());
        comboGrado.setOnAction(event -> filtroCuotas());
        comboSeccion.setOnAction(event -> filtroCuotas());
        comboEstado.setOnAction(event -> filtroCuotas());
        tablaCuotas.setOnMouseClicked(event -> colocarDatos());
        btnRegistrarPago.setOnAction(event -> registrarPago());

    }

    //Tabla y columnas
    @FXML private TableView<Object[]> tablaCuotas;
    @FXML private TableColumn<Object[], String> columCodigo;
    @FXML private TableColumn<Object[], String> columNombre;
    @FXML private TableColumn<Object[], String> columApellido;
    @FXML private TableColumn<Object[], String> columMonto;
    @FXML private TableColumn<Object[], String> columConcepto;
    @FXML private TableColumn<Object[], String> columFechaVencimiento;
    @FXML private TableColumn<Object[], String> columEstado;

    //Combo box
    @FXML private ComboBox<String> comboGrado;
    @FXML private ComboBox<String> comboSeccion;
    @FXML private ComboBox<String> comboEstado;
    @FXML private ComboBox<String> comboMetodo;

    //Text Fields
    @FXML private TextField barrabusqueda;
    @FXML private TextField codigoAlumno;
    @FXML private DatePicker fechaPago;
    @FXML private TextField montoCuota;
    @FXML private TextField conceptoCuota;

    //Boton registro Pago
    @FXML private Button btnRegistrarPago;


    //Configuracion de la tabla
    private void configurarTablaCuotas() {
        columCodigo.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue()[1])));
        columNombre.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf((String) data.getValue()[2])));
        columApellido.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf((String) data.getValue()[3])));
        columMonto.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue()[5])));
        columConcepto.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue()[4])));
        columFechaVencimiento.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue()[6])));
        columEstado.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue()[7])));
    }

    //Lista general de cuotas
    private void listarCuotas() {
        DAO_Cuota daoCuotas = new DAO_Cuota();
        daoCuotas.listarCuotasConAlumno();

        ArrayList<Object[]> lista = daoCuotas.getListaCuotas();
        ObservableList<Object[]> datos = FXCollections.observableArrayList(lista);

        tablaCuotas.setItems(datos);
    }

    //Configuracion de los Filtros
    public void configurarComboBox() {
        ObservableList<String> Grados = FXCollections.observableArrayList("Todos", "Primer", "Segundo", "Tercer", "Cuarto", "Quinto");
        ObservableList<String> Secciones = FXCollections.observableArrayList("Todos", "A", "B", "C");
        ObservableList<String> Estados = FXCollections.observableArrayList("Todos", "Pendiente", "Vencido", "Pagado");
        ObservableList<String> Metodos = FXCollections.observableArrayList("Efectivo","Targeta","Transferencia","Yape","Plin");

        comboGrado.setItems(Grados);
        comboSeccion.setItems(Secciones);
        comboEstado.setItems(Estados);
        comboMetodo.setItems(Metodos);

        comboGrado.getSelectionModel().select(0);
        comboSeccion.getSelectionModel().select(0);
        comboEstado.getSelectionModel().select(0);
        comboMetodo.getSelectionModel().select(-1);
    }

    //Listar cuotas con filtro
    public void filtroCuotas() {
        String texto = barrabusqueda.getText();
        String grado = comboGrado.getValue().equals("Todos") ? null : comboGrado.getValue();
        String seccion = comboSeccion.getValue().equals("Todos") ? null : comboSeccion.getValue();
        String estado = comboEstado.getValue().equals("Todos") ? null : comboEstado.getValue();

        DAO_Cuota daoCuotas = new DAO_Cuota();
        daoCuotas.listarCuotasFiltradas(grado, seccion, estado);
        ArrayList<Object[]> lista = daoCuotas.getListaCuotas();

        ObservableList<Object[]> datosCuotas = FXCollections.observableArrayList(lista);
        FilteredList<Object[]> filtrados = new FilteredList<>(datosCuotas, p -> true);

        filtrados.setPredicate(fila -> {
            if (texto == null || texto.isEmpty()) return true;

            String filtro = texto.toLowerCase();
            // Ejemplo: filtra por código alumno, nombres y apellidos
            return String.valueOf(fila[1]).toLowerCase().contains(filtro)
                    || String.valueOf(fila[2]).toLowerCase().contains(filtro)
                    || String.valueOf(fila[3]).toLowerCase().contains(filtro);
        });

        tablaCuotas.setItems(filtrados);
    }

    //Creacion de pago de la cuota
    public void colocarDatos(){
        DAO_Pagos daoPagos = new DAO_Pagos();
        Object[] fila = tablaCuotas.getSelectionModel().getSelectedItem();

        if (fila == null) {
            Alert advertencia = new Alert(Alert.AlertType.WARNING);
            advertencia.setTitle("Advertencia");
            advertencia.setHeaderText(null);
            advertencia.setContentText("Debe seleccionar un Cuota en la tabla.");
            advertencia.showAndWait();
            return;
        }

        String estado = fila[7].toString().trim();

        if (estado.equals("Pagado")) {
            Alert advertencia = new Alert(Alert.AlertType.WARNING);
            advertencia.setTitle("Advertencia");
            advertencia.setHeaderText(null);
            advertencia.setContentText("Debe seleccionar un cuota vencida o pendiente.");
            advertencia.showAndWait();
            return;
        }

        codigoAlumno.setText(fila[1].toString());
        fechaPago.setValue(LocalDate.parse(LocalDate.now().toString()));
        montoCuota.setText(fila[5].toString());
        conceptoCuota.setText(fila[4].toString());

    };

    public void registrarPago(){
        DAO_Pagos daoPagos = new DAO_Pagos();
        Object[] fila = tablaCuotas.getSelectionModel().getSelectedItem();

        if (fila == null) {
            Alert advertencia = new Alert(Alert.AlertType.WARNING);
            advertencia.setTitle("Advertencia");
            advertencia.setHeaderText(null);
            advertencia.setContentText("Debe seleccionar un Cuota en la tabla.");
            advertencia.showAndWait();
            return;
        }

        String estado = fila[7].toString().trim();

        if (estado.equals("Pagado")) {
            Alert advertencia = new Alert(Alert.AlertType.WARNING);
            advertencia.setTitle("Advertencia");
            advertencia.setHeaderText(null);
            advertencia.setContentText("Debe seleccionar un cuota vencida o pendiente.");
            advertencia.showAndWait();
            return;
        }

        if (comboEstado.getValue().equals(null)) {
            Alert advertencia = new Alert(Alert.AlertType.WARNING);
            advertencia.setTitle("Advertencia");
            advertencia.setHeaderText(null);
            advertencia.setContentText("Debe seleccionar un metodo de pago");
            advertencia.showAndWait();
            return;
        }

        int idcuota = Integer.parseInt(fila[0].toString());
        String metodo = comboMetodo.getValue();

        daoPagos.registrarPago(idcuota,metodo);
        filtroCuotas();
        limpiar();
    }

    public void soloLectura(){
        codigoAlumno.setEditable(false);
        codigoAlumno.setMouseTransparent(true);
        fechaPago.setEditable(false);
        fechaPago.setMouseTransparent(true);
        montoCuota.setEditable(false);
        montoCuota.setMouseTransparent(true);
        conceptoCuota.setEditable(false);
        conceptoCuota.setMouseTransparent(true);
    }

    public void limpiar(){
        codigoAlumno.setText("");
        fechaPago.setValue(null);
        montoCuota.setText("");
        conceptoCuota.setText("");
        comboMetodo.setValue(null);
    }
}

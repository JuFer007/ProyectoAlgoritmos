package Forms;
import Clases.ClasesFinanzas.ExportarEXCEL;
import Clases.ConexionBD.Entidades_CRUD.DAO_Reportes;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import javax.swing.*;
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
    @FXML private Button btnExportarExcel;

    //Caja para mostrar el total
    @FXML JTextArea totalRecaudado;

    //Variables
    private int mesActual = 1;
    DAO_Reportes dao = new DAO_Reportes();

    @FXML
    private void initialize() {
        configurarColumnasTabla();
        cargarPagos();
        btnExportarExcel.setOnAction(event -> exportarEXCEL());
    }

    //Metodo para cargar los pagos segun el mes y año
    public void cargarPagos() {
        dao.listarCuotas(mesActual);
        ArrayList<Object[]> lista = dao.getListapagos();
        ObservableList<Object[]> obs = FXCollections.observableArrayList(lista);

        tablaPagos.getItems().clear();
        tablaPagos.getItems().addAll(obs);
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

    //Metodo para exportar en excel
    public void exportarEXCEL() {
        // Crear un ComboBox con los meses del año
        ComboBox<String> comboBoxMes = new ComboBox<>();
        comboBoxMes.getItems().addAll("Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
                "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre");

        // Crear un VBox para contener el ComboBox
        VBox vbox = new VBox(comboBoxMes);

        // Crear el Alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Seleccionar Mes");
        alert.setHeaderText("Selecciona el mes que deseas exportar:");
        alert.getDialogPane().setContent(vbox);

        // Botón OK para confirmar la selección
        alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);

        // Mostrar el alert y esperar la selección del usuario
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                String selectedMonth = comboBoxMes.getSelectionModel().getSelectedItem();

                int numeroMes = getMonthNumber(selectedMonth);

                if (numeroMes != -1) {
                    ExportarEXCEL exportar = new ExportarEXCEL();
                    exportar.exportarExcel(numeroMes);

                    Alert alertSuccess = new Alert(Alert.AlertType.INFORMATION);
                    alertSuccess.setTitle("Proceso Completo");
                    alertSuccess.setHeaderText("Exportación exitosa");
                    alertSuccess.setContentText("Los datos se han exportado correctamente.");
                    alertSuccess.showAndWait();
                } else {
                    Alert alertError = new Alert(Alert.AlertType.ERROR);
                    alertError.setTitle("Error");
                    alertError.setHeaderText("Mes no válido");
                    alertError.setContentText("Por favor, selecciona un mes válido.");
                    alertError.showAndWait();
                }
            }
        });
    }

    //Metodo para convertir el nombre del mes a su número
    private int getMonthNumber(String month) {
        switch (month) {
            case "Enero": return 1;
            case "Febrero": return 2;
            case "Marzo": return 3;
            case "Abril": return 4;
            case "Mayo": return 5;
            case "Junio": return 6;
            case "Julio": return 7;
            case "Agosto": return 8;
            case "Septiembre": return 9;
            case "Octubre": return 10;
            case "Noviembre": return 11;
            case "Diciembre": return 12;
            default: return -1;
        }
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
        if (mesActual > 0) {
            mesActual--;
            cargarPagos();
        }
    }
}

package Forms;
import Clases.ConexionBD.Entidades_DAO.DAO_Reportes;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.Map;

public class fmrReporteGeneral {
    @FXML
    public void initialize() {
        remplazarLabels();
        cargarGraficoAlumnos();
        cargarGraficoDePersonal();
    }

    //Botones de interacciion
    @FXML private Button btnExportarPDF;
    @FXML private Button btnActualizar;

    //Labels del total de cada entidad
    @FXML private Label lblTotalAlumnos;
    @FXML private Label lblTotalProfesores;
    @FXML private Label lblTotalTrabajadores;
    @FXML private Label lblTotalApoderados;

    //Graficos de reportes
    @FXML private BarChart<String, Number> graficoAlumnos;
    @FXML private BarChart<String, Number> graficoProfesores;

    //Metodo para modificar los labels
    private void remplazarLabels() {
        int totalAlumnos = DAO_Reportes.obtenerTotalAlumnos();
        int totalProfesores = DAO_Reportes.obtenerTotalProfesores();
        int totalTrabajadores = DAO_Reportes.obtenerTotalTrabajadores();
        int totalApoderados = DAO_Reportes.obtenerTotalDeApoderados();

        lblTotalAlumnos.setText(String.valueOf(totalAlumnos));
        lblTotalProfesores.setText(String.valueOf(totalProfesores));
        lblTotalTrabajadores.setText(String.valueOf(totalTrabajadores));
        lblTotalApoderados.setText(String.valueOf(totalApoderados));
    }

    private void cargarGraficoAlumnos() {
        DAO_Reportes dao = new DAO_Reportes();
        Map<String, Integer> datos = dao.alumnosPorGrado();

        graficoAlumnos.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Alumnos por grado");

        for (Map.Entry<String, Integer> entry : datos.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }
        graficoAlumnos.getData().add(series);

        Platform.runLater(() -> {
            for (XYChart.Data<String, Number> data : series.getData()) {
                data.getNode().setStyle("-fx-bar-fill: #acc08f;");
            }
        });
    }

    private void cargarGraficoDePersonal() {
        DAO_Reportes dao = new DAO_Reportes();
        Map<String, Integer> datos = dao.resumenPersonal();

        graficoProfesores.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Datos de la Institución");

        for (Map.Entry<String, Integer> entry : datos.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }
        graficoProfesores.getData().add(series);

        Platform.runLater(() -> {
            for (XYChart.Data<String, Number> data : series.getData()) {
                data.getNode().setStyle("-fx-bar-fill: #acc08f;");
            }
        });
    }
}

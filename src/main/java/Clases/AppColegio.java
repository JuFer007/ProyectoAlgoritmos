package Clases;
import Clases.ClasesGestionEscolar.LibretaNotasPDF;
import com.itextpdf.text.Document;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.FileOutputStream;

public class AppColegio extends Application {

    private double x = 0;
    private double y = 0;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Formularios/Principales/IncioSesion.fxml"));

        Scene scene = new Scene(root);

        root.setOnMousePressed((MouseEvent event) -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });

        root.setOnMouseDragged((MouseEvent event) -> {
            primaryStage.setX(event.getScreenX() - x);
            primaryStage.setY(event.getScreenY() - y);

            primaryStage.setOpacity(0.8);
        });

        root.setOnMouseReleased((MouseEvent event) -> {
            primaryStage.setOpacity(1);
        });

        primaryStage.initStyle(StageStyle.UNDECORATED);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        String codigoAlumno = "AL00000002"; // Este es el código del alumno (puedes obtenerlo de donde lo necesites)
        int idMatricula = 2; // El idMatricula que se necesita para obtener los cursos y notas

        try {
            // Crear un documento nuevo con un archivo de salida
            FileOutputStream fileOutputStream = new FileOutputStream("LibretaDeNotas_" + codigoAlumno + ".pdf");
            Document documento = new Document();

            // Crear una instancia de LibretaNotasPDF
            LibretaNotasPDF libretaNotasPDF = new LibretaNotasPDF(documento, fileOutputStream, codigoAlumno, idMatricula);

            // Generar el PDF
            libretaNotasPDF.generarLibreta();

            System.out.println("Libreta de notas generada correctamente.");

        } catch (Exception e) {
            System.err.println("Error al generar la libreta de notas: " + e.getMessage());
            e.printStackTrace();
        }
    }
}


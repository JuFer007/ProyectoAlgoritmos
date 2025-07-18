module ColegioSystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires jdk.jshell;
    requires mysql.connector.j;
    requires itextpdf;
    requires jxl;
    requires java.desktop;

    opens Clases to javafx.fxml;
    opens Forms to javafx.fxml, javafx.graphics;

    exports Clases;
}

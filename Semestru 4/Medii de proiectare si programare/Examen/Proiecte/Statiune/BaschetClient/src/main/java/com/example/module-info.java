module com.example.baschetgui {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires org.apache.logging.log4j;
    requires java.sql;
    requires hibernate.core;

    opens com.example.baschetgui to javafx.fxml;
    opens com.example.baschetgui.cs.models to javafx.fxml, javafx.base;

    exports com.example.baschetgui;

}
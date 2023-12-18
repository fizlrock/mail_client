module com.evosome.jmailfx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires org.eclipse.angus.mail;

    opens com.evosome.jmailfx to javafx.fxml;
    exports com.evosome.jmailfx;
}
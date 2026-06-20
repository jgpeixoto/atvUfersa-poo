module Universidade {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens br.edu.ufersa.universidade.view to javafx.fxml, javafx.graphics;
    exports br.edu.ufersa.universidade.view;
    opens br.edu.ufersa.universidade.controller to javafx.fxml, javafx.graphics;
    exports br.edu.ufersa.universidade.controller;
}

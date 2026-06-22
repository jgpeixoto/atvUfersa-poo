module Universidade {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;

    opens br.edu.ufersa.universidade to javafx.fxml, javafx.graphics;
    opens br.edu.ufersa.universidade.view to javafx.fxml, javafx.graphics;
    exports br.edu.ufersa.universidade.view;
    opens br.edu.ufersa.universidade.controller to javafx.fxml, javafx.graphics;
    exports br.edu.ufersa.universidade.controller;
    exports br.edu.ufersa.universidade.common;
    exports br.edu.ufersa.universidade.utils;
    opens br.edu.ufersa.universidade.utils to javafx.fxml, javafx.graphics;
}

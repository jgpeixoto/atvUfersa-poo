package br.edu.ufersa.universidade.view;

import br.edu.ufersa.universidade.controller.GerentePartTurmaController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class GerentePartTurmaView extends BaseView {
    @Override
    protected String getResName() {
        return "GerentePartTurmaView";
    }

    @Override
    public void onStart(Stage primaryStage, Parent root, FXMLLoader loader) {
        GerentePartTurmaController control = loader.getController();
        control.initialize();
    }
}

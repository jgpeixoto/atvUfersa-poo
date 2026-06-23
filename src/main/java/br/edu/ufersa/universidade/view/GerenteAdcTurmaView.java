package br.edu.ufersa.universidade.view;

import br.edu.ufersa.universidade.controller.GerenteAdcTurmaController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class GerenteAdcTurmaView extends BaseView {
    @Override
    protected String getResName() {
        return "GerenteAdcTurmaView";
    }

    @Override
    protected void onStart(Stage primaryStage, Parent root, FXMLLoader loader) {
        GerenteAdcTurmaController control = loader.getController();
        control.initialize();
    }
}

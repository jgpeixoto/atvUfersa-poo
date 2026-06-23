package br.edu.ufersa.universidade.view;

import br.edu.ufersa.universidade.controller.GerenteAdcDisciplinaController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class GerenteAdcDisciplinaView extends BaseView {
    @Override
    protected String getResName() {
        return "GerenteAdcDisciplinaView";
    }

    @Override
    protected void onStart(Stage primaryStage, Parent root, FXMLLoader loader) {
        GerenteAdcDisciplinaController control = loader.getController();
        control.initialize();
    }
}

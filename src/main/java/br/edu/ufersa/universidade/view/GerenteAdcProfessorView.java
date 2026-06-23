package br.edu.ufersa.universidade.view;

import br.edu.ufersa.universidade.controller.GerenteAdcProfessorController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class GerenteAdcProfessorView extends BaseView {
    @Override
    protected String getResName() {
        return "GerenteAdcProfessorView";
    }

    @Override
    protected void onStart(Stage primaryStage, Parent root, FXMLLoader loader) {
        GerenteAdcProfessorController control = loader.getController();
        control.initialize();
    }
}

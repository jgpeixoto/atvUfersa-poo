package br.edu.ufersa.universidade.view;

import br.edu.ufersa.universidade.controller.GerenteAdcAlunoController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class GerenteAdcAlunoView extends BaseView {
    @Override
    protected String getResName() {
        return "GerenteAdcAlunoView";
    }

    @Override
    protected void onStart(Stage primaryStage, Parent root, FXMLLoader loader) {
        GerenteAdcAlunoController control = loader.getController();
        control.initialize();
    }
}

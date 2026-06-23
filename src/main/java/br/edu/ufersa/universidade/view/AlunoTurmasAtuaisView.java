package br.edu.ufersa.universidade.view;

import br.edu.ufersa.universidade.controller.AlunoTurmasAtuaisController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class AlunoTurmasAtuaisView extends BaseView {
    @Override
    protected String getResName() {
        return "aluno_turmas_atuais";
    }

    @Override
    protected void onStart(Stage primaryStage, Parent root, FXMLLoader loader)
    {
        AlunoTurmasAtuaisController control = loader.getController();
        control.initialize();
    }
}

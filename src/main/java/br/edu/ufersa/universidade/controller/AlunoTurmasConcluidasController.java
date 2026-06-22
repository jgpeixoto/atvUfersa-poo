package br.edu.ufersa.universidade.controller;
import br.edu.ufersa.universidade.model.entities.Turma;
import br.edu.ufersa.universidade.utils.WindowUtils;
import br.edu.ufersa.universidade.view.WelcomeView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;


public class AlunoTurmasConcluidasController {
    @FXML private TextField campoBusca;
    @FXML private TableView<Turma> tableTurmasConcluidas;

    @FXML public void handleAbrirTurmasAtuais(ActionEvent e) {

    }

    @FXML public void handleSair(ActionEvent e) {
        WindowUtils.SwitchToWindow(WelcomeView.class, e);
    }

}

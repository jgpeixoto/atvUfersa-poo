package br.edu.ufersa.universidade.controller;

import br.edu.ufersa.universidade.model.entities.Aluno;
import br.edu.ufersa.universidade.utils.WindowUtils;
import br.edu.ufersa.universidade.view.ProfessorTurmasView;
import br.edu.ufersa.universidade.view.WelcomeView;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ProfessorNotasController {
    @FXML private TextField campoBusca;
    @FXML private TableView<Aluno> tableNotas;
    /*
    @FXML private TableColumn<Aluno, SimpleStringProperty> colNome;
    @FXML private TableColumn<Aluno, SimpleIntegerProperty> colP1;
    @FXML private TableColumn<Aluno, SimpleIntegerProperty> colP2;
    @FXML private TableColumn<Aluno, SimpleIntegerProperty> colP3;
    @FXML private TableColumn<Aluno, SimpleIntegerProperty> colMedia;
    @FXML private TableColumn<Aluno, SimpleStringProperty> colStatus;
    */

    @FXML public void sair(ActionEvent e) {
        WindowUtils.SwitchToWindow(WelcomeView.class, e);
    }
    @FXML public void handleAbrirTurmas(ActionEvent e) {
        WindowUtils.SwitchToWindow(ProfessorTurmasView.class, campoBusca);
    }
}

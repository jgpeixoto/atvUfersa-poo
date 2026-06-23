package br.edu.ufersa.universidade.controller;

import br.edu.ufersa.universidade.model.entities.Turma;
import br.edu.ufersa.universidade.utils.WindowUtils;
import br.edu.ufersa.universidade.view.WelcomeView;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class ProfessorTurmasController {
    @FXML private TableView<Turma> tableTurmas;
    /*
    @FXML private TableColumn<Turma, SimpleIntegerProperty> colTurma; // curId da turma
    @FXML private TableColumn<Turma, SimpleStringProperty> colDisciplina;
    @FXML private TableColumn<Turma, SimpleStringProperty> colCodigo;
    @FXML private TableColumn colFrequencia;
    @FXML private TableColumn colNotas;
    */

    @FXML public void sair(ActionEvent e) {
        WindowUtils.SwitchToWindow(WelcomeView.class, e);
    }

}

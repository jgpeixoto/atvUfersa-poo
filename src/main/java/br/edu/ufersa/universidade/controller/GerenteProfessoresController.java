package br.edu.ufersa.universidade.controller;

import br.edu.ufersa.universidade.model.entities.Turma;
import br.edu.ufersa.universidade.utils.WindowUtils;
import br.edu.ufersa.universidade.view.GerenteAdcProfessorView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import br.edu.ufersa.universidade.model.entities.Professor;

public class GerenteProfessoresController extends BaseGerenteController {
    @FXML private TextField campoBusca;
    @FXML private Button btnAdicionar;
    @FXML private TableView<Professor> tabelaProfessores;

    @FXML public void adicionarProfessor(ActionEvent e) {
        GerenteAdcProfessorController.cpfAtual = "";
        WindowUtils.SwitchToWindow(GerenteAdcProfessorView.class, e);
    }
}

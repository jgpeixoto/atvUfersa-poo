package br.edu.ufersa.universidade.controller;

import br.edu.ufersa.universidade.utils.WindowUtils;
import br.edu.ufersa.universidade.view.GerenteAlunosView;
import br.edu.ufersa.universidade.view.GerenteProfessoresView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


public class GerenteAdcProfessorController extends BaseGerenteController {
    @FXML private TextField campoNome;
    @FXML private TextField campoEndereco;
    @FXML private TextField campoCpf;
    @FXML private Button btnCancelar;
    @FXML private Button btnSalvar;

    @FXML public void salvarProfessor(ActionEvent e) {
        WindowUtils.SwitchToWindow(GerenteProfessoresView.class, e);
    }

    @FXML public void cancelar(ActionEvent e) {
        WindowUtils.SwitchToWindow(GerenteProfessoresView.class, e);
    }

}

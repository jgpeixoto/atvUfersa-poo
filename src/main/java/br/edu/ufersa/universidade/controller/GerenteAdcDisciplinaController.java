package br.edu.ufersa.universidade.controller;

import br.edu.ufersa.universidade.utils.WindowUtils;
import br.edu.ufersa.universidade.view.GerenteDisciplinaView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class GerenteAdcDisciplinaController extends BaseGerenteController{
    @FXML private Button btnCancelar;
    @FXML private Button btnSalvar;
    @FXML private TextField campoNome;
    @FXML private TextField campoCodigo;

    @FXML public void cancelar(ActionEvent e) {
        WindowUtils.SwitchToWindow(GerenteDisciplinaView.class, e);
    }

    @FXML public void salvarDisciplina(ActionEvent e) {
        WindowUtils.SwitchToWindow(GerenteDisciplinaView.class, e);
    }
}

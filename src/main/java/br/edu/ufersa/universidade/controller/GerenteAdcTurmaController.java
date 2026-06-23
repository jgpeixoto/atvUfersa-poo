package br.edu.ufersa.universidade.controller;

import br.edu.ufersa.universidade.utils.WindowUtils;
import br.edu.ufersa.universidade.view.GerentePartTurmaView;
import br.edu.ufersa.universidade.view.GerenteTurmasView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import br.edu.ufersa.universidade.model.entities.Turma;

import java.util.ArrayList;

public class GerenteAdcTurmaController extends BaseGerenteController{

    @FXML private Button btnSair;
    @FXML private Button btnAdicionarParticipantes;
    @FXML private TextField campoDisciplina;
    @FXML private TextField campoHorario;
    @FXML private TextField campoLocal;
    @FXML private ComboBox<String> comboStatus;
    @FXML private Button btnCancelar;
    @FXML private Button btnSalvar;
    static int curTurmaId = -1;
    static String curProfCpf = ""; // INSERIR CPF DO PROFESSOR ATUAL
    static ArrayList<Long> curAlunoMatriculas = new ArrayList<Long>();

    @FXML public void abrirParticipantes(ActionEvent e) {
        WindowUtils.SwitchToWindow(GerentePartTurmaView.class, e);
    }

    @FXML public void cancelar(ActionEvent e) {
        WindowUtils.SwitchToWindow(GerenteTurmasView.class, e);
    }

    @FXML public void salvarTurma(ActionEvent e) {
        // save to db here (everything)
        WindowUtils.SwitchToWindow(GerenteTurmasView.class, e);
    }
}

package br.edu.ufersa.universidade.controller;

import br.edu.ufersa.universidade.model.entities.Disciplina;
import br.edu.ufersa.universidade.model.service.DisciplinaService;
import br.edu.ufersa.universidade.model.service.TurmaService;
import br.edu.ufersa.universidade.utils.WindowUtils;
import br.edu.ufersa.universidade.view.GerentePartTurmaView;
import br.edu.ufersa.universidade.view.GerenteTurmasView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import br.edu.ufersa.universidade.model.entities.Turma;

import java.sql.SQLException;
import java.util.ArrayList;

public class GerenteAdcTurmaController extends BaseGerenteController {
    @FXML private TextField campoDisciplina;
    @FXML private TextField campoHorario;
    @FXML private TextField campoLocal;
    @FXML private ComboBox<String> comboStatus;
    @FXML private Label labelError;

    private final DisciplinaService disService = new DisciplinaService();
    private final TurmaService turmaService = new TurmaService();

    static int curTurmaId = -1;

    static String curProfCpf = ""; // INSERIR CPF DO PROFESSOR ATUAL
    static ArrayList<Long> curAlunoMatriculas = new ArrayList<Long>();

    @FXML public void abrirParticipantes(ActionEvent e) {
        WindowUtils.SwitchToWindow(GerentePartTurmaView.class, e);
    }

    @FXML public void cancelar(ActionEvent e) {
        close();
    }

    @FXML public void salvarTurma(ActionEvent e) {
        String tipo = comboStatus.getValue();
        String codDisciplina = campoDisciplina.getText();
        String local = campoLocal.getText();
        String horario = campoHorario.getText();

        // save to db here (everything)

        close();
    }

    private boolean validateTurma() {
        String tipo = comboStatus.getValue();
        String codDisciplina = campoDisciplina.getText();
        String local = campoLocal.getText();
        String horario = campoHorario.getText();
        if (tipo == null) {
            labelError.setText("Por favor escolha o status.");
            return false;
        }
        if (codDisciplina == null || codDisciplina.isEmpty()) {
            labelError.setText("O código da disciplina não pode estar vazio.");
            return false;
        }
        if (local == null || local.isEmpty()) {
            labelError.setText("O local não pode estar vazio.");
            return false;
        }
        if (horario == null || horario.isEmpty()) {
            labelError.setText("O horário não pode estar vazio.");
            return false;
        }
        try {
            Disciplina dis = disService.buscarPorCodigo(codDisciplina);

        } catch (SQLException ignored) {}

        return true;
    }

    private void close() {
        curTurmaId = -1;
        curProfCpf = "";
        curAlunoMatriculas.clear();
        WindowUtils.SwitchToWindow(GerenteTurmasView.class, labelError);
    }
}

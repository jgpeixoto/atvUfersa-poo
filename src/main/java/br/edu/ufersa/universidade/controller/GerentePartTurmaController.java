package br.edu.ufersa.universidade.controller;

import br.edu.ufersa.universidade.model.entities.Aluno;
import br.edu.ufersa.universidade.model.entities.Professor;
import br.edu.ufersa.universidade.model.entities.Turma;
import br.edu.ufersa.universidade.model.service.TurmaService;
import br.edu.ufersa.universidade.utils.WindowUtils;
import br.edu.ufersa.universidade.view.GerenteAdcTurmaView;
import br.edu.ufersa.universidade.view.GerenteParticipantesAdcAlunoView;
import br.edu.ufersa.universidade.view.GerenteParticipantesAdcProfessorView;
import br.edu.ufersa.universidade.view.GerenteTurmasView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class GerentePartTurmaController extends BaseGerenteController {
    @FXML private TableView<Professor> tabelaProfessor;
    // @FXML private TableColumn<Professor, SimpleStringProperty> colProfessorNome;
    // @FXML private TableColumn<Professor, SimpleStringProperty> colProfessorCpf;
    @FXML private TableView<Aluno> tabelaAlunos;
    // @FXML private TableColumn<Aluno, SimpleStringProperty> colAlunoNome;
    // @FXML private TableColumn<Aluno, SimpleLongProperty> colAlunoMatricula;

    static String curProfCpf = "";
    static ArrayList<Long> curAlunoMatriculas = new ArrayList<Long>();

    public void initialize() {
        if (!curProfCpf.isEmpty()) {
            // popular tabela Professor aqui
        }
        if (!curAlunoMatriculas.isEmpty()) {
            // popular tabela Alunos aqui
        }
    }

    @FXML public void abrirAdicionarProfessor(ActionEvent e) {
        WindowUtils.SwitchToWindow(GerenteParticipantesAdcProfessorView.class, e);
    }
    @FXML public void abrirAdicionarAluno(ActionEvent e) {
        WindowUtils.SwitchToWindow(GerenteParticipantesAdcAlunoView.class, e);
    }
    @FXML public void cancelar(ActionEvent e) {
        close(e);
    }

    @FXML public void salvarParticipantes(ActionEvent e) {
        GerenteAdcTurmaController.curProfCpf = curProfCpf;
        GerenteAdcTurmaController.curAlunoMatriculas.clear();
        GerenteAdcTurmaController.curAlunoMatriculas.addAll(curAlunoMatriculas);
        close(e);
    }

    private void close(ActionEvent e) {
        curProfCpf = "";
        curAlunoMatriculas.clear();
        WindowUtils.SwitchToWindow(GerenteAdcTurmaView.class, e);
    }
}

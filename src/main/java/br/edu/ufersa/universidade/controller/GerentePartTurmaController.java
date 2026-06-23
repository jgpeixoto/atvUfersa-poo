package br.edu.ufersa.universidade.controller;

import br.edu.ufersa.universidade.model.entities.Aluno;
import br.edu.ufersa.universidade.model.entities.Professor;
import br.edu.ufersa.universidade.utils.WindowUtils;
import br.edu.ufersa.universidade.view.GerenteParticipantesAdcAlunoView;
import br.edu.ufersa.universidade.view.GerenteParticipantesAdcProfessorView;
import br.edu.ufersa.universidade.view.GerenteTurmasView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class GerentePartTurmaController extends BaseGerenteController {
    @FXML private TextField campoDisciplina;
    @FXML private Button btnAdicionarProfessor;
    @FXML private TableView<Professor> tabelaProfessor;
    // @FXML private TableColumn<Professor, SimpleStringProperty> colProfessorNome;
    // @FXML private TableColumn<Professor, SimpleStringProperty> colProfessorCpf;
    @FXML private Button btnAdicionarAluno;
    @FXML private TableView<Aluno> tabelaAlunos;
    // @FXML private TableColumn<Aluno, SimpleStringProperty> colAlunoNome;
    // @FXML private TableColumn<Aluno, SimpleLongProperty> colAlunoMatricula;
    @FXML private Button btnCancelar;
    @FXML private Button btnSalvar;
    static int curTurmaId; // INSERIR ID DA TURMA AQUI
    static String curProfCpf; // INSERIR CPF DO PROFESSOR ATUAL
    static ArrayList<Long> curAlunoMatriculas = new ArrayList<Long>();
    // INSERIR MATRICULAS DOS ALUNOS ATUAL

    @FXML public void abrirAdicionarProfessor(ActionEvent e) {
        WindowUtils.SwitchToWindow(GerenteParticipantesAdcProfessorView.class, e);
    }
    @FXML public void abrirAdicionarAluno(ActionEvent e) {
        WindowUtils.SwitchToWindow(GerenteParticipantesAdcAlunoView.class, e);
    }
    @FXML public void cancelar(ActionEvent e) {
        WindowUtils.SwitchToWindow(GerenteTurmasView.class, e);
    }

    @FXML public void salvarParticipantes(ActionEvent e) {
        // to-do: save to db
        WindowUtils.SwitchToWindow(GerenteTurmasView.class, e);
    }
}

package br.edu.ufersa.universidade.controller;

import br.edu.ufersa.universidade.model.entities.Aluno;
import br.edu.ufersa.universidade.model.entities.Professor;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

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

    @FXML public void abrirAdicionarProfessor(ActionEvent e) {

    }
    @FXML public void abrirAdicionarAluno(ActionEvent e) {

    }
    @FXML public void cancelar(ActionEvent e) {

    }
    @FXML public void salvarParticipantes(ActionEvent e) {

    }

}

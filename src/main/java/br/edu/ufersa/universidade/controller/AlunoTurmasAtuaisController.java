package br.edu.ufersa.universidade.controller;

import br.edu.ufersa.universidade.model.entities.Aluno;
import br.edu.ufersa.universidade.model.entities.Indice;
import br.edu.ufersa.universidade.model.entities.Turma;
import br.edu.ufersa.universidade.model.service.AlunoService;
import br.edu.ufersa.universidade.utils.WindowUtils;
import br.edu.ufersa.universidade.view.AlunoTurmasConcluidasView;
import br.edu.ufersa.universidade.view.WelcomeView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

import java.sql.SQLException;

public class AlunoTurmasAtuaisController {
    @FXML private Label lblNomeAluno; // olá, nome aluno
    @FXML private Label lblInfoAluno; // inserir Matrícula aqui
    @FXML private TableView<Turma> tableTurmas;
    @FXML private TableView<Indice> tableNotas;
    @FXML private VBox boxFrequencia;
    private final AlunoService alunoService = new AlunoService();
    private Aluno alunoAtual;

    public void initialize()
    {
        try {
            Aluno al = alunoService.buscarPorId(LoginController.curUser.getId());
            this.alunoAtual = al;
            lblNomeAluno.setText("Olá, " + al.getNome());
            lblInfoAluno.setText("Matrícula: " + al.getMatricula());
        } catch (SQLException ignored) {}
    }

    @FXML public void handleAbrirTurmasConcluidas() {
        WindowUtils.SwitchToWindow(AlunoTurmasConcluidasView.class, boxFrequencia);
    }

    @FXML public void handleSair(ActionEvent e) {
        WindowUtils.SwitchToWindow(WelcomeView.class, e);
    }
}
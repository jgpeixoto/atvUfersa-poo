package br.edu.ufersa.universidade.controller;

import br.edu.ufersa.universidade.model.entities.Indice;
import br.edu.ufersa.universidade.model.entities.Turma;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

public class AlunoTurmasAtuaisController {
    @FXML private Label lblNomeAluno; // olá, nome aluno
    @FXML private Label lblInfoAluno; // inserir Matrícula aqui
    @FXML private TableView<Turma> tableTurmas;
    @FXML private TableView<Indice> tableNotas;
    @FXML private VBox boxFrequencia;

    @FXML public void handleAbrirTurmasConcluidas(ActionEvent e) {

    }

    @FXML public void handleSair(ActionEvent e) {

    }
}
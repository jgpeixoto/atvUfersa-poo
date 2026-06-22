package br.edu.ufersa.universidade.controller;

import br.edu.ufersa.universidade.model.entities.Aluno;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ProfessorFrequenciaController {
    @FXML private Button btnSair;
    @FXML private TextField buscaAluno;
    @FXML private TableView<Aluno> tableFrequencia;
    @FXML private TableColumn<Aluno, SimpleStringProperty> colNome;
    @FXML private TableColumn<Aluno, SimpleLongProperty> colMatricula;
    @FXML private TableColumn<Aluno, SimpleIntegerProperty> colFaltas;

    @FXML public void sair(ActionEvent e) {

    }
    @FXML public void updateAlunos(ActionEvent e) {

    }
    @FXML public void handleAbrirTurmas(ActionEvent e) {

    }
}

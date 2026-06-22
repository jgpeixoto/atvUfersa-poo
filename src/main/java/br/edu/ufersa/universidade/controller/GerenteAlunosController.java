package br.edu.ufersa.universidade.controller;

import br.edu.ufersa.universidade.model.entities.Aluno;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class GerenteAlunosController {
    @FXML private Button btnSair;
    @FXML private TextField campoBusca;
    @FXML private Button btnAdicionar;
    @FXML private TableView<Aluno> tabelaAlunos;
    @FXML private TableColumn<Aluno, SimpleStringProperty> colNome;
    @FXML private TableColumn<Aluno, SimpleStringProperty> colEndereco;
    @FXML private TableColumn<Aluno, SimpleLongProperty> colMatricula;
    @FXML private TableColumn colAjustes;

    @FXML public void sair(ActionEvent e) {

    }
    @FXML public void adicionarAluno(ActionEvent e) {

    }

}

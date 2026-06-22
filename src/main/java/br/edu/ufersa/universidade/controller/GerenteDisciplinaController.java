package br.edu.ufersa.universidade.controller;

import br.edu.ufersa.universidade.model.entities.Disciplina;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class GerenteDisciplinaController {
    @FXML private Button btnSair;
    @FXML private TextField campoBusca;
    @FXML private Button btnAdicionar;
    @FXML private TableView<Disciplina> tabelaDisciplinas;
    @FXML private TableColumn<Disciplina, SimpleStringProperty> colNome;
    @FXML private TableColumn<Disciplina, SimpleStringProperty> colCodigo;
    @FXML private TableColumn colAjustes;

    @FXML public void sair(ActionEvent e) {

    }
    @FXML public void adicionarDisciplina(ActionEvent e) {

    }
}

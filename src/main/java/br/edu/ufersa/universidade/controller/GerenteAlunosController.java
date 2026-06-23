package br.edu.ufersa.universidade.controller;

import br.edu.ufersa.universidade.model.entities.Aluno;
import br.edu.ufersa.universidade.utils.WindowUtils;
import br.edu.ufersa.universidade.view.GerenteAdcAlunoView;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class GerenteAlunosController extends BaseGerenteController {
    @FXML private TextField campoBusca;
    @FXML private Button btnAdicionar;
    @FXML private TableView<Aluno> tabelaAlunos;
    /*
    @FXML private TableColumn colNome;
    @FXML private TableColumn colEndereco;
    @FXML private TableColumn colMatricula;
    @FXML private TableColumn colAjustes; */

    @FXML public void adicionarAluno(ActionEvent e) {
        GerenteAdcAlunoController.matriculaAtual = -1;
        WindowUtils.SwitchToWindow(GerenteAdcAlunoView.class, e);
    }

}

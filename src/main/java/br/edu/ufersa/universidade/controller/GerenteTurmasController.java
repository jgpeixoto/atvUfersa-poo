package br.edu.ufersa.universidade.controller;

import br.edu.ufersa.universidade.model.entities.Turma;
import br.edu.ufersa.universidade.utils.WindowUtils;
import br.edu.ufersa.universidade.view.GerenteAdcTurmaView;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class GerenteTurmasController extends BaseGerenteController {
    @FXML private TextField campoBusca;
    @FXML private Button btnAdicionar;
    @FXML private TableView<Turma> tabelaTurmas; /*
    @FXML private TableColumn<Turma, SimpleStringProperty> colDisciplina;
    @FXML private TableColumn<Turma, SimpleStringProperty> colHorario;
    @FXML private TableColumn<Turma, SimpleStringProperty> colAlunos;
    @FXML private TableColumn<Turma, SimpleStringProperty> colLocal;
    @FXML private TableColumn<Turma, SimpleStringProperty> colStatus;
    @FXML private TableColumn colAjustes; */



    @FXML public void adicionarTurma(ActionEvent e) {
        GerenteAdcTurmaController.curTurmaId = -1;
        GerenteAdcTurmaController.curProfCpf = "";
        GerenteAdcTurmaController.curAlunoMatriculas.clear();
        WindowUtils.SwitchToWindow(GerenteAdcTurmaView.class, e);
    }
}

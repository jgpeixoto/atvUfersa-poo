package br.edu.ufersa.universidade.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import br.edu.ufersa.universidade.model.entities.Turma;

public class GerenteAdcTurmaController {

    @FXML private Button btnSair;
    @FXML private Button btnAdicionarParticipantes;
    @FXML private TextField campoDisciplina;
    @FXML private TextField campoHorario;
    @FXML private TextField campoLocal;
    @FXML private TextField campoAlunos;
    @FXML private ComboBox<Turma.EstadoTurma> comboStatus;
    @FXML private Button btnCancelar;
    @FXML private Button btnSalvar;


    @FXML public void sair(ActionEvent e) {

    }

    @FXML public void abrirParticipantes(ActionEvent e) {

    }

    @FXML public void cancelar(ActionEvent e) {

    }

    @FXML public void salvarTurma(ActionEvent e) {

    }
}

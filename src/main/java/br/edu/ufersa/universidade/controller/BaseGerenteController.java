package br.edu.ufersa.universidade.controller;

import br.edu.ufersa.universidade.utils.WindowUtils;
import br.edu.ufersa.universidade.view.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class BaseGerenteController {
    @FXML private Button btnSair;
    @FXML private VBox btnDisciplina;
    @FXML private VBox btnTurmas;
    @FXML private VBox btnProfessores;
    @FXML private VBox btnAlunos;

    @FXML public void sair(ActionEvent e) {
        WindowUtils.SwitchToWindow(WelcomeView.class, e);
    }

    @FXML public void abrirDisciplina() {
        WindowUtils.SwitchToWindow(GerenteDisciplinaView.class, btnDisciplina);
    }
    @FXML public void abrirTurmas() {
        WindowUtils.SwitchToWindow(GerenteTurmasView.class, btnTurmas);
    }
    @FXML public void abrirProfessores() {
        WindowUtils.SwitchToWindow(GerenteProfessoresView.class, btnProfessores);
    }
    @FXML public void abrirAlunos() {
        WindowUtils.SwitchToWindow(GerenteAlunosView.class, btnAlunos);
    }
}

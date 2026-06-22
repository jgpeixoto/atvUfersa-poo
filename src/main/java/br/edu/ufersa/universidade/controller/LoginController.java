package br.edu.ufersa.universidade.controller;

import br.edu.ufersa.universidade.utils.WindowUtils;
import br.edu.ufersa.universidade.view.AlunoTurmasAtuaisView;
import br.edu.ufersa.universidade.view.GerenteAlunosView;
import br.edu.ufersa.universidade.view.ProfessorTurmasView;
import br.edu.ufersa.universidade.view.WelcomeView;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML private TextField campoUsuario;
    @FXML private PasswordField campoSenha;
    @FXML private ComboBox<String> comboPerfil;
    @FXML private Button btnEntrar;

    @FXML public void realizarLogin(ActionEvent e) {
        switch (comboPerfil.getValue()) {
            case "Aluno":
                WindowUtils.SwitchToWindow(AlunoTurmasAtuaisView.class, e);
                break;
            case "Professor":
                WindowUtils.SwitchToWindow(ProfessorTurmasView.class, e);
                break;
            case "Gerente":
                WindowUtils.SwitchToWindow(GerenteAlunosView.class, e);
                break;
        }
    }
}

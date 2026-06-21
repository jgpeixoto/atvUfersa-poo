package br.edu.ufersa.universidade.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

enum LoginType {
    Aluno,
    Professor,
    Admin
}

public class LoginController {
    @FXML private TextField campoUsuario;
    @FXML private PasswordField campoSenha;
    @FXML private ComboBox<LoginType> comboPerfil;
    @FXML private Button btnEntrar;

    @FXML public void realizarLogin(ActionEvent e) {

    }
}

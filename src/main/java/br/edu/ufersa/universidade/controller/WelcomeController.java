package br.edu.ufersa.universidade.controller;

import br.edu.ufersa.universidade.utils.WindowUtils;
import br.edu.ufersa.universidade.view.LoginView;
import br.edu.ufersa.universidade.view.WelcomeView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class WelcomeController {
    @FXML private Button btnLogin;

    @FXML public void abrirLogin(ActionEvent e) {
        WindowUtils.SwitchToWindow(LoginView.class, e);
    }
}

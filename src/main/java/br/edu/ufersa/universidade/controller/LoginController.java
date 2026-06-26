package br.edu.ufersa.universidade.controller;

import br.edu.ufersa.universidade.model.entities.Usuario;
import br.edu.ufersa.universidade.model.service.UsuarioService;
import br.edu.ufersa.universidade.utils.WindowUtils;
import br.edu.ufersa.universidade.view.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

import java.sql.SQLException;
import java.util.ArrayList;

public class LoginController {
    @FXML private TextField campoUsuario;
    @FXML private PasswordField campoSenha;
    @FXML private ComboBox<String> comboPerfil;
    @FXML private Label loginError;

    static Usuario curUser;
    private final UsuarioService usuarioService = new UsuarioService();

    private boolean validarLogin(String tipo, String user, String pass) {
        if (tipo == null) {
            loginError.setText("Por favor selecione um tipo de usuário.");
            return false;
        }
        if (user == null || user.isEmpty()) {
            loginError.setText("O usuário não pode estar vazio.");
            return false;
        }
        if (pass == null || pass.isEmpty()) {
            loginError.setText("A senha não pode estar vazia.");
            return false;
        }
        loginError.setText("");
        return true;
    }

    @FXML public void realizarLogin(ActionEvent e) {
        LoginController.curUser = null;
        String tipo = comboPerfil.getValue();
        String user = campoUsuario.getText().trim();
        String pass = campoSenha.getText();
        if (!validarLogin(tipo, user, pass))
            return;
        try {
            var list = usuarioService.buscarPorNome(user);
            var list2 = new ArrayList<Usuario>();
            for (var usuario : list) {
                switch (tipo) {
                    case "Aluno":
                        if (usuario.getTipo() == Usuario.TipoUsuario.Aluno)
                            list2.add(usuario);
                        break;
                    case "Professor":
                        if (usuario.getTipo() == Usuario.TipoUsuario.Prof)
                            list2.add(usuario);
                        break;
                    case "Gerente":
                        if (usuario.getTipo() == Usuario.TipoUsuario.Admin)
                            list2.add(usuario);
                        break;
                }
            }
            for (Usuario usuario : list2) {
                if (usuario.getNome().equalsIgnoreCase(user) && usuario.getSenha().equals(pass))
                    LoginController.curUser = usuario;
            }
            if (LoginController.curUser == null) {
                loginError.setText("Usuário e/ou senha incorretos.");
                return;
            }
        } catch (SQLException h) {
            System.out.println(h);
            return;
        }
        switch (tipo) {
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

package br.edu.ufersa.universidade.controller;

import br.edu.ufersa.universidade.model.entities.Aluno;
import br.edu.ufersa.universidade.model.service.AlunoService;
import br.edu.ufersa.universidade.model.service.UsuarioService;
import br.edu.ufersa.universidade.utils.WindowUtils;
import br.edu.ufersa.universidade.view.GerenteAlunosView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class GerenteAdcAlunoController extends BaseGerenteController {
    @FXML private TextField campoNome;
    @FXML private TextField campoEndereco;
    @FXML private TextField campoMatricula;
    @FXML private Label labelError;

    private final AlunoService alunoService = new AlunoService();
    private final UsuarioService userService = new UsuarioService();
    static long matriculaAtual = -1;

    public void initialize() {
        if (matriculaAtual == -1) {
            campoMatricula.setEditable(true);
        } else {
            campoMatricula.setEditable(false);
            campoMatricula.setText(Long.toString(matriculaAtual));
        }
    }

    @FXML public void cancelar(ActionEvent e) {
        close();
    }

    @FXML public void salvarAluno(ActionEvent e) {
        if (!validarAluno())
            return;
        String nome = campoNome.getText();
        String endereco = campoEndereco.getText();
        long matricula = Long.parseLong(campoMatricula.getText());
        Aluno al = new Aluno(0);
        al.setNome(nome);
        al.setEndereco(endereco);
        al.setMatricula(matricula);
        if (matriculaAtual == -1) {
            al.setSenha("aluno");
            try {
                alunoService.cadastrar(al);
            } catch (SQLException ignored) {
            }
        } else {
            try {
                al.setId(alunoService.buscarPorMatricula(matriculaAtual).getId());
                userService.atualizar(al);
            } catch (SQLException ignored) {
            }
        }
        close();
    }

    private void close() {
        matriculaAtual = -1;
        WindowUtils.SwitchToWindow(GerenteAlunosView.class, labelError);
    }

    private boolean validarAluno() {
        String nome = campoNome.getText();
        String endereco = campoEndereco.getText();
        String entrMatricula = campoMatricula.getText();
        if (nome == null || nome.isEmpty())
        {
            this.labelError.setText("Nome não pode estar vazio.");
            return false;
        }
        if (endereco == null || endereco.isEmpty())
        {
            this.labelError.setText("Endereço não pode estar vazio.");
            return false;
        }
        long matricula = -1L;
        try {
            matricula = Long.parseLong(entrMatricula);
        } catch (NumberFormatException ignored) {}
        if (matricula < 0) {
            this.labelError.setText("A matrícula é inválida.");
            return false;
        }
        if (matriculaAtual != -1) {
            try {
                Aluno al = alunoService.buscarPorMatricula(matricula);
                if (al != null) {
                    this.labelError.setText("Já existe um aluno com esta matrícula.");
                    return false;
                }
            } catch (SQLException ignored) {
                this.labelError.setText("Já existe um aluno com esta matrícula.");
                return false;
            }
        }
        return true;
    }
}

package br.edu.ufersa.universidade.controller;

import br.edu.ufersa.universidade.model.entities.Professor;
import br.edu.ufersa.universidade.model.service.ProfessorService;
import br.edu.ufersa.universidade.utils.WindowUtils;
import br.edu.ufersa.universidade.view.GerenteProfessoresView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.SQLException;


public class GerenteAdcProfessorController extends BaseGerenteController {
    @FXML private TextField campoNome;
    @FXML private TextField campoEndereco;
    @FXML private TextField campoCpf;
    @FXML private Label labelError;
    private final ProfessorService profService = new ProfessorService();

    @FXML public void cancelar(ActionEvent e) {
        WindowUtils.SwitchToWindow(GerenteProfessoresView.class, e);
    }

    @FXML public void salvarProfessor(ActionEvent e) {
        if (!validateProf())
            return;
        String nome = campoNome.getText();
        String endereco = campoEndereco.getText();
        String cpf = campoCpf.getText();
        Professor prof = new Professor(-1);
        prof.setCpf(cpf);
        prof.setEndereco(endereco);
        prof.setNome(nome);
        prof.setSenha("professor");
        try {
            profService.cadastrar(prof, LoginController.curUser);
        } catch (SQLException ignored) {}
        WindowUtils.SwitchToWindow(GerenteProfessoresView.class, e);
    }

    private boolean validateProf() {
        String nome = campoNome.getText();
        String endereco = campoEndereco.getText();
        String cpf = campoCpf.getText();
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
        if (cpf == null || cpf.isEmpty())
        {
            this.labelError.setText("CPF não pode estar vazio.");
            return false;
        }
        if (cpf.length() != 11) {
            this.labelError.setText("CPF deve ser 11 caracteres.");
            return false;
        }
        Professor prof = null;
        try {
            prof = profService.buscarPorCpf(cpf);
            if (prof != null && prof.getCpf().equals(cpf)) {
                this.labelError.setText("Já existe um professor com este CPF!");
                return false;
            }
        } catch (RuntimeException ignored) {}
        return true;
    }
}

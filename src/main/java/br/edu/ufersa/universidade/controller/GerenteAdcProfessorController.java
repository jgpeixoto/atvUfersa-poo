package br.edu.ufersa.universidade.controller;

import br.edu.ufersa.universidade.exceptions.ServiceException;
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
    static String cpfAtual = "";

    public void initialize() {
        if (cpfAtual.isEmpty()) {
            campoCpf.setEditable(true);
        } else {
            try {
                Professor profReal = profService.buscarPorCpf(cpfAtual);
                campoEndereco.setText(profReal.getEndereco());
                campoNome.setText(profReal.getNome());
            } catch (ServiceException ignored) {}
            campoCpf.setText(cpfAtual);
            campoCpf.setEditable(false);
        }
    }

    @FXML public void cancelar(ActionEvent e) {
        close();
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
        try {
            if (cpfAtual.isEmpty()) {
                prof.setSenha("professor");
                profService.cadastrar(prof, LoginController.curUser);
            }
            else {
                Professor profReal = profService.buscarPorCpf(cpf);
                prof.setId(profReal.getId());
                profService.editar(prof, LoginController.curUser);
            }
        } catch (Exception ex) {
            labelError.setText("Ocorreu um erro desconhecido.");
            System.out.println(ex);
            return;
        }
        close();
    }

    private void close() {
        cpfAtual = "";
        WindowUtils.SwitchToWindow(GerenteProfessoresView.class, labelError);
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
        } catch (ServiceException ignored) {}
        return true;
    }
}

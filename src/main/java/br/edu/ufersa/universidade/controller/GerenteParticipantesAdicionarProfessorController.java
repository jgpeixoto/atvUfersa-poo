package br.edu.ufersa.universidade.controller;

import br.edu.ufersa.universidade.model.entities.Professor;
import br.edu.ufersa.universidade.model.service.ProfessorService;
import br.edu.ufersa.universidade.utils.WindowUtils;
import br.edu.ufersa.universidade.view.GerentePartTurmaView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class GerenteParticipantesAdicionarProfessorController extends BaseGerenteController {
    @FXML private Button btnSair;
    @FXML private TextField campoCpf;
    @FXML private Label labelError;
    @FXML private Button btnCancelar;
    @FXML private Button btnSalvar;
    private final ProfessorService professorService = new ProfessorService();

    @FXML public void cancelar(ActionEvent e) {
        WindowUtils.SwitchToWindow(GerentePartTurmaView.class, e);
    }
    @FXML public void salvarProfessorNaTurma(ActionEvent e) {
        if (!this.validateCpf())
            return;
        WindowUtils.SwitchToWindow(GerentePartTurmaView.class, e);
    }
    private boolean validateCpf() {
        this.labelError.setText("");
        String cpf = campoCpf.getText();
        if (cpf == null || cpf.isEmpty()) {
            this.labelError.setText("O CPF deve ser preenchido.");
            return false;
        }
        if (cpf.length() != 11) {
            this.labelError.setText("O CPF deve ter 11 caracteres.");
            return false;
        }
        if (GerentePartTurmaController.curProfCpf != null && GerentePartTurmaController.curProfCpf.equals(cpf)) {
            this.labelError.setText("Esse já é o professor desta turma!");
            return false;
        }
        try {
            Professor prof = professorService.buscarPorCpf(cpf);
            if (prof == null) {
                this.labelError.setText("Nenhum professor encontrado com este CPF.");
                return false;
            }
        } catch (RuntimeException ignored) {
            this.labelError.setText("Nenhum professor encontrado com este CPF.");
            return false;
        }
        GerentePartTurmaController.curProfCpf = cpf;
        return true;
    }
}

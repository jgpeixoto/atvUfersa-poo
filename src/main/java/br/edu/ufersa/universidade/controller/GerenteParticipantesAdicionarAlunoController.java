package br.edu.ufersa.universidade.controller;

import br.edu.ufersa.universidade.model.entities.Aluno;
import br.edu.ufersa.universidade.model.service.AlunoService;
import br.edu.ufersa.universidade.utils.WindowUtils;
import br.edu.ufersa.universidade.view.GerentePartTurmaView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class GerenteParticipantesAdicionarAlunoController extends BaseGerenteController {
    @FXML private TextField campoMatricula;
    @FXML private Label labelError;
    @FXML private Button btnCancelar;
    @FXML private Button btnSalvar;
    private final AlunoService alunoService = new AlunoService();

    @FXML public void cancelar(ActionEvent e) {
        WindowUtils.SwitchToWindow(GerentePartTurmaView.class, e);
    }
    @FXML public void salvarAlunoNaTurma(ActionEvent e) {
        if (!validateMatricula())
            return;
        WindowUtils.SwitchToWindow(GerentePartTurmaView.class, e);
    }

    private boolean validateMatricula() {
        this.labelError.setText("");
        String entrada = campoMatricula.getText();
        long matricula = -1L;
        if (entrada == null || entrada.isEmpty()) {
            this.labelError.setText("A matricula deve ser preenchida.");
            return false;
        }
        try {
            matricula = Long.getLong(entrada);
        } catch (NumberFormatException ignored) {}
        if (matricula < 0) {
            this.labelError.setText("A matrícula é inválida.");
            return false;
        }
        if (GerentePartTurmaController.curAlunoMatriculas.contains(matricula)) {
            this.labelError.setText("O aluno já está matrículado na turma!");
            return false;
        }
        try {
            Aluno al = alunoService.buscarPorMatricula(matricula);
            if (al == null) {
                this.labelError.setText("Nenhum aluno foi encontrado com esta matrícula.");
                return false;
            }
        } catch (SQLException ignored) {
            this.labelError.setText("Nenhum aluno foi encontrado com esta matrícula.");
            return false;
        }
        GerentePartTurmaController.curAlunoMatriculas.add(matricula);
        return true;
    }

}

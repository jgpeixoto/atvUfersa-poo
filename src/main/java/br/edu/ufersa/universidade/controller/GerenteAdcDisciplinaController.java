package br.edu.ufersa.universidade.controller;

import br.edu.ufersa.universidade.model.entities.Disciplina;
import br.edu.ufersa.universidade.model.entities.Professor;
import br.edu.ufersa.universidade.model.service.DisciplinaService;
import br.edu.ufersa.universidade.utils.WindowUtils;
import br.edu.ufersa.universidade.view.GerenteDisciplinaView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class GerenteAdcDisciplinaController extends BaseGerenteController{
    @FXML private TextField campoNome;
    @FXML private TextField campoCodigo;
    @FXML private Label labelError;

    private final DisciplinaService disciplinaService = new DisciplinaService();
    static String codigoAtual = "";

    public void initialize() {
        if (codigoAtual.isEmpty()) {
            campoCodigo.setEditable(true);
        }
        else {
            try {
                Disciplina dis = disciplinaService.buscarPorCodigo(codigoAtual);
                campoNome.setText(dis.getNome());
            } catch (SQLException ignored) {}
            campoCodigo.setText(codigoAtual);
            campoCodigo.setEditable(false);
        }
    }

    @FXML public void cancelar(ActionEvent e) {
        close();
    }

    @FXML public void salvarDisciplina(ActionEvent e) {
        if (!validarDisciplina())
            return;
        String nome = campoNome.getText();
        String codigo = campoCodigo.getText();
        Disciplina disciplina = new Disciplina(-1);
        disciplina.setCodigo(codigo);
        disciplina.setNome(nome);
        if (codigoAtual.isEmpty()) {
            try {
                disciplinaService.cadastrar(disciplina);
            } catch (SQLException ignored) {}
        } else {
            try {
                Disciplina disReal = disciplinaService.buscarPorCodigo(codigoAtual);
                disciplina.setId(disReal.getId());
                disciplinaService.atualizar(disciplina);
            } catch (SQLException ignored) {}
        }
        close();
    }

    private void close() {
        codigoAtual = "";
        WindowUtils.SwitchToWindow(GerenteDisciplinaView.class, labelError);
    }

    private boolean validarDisciplina() {
        String nome = campoNome.getText();
        String codigo = campoCodigo.getText();
        if (nome == null || nome.isEmpty())
        {
            this.labelError.setText("Nome não pode estar vazio.");
            return false;
        }
        if (codigo == null || codigo.isEmpty())
        {
            this.labelError.setText("Código não pode estar vazio.");
            return false;
        }
        if (codigoAtual.isEmpty()) {
            try {
                Disciplina dis = disciplinaService.buscarPorCodigo(codigo);
                if (dis != null && dis.getCodigo().equals(codigo)) {
                    this.labelError.setText("Já existe uma disciplina com esse código!");
                    return false;
                }
            } catch (SQLException ignored) {
            }
        }
        return true;
    }
}

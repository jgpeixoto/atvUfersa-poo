package br.edu.ufersa.universidade.controller;

import br.edu.ufersa.universidade.model.dao.IndiceDAO;
import br.edu.ufersa.universidade.model.entities.Indice;
import br.edu.ufersa.universidade.utils.TableViewUtils;
import br.edu.ufersa.universidade.utils.WindowUtils;
import br.edu.ufersa.universidade.view.ProfessorTurmasView;
import br.edu.ufersa.universidade.view.WelcomeView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * NOTA: o fx:id "tableFrequencia" estava tipado como TableView<Aluno>, mas o
 * número de faltas é informação do Indice, não do Aluno — por isso troquei
 * o tipo para TableView<Indice> aqui no controller.
 *
 * NOTA 2: o banco só guarda o total de faltas por aluno (Indice.faltas), não
 * uma chamada por data. Por isso a coluna "Faltas" mostra esse total — não
 * tem como mostrar presença dia a dia sem criar uma tabela nova no banco.
 */
public class ProfessorFrequenciaController {
    @FXML private Button btnSair;
    @FXML private TextField buscaAluno;
    @FXML private TableView<Indice> tableFrequencia;

    static int idTurma;

    private final IndiceDAO indiceDAO = new IndiceDAO();
    private ArrayList<Indice> todos = new ArrayList<>();

    public void initialize() {
        TableViewUtils.setColumn(tableFrequencia, 0, i -> i.getAluno().getNome());
        TableViewUtils.setColumn(tableFrequencia, 1, i -> i.getAluno().getMatricula());
        TableViewUtils.setColumn(tableFrequencia, 2, Indice::getFaltas);

        try {
            todos = idTurma > 0 ? indiceDAO.buscarPorTurma(idTurma) : new ArrayList<>();
            aplicarFiltro();
            buscaAluno.textProperty().addListener((obs, oldV, newV) -> aplicarFiltro());
        } catch (SQLException ignored) {}
    }

    private void aplicarFiltro() {
        ObservableList<Indice> dados = FXCollections.observableArrayList(todos);
        FilteredList<Indice> filtrado = new FilteredList<>(dados, x -> true);

        String termo = buscaAluno.getText() == null ? "" : buscaAluno.getText().toLowerCase();
        filtrado.setPredicate(i -> termo.isBlank()
                || i.getAluno().getNome().toLowerCase().contains(termo)
                || String.valueOf(i.getAluno().getMatricula()).contains(termo));

        tableFrequencia.setItems(filtrado);
    }

    @FXML public void sair(ActionEvent e) {
        WindowUtils.SwitchToWindow(WelcomeView.class, e);
    }

    @FXML public void updateAlunos(ActionEvent e) {
        aplicarFiltro();
    }

    @FXML public void handleAbrirTurmas() {
        idTurma = -1;
        WindowUtils.SwitchToWindow(ProfessorTurmasView.class, buscaAluno);
    }
}
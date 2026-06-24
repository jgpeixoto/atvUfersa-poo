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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * NOTA: o fx:id "tableNotas" estava tipado como TableView<Aluno>, mas as
 * notas e o status são informação do Indice, não do Aluno — por isso troquei
 * o tipo para TableView<Indice> aqui no controller.
 */
public class ProfessorNotasController {
    @FXML private TextField campoBusca;
    @FXML private TableView<Indice> tableNotas;

    static int idTurma;

    private final IndiceDAO indiceDAO = new IndiceDAO();
    private ArrayList<Indice> todos = new ArrayList<>();

    public void initialize() {
        TableViewUtils.setColumn(tableNotas, 0, i -> i.getAluno().getNome());
        TableViewUtils.setColumn(tableNotas, 1, Indice::getNota1);
        TableViewUtils.setColumn(tableNotas, 2, Indice::getNota2);
        TableViewUtils.setColumn(tableNotas, 3, Indice::getNota3);
        TableViewUtils.setColumn(tableNotas, 4, Indice::obterMedia);
        TableViewUtils.setColumn(tableNotas, 5, i -> i.getEstado().toString());

        try {
            todos = idTurma > 0 ? indiceDAO.buscarPorTurma(idTurma) : new ArrayList<>();
            aplicarFiltro();
            campoBusca.textProperty().addListener((obs, oldV, newV) -> aplicarFiltro());
        } catch (SQLException ignored) {}
    }

    private void aplicarFiltro() {
        ObservableList<Indice> dados = FXCollections.observableArrayList(todos);
        FilteredList<Indice> filtrado = new FilteredList<>(dados, x -> true);

        String termo = campoBusca.getText() == null ? "" : campoBusca.getText().toLowerCase();
        filtrado.setPredicate(i -> termo.isBlank()
                || i.getAluno().getNome().toLowerCase().contains(termo)
                || String.valueOf(i.getAluno().getMatricula()).contains(termo));

        tableNotas.setItems(filtrado);
    }

    @FXML public void sair(ActionEvent e) {
        WindowUtils.SwitchToWindow(WelcomeView.class, e);
    }

    @FXML public void updateAlunos(ActionEvent e) {
        aplicarFiltro();
    }

    @FXML public void handleAbrirTurmas() {
        idTurma = -1;
        WindowUtils.SwitchToWindow(ProfessorTurmasView.class, campoBusca);
    }
}
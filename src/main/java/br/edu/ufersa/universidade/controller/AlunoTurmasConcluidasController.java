package br.edu.ufersa.universidade.controller;

import br.edu.ufersa.universidade.model.entities.Aluno;
import br.edu.ufersa.universidade.model.entities.Indice;
import br.edu.ufersa.universidade.model.service.AlunoService;
import br.edu.ufersa.universidade.model.service.IndiceService;
import br.edu.ufersa.universidade.utils.TableViewUtils;
import br.edu.ufersa.universidade.utils.WindowUtils;
import br.edu.ufersa.universidade.view.AlunoTurmasAtuaisView;
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
 * NOTA: o fx:id "tableTurmasConcluidas" estava tipado como TableView<Turma>,
 * mas o Status (Aprovado/Reprovado) é informação do Indice, não da Turma —
 * por isso troquei o tipo para TableView<Indice> aqui no controller.
 */
public class AlunoTurmasConcluidasController {
    @FXML private TextField campoBusca;
    @FXML private TableView<Indice> tableTurmasConcluidas;

    private final AlunoService alunoService = new AlunoService();
    private final IndiceService indiceService = new IndiceService();
    private ArrayList<Indice> concluidos = new ArrayList<>();

    public void initialize() {
        TableViewUtils.setColumn(tableTurmasConcluidas, 0, i -> i.getTurma().getDisciplina().getNome());
        TableViewUtils.setColumn(tableTurmasConcluidas, 1, i -> i.getTurma().getHorario());
        TableViewUtils.setColumn(tableTurmasConcluidas, 2, i -> i.getTurma().getLocal());
        TableViewUtils.setColumn(tableTurmasConcluidas, 3,
                i -> i.getEstado() == Indice.EstadoMatricula.Apr ? "Aprovado" : "Reprovado");

        try {
            Aluno aluno = alunoService.buscarPorId(LoginController.curUser.getId());
            ArrayList<Indice> todos = indiceService.listarPorAluno(aluno.getMatricula());

            concluidos = new ArrayList<>();
            for (Indice i : todos) {
                if (i.getEstado() == Indice.EstadoMatricula.Apr
                        || i.getEstado() == Indice.EstadoMatricula.Rep) {
                    concluidos.add(i);
                }
            }

            aplicarFiltro();
            campoBusca.textProperty().addListener((obs, oldV, newV) -> aplicarFiltro());
        } catch (SQLException ignored) {}
    }

    private void aplicarFiltro() {
        ObservableList<Indice> dados = FXCollections.observableArrayList(concluidos);
        FilteredList<Indice> filtrado = new FilteredList<>(dados, x -> true);

        String termo = campoBusca.getText() == null ? "" : campoBusca.getText().toLowerCase();
        filtrado.setPredicate(i -> termo.isBlank()
                || i.getTurma().getDisciplina().getNome().toLowerCase().contains(termo)
                || i.getTurma().getHorario().toLowerCase().contains(termo));

        tableTurmasConcluidas.setItems(filtrado);
    }

    @FXML public void handleAbrirTurmasAtuais() {
        WindowUtils.SwitchToWindow(AlunoTurmasAtuaisView.class, campoBusca);
    }

    @FXML public void handleSair(ActionEvent e) {
        WindowUtils.SwitchToWindow(WelcomeView.class, e);
    }
}
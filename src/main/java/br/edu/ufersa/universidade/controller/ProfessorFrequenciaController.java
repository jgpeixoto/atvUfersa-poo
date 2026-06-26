package br.edu.ufersa.universidade.controller;

import br.edu.ufersa.universidade.model.dao.IndiceDAO;
import br.edu.ufersa.universidade.model.dao.TurmaDAO;
import br.edu.ufersa.universidade.model.entities.Aluno;
import br.edu.ufersa.universidade.model.entities.Indice;
import br.edu.ufersa.universidade.model.entities.Turma;
import br.edu.ufersa.universidade.model.service.AlunoService;
import br.edu.ufersa.universidade.model.service.IndiceService;
import br.edu.ufersa.universidade.model.service.TurmaService;
import br.edu.ufersa.universidade.utils.TableViewUtils;
import br.edu.ufersa.universidade.utils.WindowUtils;
import br.edu.ufersa.universidade.view.ProfessorTurmasView;
import br.edu.ufersa.universidade.view.WelcomeView;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * NOTA: o fx:id "tableFrequencia" estava tipado como TableView<Aluno>, mas o
 * número de faltas é informação do Indice, não do Aluno — por isso troquei
 * o tipo para TableView<Indice> aqui no controller.
 *
 * NOVO: campo "Aulas ministradas" — Indice.obterFrequencia() SEMPRE retorna
 * 0% quando Turma.aulasMinistradas == 0 (é assim de propósito, pra evitar
 * divisão por zero). Como não existia nenhuma tela pra atualizar esse
 * número, a % de frequência do aluno nunca aparecia certa. Esse campo
 * resolve isso, salvando direto via TurmaDAO (o TurmaService.editar exige
 * permissão de Gerente, e essa tela é do Professor).
 */
public class ProfessorFrequenciaController {
    @FXML private Button btnSair;
    @FXML private TextField buscaAluno;
    @FXML private TextField campoAulasMinistradas;
    @FXML private Label labelAvisoAulas;
    @FXML private TableView<Indice> tableFrequencia;

    static int idTurma;

    private final IndiceService indiceService = new IndiceService();
    private final AlunoService alunoService = new AlunoService();
    private final IndiceDAO indiceDAO = new IndiceDAO();
    private final TurmaDAO turmaDAO = new TurmaDAO();
    private final TurmaService turmaService = new TurmaService();
    private ArrayList<Indice> todos = new ArrayList<>();

    public void initialize() {
        tableFrequencia.setEditable(true);

        TableViewUtils.setColumn(tableFrequencia, 0, i -> i.getAluno().getNome());
        TableViewUtils.setColumn(tableFrequencia, 1, i -> i.getAluno().getMatricula());
        colunaFaltasEditavel(2);

        Turma turma = idTurma > 0 ? turmaService.buscarPorId(idTurma) : null;
        campoAulasMinistradas.setText(turma == null ? "0" : String.valueOf(turma.getAulasMinistradas()));

        try {
            todos = idTurma > 0 ? indiceService.buscarPorTurma(idTurma) : new ArrayList<>();
            for (Indice i : todos) {
                Aluno al = alunoService.buscarPorMatricula(i.getAluno().getMatricula());
                i.setAluno(al);
            }
            aplicarFiltro();
            buscaAluno.textProperty().addListener((obs, oldV, newV) -> aplicarFiltro());
        } catch (SQLException ignored) {}
    }

    @FXML public void salvarAulasMinistradas(ActionEvent e) {
        if (idTurma <= 0) return;
        int novoValor;
        try {
            novoValor = Integer.parseInt(campoAulasMinistradas.getText().trim());
        } catch (NumberFormatException ex) {
            labelAvisoAulas.setText("Digite um número válido.");
            return;
        }
        if (novoValor < 0) {
            labelAvisoAulas.setText("Não pode ser negativo.");
            return;
        }

        Turma turma = turmaService.buscarPorId(idTurma);
        if (turma == null) return;
        turma.setAulasMinistradas(novoValor);
        turmaDAO.atualizar(turma);

        labelAvisoAulas.setText("Salvo!");
        tableFrequencia.refresh();
    }

    @SuppressWarnings("unchecked")
    private void colunaFaltasEditavel(int indice) {
        TableColumn<Indice, Integer> coluna = (TableColumn<Indice, Integer>) tableFrequencia.getColumns().get(indice);
        coluna.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getFaltas()));
        coluna.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Integer>() {
            @Override public String toString(Integer valor) { return valor == null ? "0" : String.valueOf(valor); }
            @Override public Integer fromString(String texto) {
                try { return Integer.parseInt(texto.trim()); } catch (NumberFormatException e) { return 0; }
            }
        }));
        coluna.setOnEditCommit(event -> {
            Indice ind = event.getRowValue();
            ind.setFaltas(event.getNewValue());
            try {
                indiceDAO.atualizar(ind);
            } catch (SQLException ignored) {}
            tableFrequencia.refresh();
        });
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
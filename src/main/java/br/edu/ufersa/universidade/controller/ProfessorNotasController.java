package br.edu.ufersa.universidade.controller;

import br.edu.ufersa.universidade.model.entities.Aluno;
import br.edu.ufersa.universidade.model.entities.Indice;
import br.edu.ufersa.universidade.model.service.AlunoService;
import br.edu.ufersa.universidade.model.service.IndiceService;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * NOTA: o fx:id "tableNotas" estava tipado como TableView<Aluno>, mas as
 * notas e o status são informação do Indice, não do Aluno — por isso troquei
 * o tipo para TableView<Indice> aqui no controller.
 *
 * As colunas P1/P2/P3 agora são EDITÁVEIS: clique duplo na célula, digita um
 * número de 0 a 100, aperta Enter — salva direto no banco via
 * IndiceService.lancarNotas(...), que também recalcula a Média e o Status
 * (Aprovado/Reprovado) automaticamente.
 */
public class ProfessorNotasController {
    @FXML private TextField campoBusca;
    @FXML private TableView<Indice> tableNotas;

    static int idTurma;

    private final IndiceService indiceService = new IndiceService();
    private final AlunoService alunoService = new AlunoService();
    private ArrayList<Indice> todos = new ArrayList<>();

    public void initialize() {
        tableNotas.setEditable(true);

        TableViewUtils.setColumn(tableNotas, 0, i -> i.getAluno().getNome());
        colunaEditavel(1, Indice::getNota1, Indice::setNota1);
        colunaEditavel(2, Indice::getNota2, Indice::setNota2);
        colunaEditavel(3, Indice::getNota3, Indice::setNota3);
        TableViewUtils.setColumn(tableNotas, 4, Indice::obterMedia);
        TableViewUtils.setColumn(tableNotas, 5, i -> i.getEstado().toString());

        try {
            todos = idTurma > 0 ? indiceService.buscarPorTurma(idTurma) : new ArrayList<>();
            for (Indice i : todos) {
                Aluno al = alunoService.buscarPorMatricula(i.getAluno().getMatricula());
                i.setAluno(al);
            }
            aplicarFiltro();
            campoBusca.textProperty().addListener((obs, oldV, newV) -> aplicarFiltro());
        } catch (SQLException ignored) {}
    }

    /** Liga uma coluna existente do FXML a um getter/setter de nota, deixando-a editável. */
    @SuppressWarnings("unchecked")
    private void colunaEditavel(int indice, Function<Indice, Integer> getter, BiConsumer<Indice, Integer> setter) {
        TableColumn<Indice, Integer> coluna = (TableColumn<Indice, Integer>) tableNotas.getColumns().get(indice);
        coluna.setCellValueFactory(cell -> new SimpleObjectProperty<>(getter.apply(cell.getValue())));
        coluna.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Integer>() {
            @Override public String toString(Integer valor) { return valor == null ? "0" : String.valueOf(valor); }
            @Override public Integer fromString(String texto) {
                try { return Integer.parseInt(texto.trim()); } catch (NumberFormatException e) { return 0; }
            }
        }));
        coluna.setOnEditCommit(event -> {
            Indice ind = event.getRowValue();
            setter.accept(ind, event.getNewValue());
            try {
                indiceService.lancarNotas(ind.getId(), ind);
            } catch (SQLException | IllegalArgumentException ignored) {}
            tableNotas.refresh();
        });
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
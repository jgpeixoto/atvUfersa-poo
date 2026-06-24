package br.edu.ufersa.universidade.utils;

import java.util.List;
import java.util.function.Function;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Utilitário genérico para popular qualquer TableView a partir de uma lista
 * de entidades (Aluno, Turma, Professor, Disciplina, Indice...), sem precisar
 * criar uma Factory diferente para cada tabela.
 *
 * Uso típico dentro do initialize() de um Controller:
 *
 *   List<Aluno> alunos = alunoService.listarTodos();
 *   TableViewUtils.addColumn(tableAlunos, "Nome", Aluno::getNome);
 *   TableViewUtils.addColumn(tableAlunos, "Matrícula", a -> a.getMatricula());
 *   TableViewUtils.popular(tableAlunos, alunos);
 *
 * Quando as colunas JÁ existem no FXML (sem fx:id, só com texto/largura
 * definidos visualmente no Scene Builder), use setColumn(...) em vez de
 * addColumn(...) — assim você só liga a coluna existente a um valor, sem
 * criar uma coluna nova (e duplicar as do FXML).
 */
public final class TableViewUtils {

    private TableViewUtils() { }

    /**
     * Adiciona uma coluna NOVA na tabela, ligada a um "extrator" (geralmente
     * uma referência de método, tipo Aluno::getNome).
     */
    public static <T> TableColumn<T, Object> addColumn(TableView<T> table, String titulo,
                                                       Function<T, Object> extractor) {
        TableColumn<T, Object> coluna = new TableColumn<>(titulo);
        coluna.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(extractor.apply(cellData.getValue())));
        table.getColumns().add(coluna);
        return coluna;
    }

    /**
     * Liga uma coluna QUE JÁ EXISTE no FXML (identificada pela posição, da
     * esquerda pra direita, começando em 0) a um extrator de valor.
     * Use isso quando o FXML já declarou os <TableColumn> mas sem fx:id.
     */
    @SuppressWarnings("unchecked")
    public static <T> void setColumn(TableView<T> table, int indice, Function<T, Object> extractor) {
        TableColumn<T, Object> coluna = (TableColumn<T, Object>) table.getColumns().get(indice);
        coluna.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(extractor.apply(cellData.getValue())));
    }

    /**
     * Joga a lista de itens (vinda do Service/DAO) dentro da TableView.
     * Chame depois de já ter ligado as colunas.
     */
    public static <T> void popular(TableView<T> table, List<T> itens) {
        table.setItems(FXCollections.observableArrayList(itens));
    }
}
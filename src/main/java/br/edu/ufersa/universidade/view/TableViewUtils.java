package br.edu.ufersa.universidade.view;

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
 */
public final class TableViewUtils {

    private TableViewUtils() { }

    /**
     * Adiciona uma coluna na tabela, ligada a um "extrator" (geralmente uma
     * referência de método, tipo Aluno::getNome).
     *
     * @param table     a TableView que vai receber a coluna
     * @param titulo    texto do cabeçalho da coluna
     * @param extractor função que recebe o objeto da linha e devolve o valor
     *                  a ser mostrado nessa coluna
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
     * Joga a lista de itens (vinda do Service/DAO) dentro da TableView.
     * Chame depois de já ter cadastrado as colunas com addColumn(...).
     */
    public static <T> void popular(TableView<T> table, List<T> itens) {
        table.setItems(FXCollections.observableArrayList(itens));
    }
}
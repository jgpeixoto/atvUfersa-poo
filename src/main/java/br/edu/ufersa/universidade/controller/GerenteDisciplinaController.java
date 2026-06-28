package br.edu.ufersa.universidade.controller;

import br.edu.ufersa.universidade.model.entities.Disciplina;
import br.edu.ufersa.universidade.model.service.DisciplinaService;
import br.edu.ufersa.universidade.utils.TableViewUtils;
import br.edu.ufersa.universidade.utils.WindowUtils;
import br.edu.ufersa.universidade.view.ConfirmarExclusaoView;
import br.edu.ufersa.universidade.view.GerenteAdcDisciplinaView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.ArrayList;

public class GerenteDisciplinaController extends BaseGerenteController {
    @FXML private TextField campoBusca;
    @FXML private Button btnAdicionar;
    @FXML private TableView<Disciplina> tabelaDisciplinas;

    private final DisciplinaService disciplinaService = new DisciplinaService();
    private ArrayList<Disciplina> todos = new ArrayList<>();

    public void initialize() {
        TableViewUtils.setColumn(tabelaDisciplinas, 0, Disciplina::getNome);
        TableViewUtils.setColumn(tabelaDisciplinas, 1, Disciplina::getCodigo);

        botaoNaColuna(2, "Editar", dis -> {
            GerenteAdcDisciplinaController.codigoAtual = dis.getCodigo();
            WindowUtils.SwitchToWindow(GerenteAdcDisciplinaView.class, btnAdicionar);
        });

        botaoNaColuna(3, "Excluir", dis ->
                abrirConfirmacao(dis.getCodigo(), () -> {
                    try {
                        disciplinaService.deletarPorId(dis.getId());
                    } catch (SQLException ignored) {}
                }));

        carregar();
        campoBusca.textProperty().addListener((obs, oldV, newV) -> aplicarFiltro());
    }

    private void carregar() {
        try {
            todos = disciplinaService.buscarTodos();
        } catch (SQLException ignored) {
            todos = new ArrayList<>();
        }
        aplicarFiltro();
    }

    private void aplicarFiltro() {
        ObservableList<Disciplina> dados = FXCollections.observableArrayList(todos);
        FilteredList<Disciplina> filtrado = new FilteredList<>(dados, x -> true);

        String termo = campoBusca.getText() == null ? "" : campoBusca.getText().toLowerCase();
        filtrado.setPredicate(d -> termo.isBlank()
                || d.getNome().toLowerCase().contains(termo)
                || d.getCodigo().toLowerCase().contains(termo));

        tabelaDisciplinas.setItems(filtrado);
    }

    private void botaoNaColuna(int indice, String texto, java.util.function.Consumer<Disciplina> acao) {
        TableColumn<Disciplina, Void> coluna = (TableColumn<Disciplina, Void>) tabelaDisciplinas.getColumns().get(indice);
        coluna.setCellFactory(col -> new TableCell<>() {
            private final Button botao = new Button(texto);
            {
                if (texto.equalsIgnoreCase("Editar")) {
                    botao.getStyleClass().add("btn-editar");
                } else if (texto.equalsIgnoreCase("Excluir")) {
                    botao.getStyleClass().add("btn-excluir");
                }
                botao.setOnAction(e -> acao.accept(getTableView().getItems().get(getIndex())));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : botao);
            }
        });
    }

    private void abrirConfirmacao(String idStr, Runnable acaoExcluir) {
        try {
            new ConfirmarExclusaoView(() -> {}, id -> {
                acaoExcluir.run();
                carregar();
            }, idStr).start(null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML public void adicionarDisciplina(ActionEvent e) {
        GerenteAdcDisciplinaController.codigoAtual = "";
        WindowUtils.SwitchToWindow(GerenteAdcDisciplinaView.class, e);
    }
}
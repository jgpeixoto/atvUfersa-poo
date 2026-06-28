package br.edu.ufersa.universidade.controller;

import br.edu.ufersa.universidade.model.entities.Aluno;
import br.edu.ufersa.universidade.model.service.AlunoService;
import br.edu.ufersa.universidade.utils.TableViewUtils;
import br.edu.ufersa.universidade.utils.WindowUtils;
import br.edu.ufersa.universidade.view.ConfirmarExclusaoView;
import br.edu.ufersa.universidade.view.GerenteAdcAlunoView;
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

public class GerenteAlunosController extends BaseGerenteController {
    @FXML private TextField campoBusca;
    @FXML private Button btnAdicionar;
    @FXML private TableView<Aluno> tabelaAlunos;

    private final AlunoService alunoService = new AlunoService();
    private ArrayList<Aluno> todos = new ArrayList<>();

    public void initialize() {
        TableViewUtils.setColumn(tabelaAlunos, 0, Aluno::getNome);
        TableViewUtils.setColumn(tabelaAlunos, 1, Aluno::getEndereco);
        TableViewUtils.setColumn(tabelaAlunos, 2, Aluno::getMatricula);

        botaoNaColuna(3, "Editar", aluno -> {
            GerenteAdcAlunoController.matriculaAtual = aluno.getMatricula();
            WindowUtils.SwitchToWindow(GerenteAdcAlunoView.class, btnAdicionar);
        });

        botaoNaColuna(4, "Excluir", aluno ->
                abrirConfirmacao(String.valueOf(aluno.getMatricula()), () -> {
                    try {
                        alunoService.deletar(aluno.getMatricula());
                    } catch (SQLException ignored) {}
                }));

        carregar();
        campoBusca.textProperty().addListener((obs, oldV, newV) -> aplicarFiltro());
    }

    private void carregar() {
        try {
            todos = alunoService.listarTodos();
        } catch (SQLException ignored) {
            todos = new ArrayList<>();
        }
        aplicarFiltro();
    }

    private void aplicarFiltro() {
        ObservableList<Aluno> dados = FXCollections.observableArrayList(todos);
        FilteredList<Aluno> filtrado = new FilteredList<>(dados, x -> true);

        String termo = campoBusca.getText() == null ? "" : campoBusca.getText().toLowerCase();
        filtrado.setPredicate(a -> termo.isBlank()
                || a.getNome().toLowerCase().contains(termo)
                || String.valueOf(a.getMatricula()).contains(termo));

        tabelaAlunos.setItems(filtrado);
    }

    private void botaoNaColuna(int indice, String texto, java.util.function.Consumer<Aluno> acao) {
        TableColumn<Aluno, Void> coluna = (TableColumn<Aluno, Void>) tabelaAlunos.getColumns().get(indice);
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

    @FXML public void adicionarAluno(ActionEvent e) {
        GerenteAdcAlunoController.matriculaAtual = -1;
        WindowUtils.SwitchToWindow(GerenteAdcAlunoView.class, e);
    }
}
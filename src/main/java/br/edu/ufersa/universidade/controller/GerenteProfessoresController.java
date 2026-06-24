package br.edu.ufersa.universidade.controller;

import br.edu.ufersa.universidade.exceptions.ServiceException;
import br.edu.ufersa.universidade.model.entities.Professor;
import br.edu.ufersa.universidade.model.service.ProfessorService;
import br.edu.ufersa.universidade.model.service.TurmaService;
import br.edu.ufersa.universidade.utils.TableViewUtils;
import br.edu.ufersa.universidade.utils.WindowUtils;
import br.edu.ufersa.universidade.view.ConfirmarExclusaoView;
import br.edu.ufersa.universidade.view.GerenteAdcProfessorView;
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

import java.util.ArrayList;

public class GerenteProfessoresController extends BaseGerenteController {
    @FXML private TextField campoBusca;
    @FXML private Button btnAdicionar;
    @FXML private TableView<Professor> tabelaProfessores;

    private final ProfessorService professorService = new ProfessorService();
    private final TurmaService turmaService = new TurmaService();
    private ArrayList<Professor> todos = new ArrayList<>();

    public void initialize() {
        TableViewUtils.setColumn(tabelaProfessores, 0, Professor::getNome);
        TableViewUtils.setColumn(tabelaProfessores, 1, Professor::getEndereco);
        TableViewUtils.setColumn(tabelaProfessores, 2, Professor::getCpf);
        TableViewUtils.setColumn(tabelaProfessores, 3, this::contarTurmas);

        botaoNaColuna(4, "Editar", prof -> {
            GerenteAdcProfessorController.cpfAtual = prof.getCpf();
            WindowUtils.SwitchToWindow(GerenteAdcProfessorView.class, btnAdicionar);
        });

        botaoNaColuna(5, "Excluir", prof ->
                abrirConfirmacao(prof.getCpf(), () ->
                        professorService.deletar(prof.getId(), LoginController.curUser)));

        carregar();
        campoBusca.textProperty().addListener((obs, oldV, newV) -> aplicarFiltro());
    }

    private int contarTurmas(Professor prof) {
        try {
            return turmaService.buscarPorProfessor(prof.getId(), LoginController.curUser).size();
        } catch (ServiceException ignored) {
            return 0;
        }
    }

    private void carregar() {
        todos = professorService.listarTodos();
        aplicarFiltro();
    }

    private void aplicarFiltro() {
        ObservableList<Professor> dados = FXCollections.observableArrayList(todos);
        FilteredList<Professor> filtrado = new FilteredList<>(dados, x -> true);

        String termo = campoBusca.getText() == null ? "" : campoBusca.getText().toLowerCase();
        filtrado.setPredicate(p -> termo.isBlank()
                || p.getNome().toLowerCase().contains(termo)
                || p.getCpf().toLowerCase().contains(termo));

        tabelaProfessores.setItems(filtrado);
    }

    private void botaoNaColuna(int indice, String texto, java.util.function.Consumer<Professor> acao) {
        TableColumn<Professor, Void> coluna = (TableColumn<Professor, Void>) tabelaProfessores.getColumns().get(indice);
        coluna.setCellFactory(col -> new TableCell<>() {
            private final Button botao = new Button(texto);
            {
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

    @FXML public void adicionarProfessor(ActionEvent e) {
        GerenteAdcProfessorController.cpfAtual = "";
        WindowUtils.SwitchToWindow(GerenteAdcProfessorView.class, e);
    }
}
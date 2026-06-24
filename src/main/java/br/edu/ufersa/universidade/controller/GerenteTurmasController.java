package br.edu.ufersa.universidade.controller;

import br.edu.ufersa.universidade.model.entities.Turma;
import br.edu.ufersa.universidade.model.service.TurmaService;
import br.edu.ufersa.universidade.utils.TableViewUtils;
import br.edu.ufersa.universidade.utils.WindowUtils;
import br.edu.ufersa.universidade.view.ConfirmarExclusaoView;
import br.edu.ufersa.universidade.view.GerenteAdcTurmaView;
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

public class GerenteTurmasController extends BaseGerenteController {

    @FXML private TextField campoBusca;
    @FXML private Button btnAdicionar;
    @FXML private TableView<Turma> tabelaTurmas;

    private final TurmaService turmaService = new TurmaService();
    private ArrayList<Turma> todas = new ArrayList<>();

    public void initialize() {

        TableViewUtils.setColumn(
                tabelaTurmas,
                0,
                t -> t.getDisciplina().getNome()
        );

        TableViewUtils.setColumn(
                tabelaTurmas,
                1,
                Turma::getHorario
        );

        TableViewUtils.setColumn(
                tabelaTurmas,
                2,
                t -> turmaService.listarIndices(t).size()
        );

        TableViewUtils.setColumn(
                tabelaTurmas,
                3,
                Turma::getLocal
        );

        TableViewUtils.setColumn(
                tabelaTurmas,
                4,
                t -> t.getEstado().toString()
        );

        botaoNaColuna(5, "Editar", turma -> {
            GerenteAdcTurmaController.curTurmaId = turma.getId();

            WindowUtils.SwitchToWindow(
                    GerenteAdcTurmaView.class,
                    btnAdicionar
            );
        });

        botaoNaColuna(6, "Excluir", turma ->
                abrirConfirmacao(
                        String.valueOf(turma.getId()),
                        () -> turmaService.deletar(
                                turma.getId(),
                                LoginController.curUser
                        )
                ));

        carregar();

        campoBusca.textProperty().addListener(
                (obs, oldV, newV) -> aplicarFiltro()
        );
    }

    private void carregar() {
        todas = turmaService.listarTodas();
        aplicarFiltro();
    }

    private void aplicarFiltro() {

        ObservableList<Turma> dados =
                FXCollections.observableArrayList(todas);

        FilteredList<Turma> filtrado =
                new FilteredList<>(dados, x -> true);

        String termo =
                campoBusca.getText() == null
                        ? ""
                        : campoBusca.getText().toLowerCase();

        filtrado.setPredicate(t ->
                termo.isBlank()
                        || t.getDisciplina()
                        .getNome()
                        .toLowerCase()
                        .contains(termo)
                        || t.getHorario()
                        .toLowerCase()
                        .contains(termo)
                        || t.getLocal()
                        .toLowerCase()
                        .contains(termo));

        tabelaTurmas.setItems(filtrado);
    }

    private void botaoNaColuna(
            int indice,
            String texto,
            java.util.function.Consumer<Turma> acao) {

        TableColumn<Turma, Void> coluna =
                (TableColumn<Turma, Void>)
                        tabelaTurmas.getColumns().get(indice);

        coluna.setCellFactory(col -> new TableCell<>() {

            private final Button botao = new Button(texto);

            {
                botao.setOnAction(e ->
                        acao.accept(
                                getTableView()
                                        .getItems()
                                        .get(getIndex())
                        ));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : botao);
            }
        });
    }

    private void abrirConfirmacao(
            String idStr,
            Runnable acaoExcluir) {

        try {
            new ConfirmarExclusaoView(
                    () -> {},
                    id -> {
                        acaoExcluir.run();
                        carregar();
                    },
                    idStr
            ).start(null);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void adicionarTurma(ActionEvent e) {

        GerenteAdcTurmaController.curTurmaId = -1;
        GerenteAdcTurmaController.curProfCpf = "";
        GerenteAdcTurmaController.curAlunoMatriculas.clear();

        WindowUtils.SwitchToWindow(
                GerenteAdcTurmaView.class,
                e
        );
    }
}
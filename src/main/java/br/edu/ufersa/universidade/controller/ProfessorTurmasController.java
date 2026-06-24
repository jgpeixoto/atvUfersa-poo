package br.edu.ufersa.universidade.controller;

import br.edu.ufersa.universidade.model.entities.Turma;
import br.edu.ufersa.universidade.model.service.TurmaService;
import br.edu.ufersa.universidade.utils.TableViewUtils;
import br.edu.ufersa.universidade.utils.WindowUtils;
import br.edu.ufersa.universidade.view.ProfessorFrequenciaView;
import br.edu.ufersa.universidade.view.ProfessorNotasView;
import br.edu.ufersa.universidade.view.WelcomeView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.ArrayList;
import java.util.function.Consumer;

public class ProfessorTurmasController {
    @FXML private TableView<Turma> tableTurmas;

    private final TurmaService turmaService = new TurmaService();

    public void initialize() {
        TableViewUtils.setColumn(tableTurmas, 0, t -> t.getId());
        TableViewUtils.setColumn(tableTurmas, 1, t -> t.getDisciplina().getNome());
        TableViewUtils.setColumn(tableTurmas, 2, t -> t.getDisciplina().getCodigo());

        substituirPorBotao(3, "Adicionar  +",
                turma -> { ProfessorFrequenciaController.idTurma = turma.getId(); },
                ProfessorFrequenciaView.class);

        substituirPorBotao(4, "Adicionar  +",
                turma -> { ProfessorNotasController.idTurma = turma.getId(); },
                ProfessorNotasView.class);

        ArrayList<Turma> turmas = turmaService.buscarPorProfessor(
                LoginController.curUser.getId(), LoginController.curUser);
        TableViewUtils.popular(tableTurmas, turmas);
    }

    /** Troca a coluna (já existente no FXML, na posição "indice") por um botão. */
    private <T extends javafx.application.Application> void substituirPorBotao(
            int indice, String texto, Consumer<Turma> antesDeNavegar, Class<T> destino) {
        TableColumn<Turma, Void> coluna = (TableColumn<Turma, Void>) tableTurmas.getColumns().get(indice);
        coluna.setCellFactory(col -> new TableCell<>() {
            private final Button botao = new Button(texto);
            {
                botao.getStyleClass().add("btn-link");
                botao.setOnAction(e -> {
                    Turma turma = getTableView().getItems().get(getIndex());
                    antesDeNavegar.accept(turma);
                    WindowUtils.SwitchToWindow(destino, botao);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : botao);
            }
        });
    }

    @FXML public void sair(ActionEvent e) {
        WindowUtils.SwitchToWindow(WelcomeView.class, e);
    }
}
package br.edu.ufersa.universidade.controller;

import br.edu.ufersa.universidade.model.entities.Aluno;
import br.edu.ufersa.universidade.model.entities.Indice;
import br.edu.ufersa.universidade.model.entities.Turma;
import br.edu.ufersa.universidade.model.service.AlunoService;
import br.edu.ufersa.universidade.model.service.IndiceService;
import br.edu.ufersa.universidade.model.service.TurmaService;
import br.edu.ufersa.universidade.utils.TableViewUtils;
import br.edu.ufersa.universidade.utils.WindowUtils;
import br.edu.ufersa.universidade.view.AlunoTurmasConcluidasView;
import br.edu.ufersa.universidade.view.WelcomeView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.util.ArrayList;

public class AlunoTurmasAtuaisController {
    @FXML private Label lblNomeAluno; // olá, nome aluno
    @FXML private Label lblInfoAluno; // inserir Matrícula aqui
    @FXML private TableView<Turma> tableTurmas;
    @FXML private TableView<Indice> tableNotas;
    @FXML private VBox boxFrequencia;

    private final AlunoService alunoService = new AlunoService();
    private final IndiceService indiceService = new IndiceService();
    private final TurmaService turmaService = new TurmaService();

    public void initialize() {
        try {
            Aluno al = alunoService.buscarPorId(LoginController.curUser.getId());
            lblNomeAluno.setText("Olá, " + al.getNome());
            lblInfoAluno.setText("Matrícula: " + al.getMatricula());

            ArrayList<Indice> indices = indiceService.listarPorAluno(al.getMatricula());
            for (Indice indice : indices) {
                Turma turma = turmaService.buscarPorId(indice.getTurma().getId());
                indice.setTurma(turma);
            }
            popularMinhasTurmas(indices);
            popularNotas(indices);
            popularFrequencia(indices);
        } catch (SQLException ignored) {}
    }

    /** A tabela "Minhas Turmas" mostra as Turma (não os índices), uma linha por turma. */
    private void popularMinhasTurmas(ArrayList<Indice> indices) {
        ArrayList<Turma> turmas = new ArrayList<>();
        for (Indice i : indices) {
            turmas.add(i.getTurma());
        }

        TableViewUtils.setColumn(tableTurmas, 0, t -> t.getDisciplina().getNome());
        TableViewUtils.setColumn(tableTurmas, 1, t -> t.getProfessor().getNome());
        TableViewUtils.setColumn(tableTurmas, 2, Turma::getHorario);
        TableViewUtils.setColumn(tableTurmas, 3, t -> t.getEstado().toString());

        TableViewUtils.popular(tableTurmas, turmas);
    }

    private void popularNotas(ArrayList<Indice> indices) {
        TableViewUtils.setColumn(tableNotas, 0, i -> i.getTurma().getDisciplina().getNome());
        TableViewUtils.setColumn(tableNotas, 1, Indice::getNota1);
        TableViewUtils.setColumn(tableNotas, 2, Indice::getNota2);
        TableViewUtils.setColumn(tableNotas, 3, Indice::getNota3);
        TableViewUtils.setColumn(tableNotas, 4, Indice::obterMedia);

        TableViewUtils.popular(tableNotas, indices);
    }

    /** A "Frequência" é desenhada na mão (não é TableView), uma linha por turma. */
    private void popularFrequencia(ArrayList<Indice> indices) {
        boxFrequencia.getChildren().clear();
        for (Indice indice : indices) {
            double percentual = indice.obterFrequencia();
            boxFrequencia.getChildren().add(
                    linhaFrequencia(indice.getTurma().getDisciplina().getNome(), percentual));
        }
    }

    private HBox linhaFrequencia(String disciplina, double percentual) {
        Label nome = new Label(disciplina);
        nome.setPrefWidth(90);
        nome.setStyle("-fx-font-size: 13px; -fx-text-fill: #2c2c2c;");

        ProgressBar bar = new ProgressBar(percentual);
        bar.setPrefWidth(180);
        bar.getStyleClass().add("freq-bar");
        if (percentual >= 0.80) {
            bar.getStyleClass().add("freq-bar-green");
        } else if (percentual >= 0.60) {
            bar.getStyleClass().add("freq-bar-yellow");
        } else {
            bar.getStyleClass().add("freq-bar-red");
        }

        Label percentLabel = new Label("%" + (int) Math.round(percentual));
        percentLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #2c2c2c;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox linha = new HBox(10, nome, bar, spacer, percentLabel);
        linha.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        return linha;
    }

    @FXML public void handleAbrirTurmasConcluidas() {
        WindowUtils.SwitchToWindow(AlunoTurmasConcluidasView.class, boxFrequencia);
    }

    @FXML public void handleSair(ActionEvent e) {
        WindowUtils.SwitchToWindow(WelcomeView.class, e);
    }
}
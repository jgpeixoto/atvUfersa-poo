package br.edu.ufersa.universidade.controller;

import br.edu.ufersa.universidade.model.entities.Aluno;
import br.edu.ufersa.universidade.model.entities.Professor;
import br.edu.ufersa.universidade.model.service.AlunoService;
import br.edu.ufersa.universidade.model.service.ProfessorService;
import br.edu.ufersa.universidade.utils.TableViewUtils;
import br.edu.ufersa.universidade.utils.WindowUtils;
import br.edu.ufersa.universidade.view.GerenteAdcTurmaView;
import br.edu.ufersa.universidade.view.GerenteParticipantesAdcAlunoView;
import br.edu.ufersa.universidade.view.GerenteParticipantesAdcProfessorView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Tela de "Participantes da turma", usada ao criar/editar uma turma.
 * As duas tabelas (Professor e Alunos) refletem o que já foi escolhido nas
 * telas de adicionar professor/aluno (guardado em curProfCpf e
 * curAlunoMatriculas) — não vem direto do banco filtrado por turma, porque
 * essas listas só existem em memória até o usuário clicar em "Salvar".
 */
public class GerentePartTurmaController extends BaseGerenteController {
    @FXML private TableView<Professor> tabelaProfessor;
    @FXML private TableView<Aluno> tabelaAlunos;

    static String curProfCpf = "";
    static ArrayList<Long> curAlunoMatriculas = new ArrayList<Long>();

    private final ProfessorService professorService = new ProfessorService();
    private final AlunoService alunoService = new AlunoService();

    public void initialize() {
        TableViewUtils.setColumn(tabelaProfessor, 0, Professor::getNome);
        TableViewUtils.setColumn(tabelaProfessor, 1, Professor::getCpf);
        botaoNaColuna(tabelaProfessor, 2, "Excluir", prof -> {
            curProfCpf = "";
            popularProfessor();
        });

        TableViewUtils.setColumn(tabelaAlunos, 0, Aluno::getNome);
        TableViewUtils.setColumn(tabelaAlunos, 1, Aluno::getMatricula);
        botaoNaColuna(tabelaAlunos, 2, "Excluir", aluno -> {
            curAlunoMatriculas.remove(aluno.getMatricula());
            popularAlunos();
        });

        popularProfessor();
        popularAlunos();
    }

    private void popularProfessor() {
        ArrayList<Professor> lista = new ArrayList<>();
        if (!curProfCpf.isEmpty()) {
            try {
                lista.add(professorService.buscarPorCpf(curProfCpf));
            } catch (RuntimeException ignored) {}
        }
        TableViewUtils.popular(tabelaProfessor, lista);
    }

    private void popularAlunos() {
        ArrayList<Aluno> lista = new ArrayList<>();
        for (long matricula : curAlunoMatriculas) {
            try {
                Aluno al = alunoService.buscarPorMatricula(matricula);
                if (al != null) lista.add(al);
            } catch (SQLException ignored) {}
        }
        TableViewUtils.popular(tabelaAlunos, lista);
    }

    private <T> void botaoNaColuna(TableView<T> tabela, int indice, String texto,
                                   java.util.function.Consumer<T> acao) {
        TableColumn<T, Void> coluna = (TableColumn<T, Void>) tabela.getColumns().get(indice);
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

    @FXML public void abrirAdicionarProfessor(ActionEvent e) {
        WindowUtils.SwitchToWindow(GerenteParticipantesAdcProfessorView.class, e);
    }
    @FXML public void abrirAdicionarAluno(ActionEvent e) {
        WindowUtils.SwitchToWindow(GerenteParticipantesAdcAlunoView.class, e);
    }
    @FXML public void cancelar(ActionEvent e) {
        close(e);
    }

    @FXML public void salvarParticipantes(ActionEvent e) {
        GerenteAdcTurmaController.curProfCpf = curProfCpf;
        GerenteAdcTurmaController.curAlunoMatriculas.clear();
        GerenteAdcTurmaController.curAlunoMatriculas.addAll(curAlunoMatriculas);
        close(e);
    }

    private void close(ActionEvent e) {
        curProfCpf = "";
        curAlunoMatriculas.clear();
        WindowUtils.SwitchToWindow(GerenteAdcTurmaView.class, e);
    }
}
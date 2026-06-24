package br.edu.ufersa.universidade.controller;

import br.edu.ufersa.universidade.model.entities.Disciplina;
import br.edu.ufersa.universidade.model.entities.Indice;
import br.edu.ufersa.universidade.model.entities.Professor;
import br.edu.ufersa.universidade.model.service.*;
import br.edu.ufersa.universidade.utils.WindowUtils;
import br.edu.ufersa.universidade.view.GerentePartTurmaView;
import br.edu.ufersa.universidade.view.GerenteTurmasView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import br.edu.ufersa.universidade.model.entities.Turma;

import java.sql.SQLException;
import java.util.ArrayList;

public class GerenteAdcTurmaController extends BaseGerenteController {
    @FXML private TextField campoDisciplina;
    @FXML private TextField campoHorario;
    @FXML private TextField campoLocal;
    @FXML private ComboBox<String> comboStatus;
    @FXML private Label labelError;

    private final ProfessorService profService = new ProfessorService();
    private final DisciplinaService disService = new DisciplinaService();
    private final TurmaService turmaService = new TurmaService();
    private final IndiceService indiceService = new IndiceService();

    static int curTurmaId = -1;
    static String curProfCpf = "";
    static ArrayList<Long> curAlunoMatriculas = new ArrayList<Long>();

    static String lastHorario = "";
    static String lastLocal = "";
    static String lastState = "";
    static String lastCodigo = "";

    public void initialize() {
        if (curTurmaId != -1) { // não é uma nova turma
            Turma turma = turmaService.buscarPorId(curTurmaId);
            if (curProfCpf.isEmpty()) {
                curProfCpf = turma.getProfessor().getCpf();
                campoDisciplina.setText(turma.getDisciplina().getCodigo());
                campoHorario.setText(turma.getHorario());
                campoLocal.setText(turma.getLocal());
                comboStatus.setValue("Ativa");
            }
            if (curAlunoMatriculas.isEmpty()) {
                try {
                    ArrayList<Indice> indices = turmaService.listarIndices(turma);
                    for (Indice in : indices) {
                        curAlunoMatriculas.add(in.getAluno().getMatricula());
                    }
                }
                catch (RuntimeException ignored) {}
            }
        }
        if (!lastLocal.isEmpty()) {
            campoLocal.setText(lastLocal);
        }
        if (!lastHorario.isEmpty()) {
            campoHorario.setText(lastHorario);
        }
        if (!lastCodigo.isEmpty()) {
            campoDisciplina.setText(lastCodigo);
        }
        if (!lastState.isEmpty()) {
            comboStatus.setValue(lastState);
        }
    }

    @FXML public void abrirParticipantes(ActionEvent e) {
        GerentePartTurmaController.curProfCpf = curProfCpf;
        GerentePartTurmaController.curAlunoMatriculas.clear();
        GerentePartTurmaController.curAlunoMatriculas.addAll(curAlunoMatriculas);
        String local = campoLocal.getText();
        String horario = campoHorario.getText();
        String codigo = campoDisciplina.getText();
        String state = comboStatus.getValue();
        if (local != null && !local.isEmpty()) {
            lastLocal = local;
        }
        if (horario != null && !horario.isEmpty()) {
            lastHorario = horario;
        }
        if (codigo != null && !codigo.isEmpty()) {
            lastCodigo = codigo;
        }
        if (state != null && !state.isEmpty()) {
            lastState = state;
        }
        WindowUtils.SwitchToWindow(GerentePartTurmaView.class, e);
    }

    @FXML public void cancelar(ActionEvent e) {
        close();
    }

    @FXML public void salvarTurma(ActionEvent e) {
        if (!validateTurma())
            return;
        String tipo = comboStatus.getValue();
        String codDisciplina = campoDisciplina.getText();
        String local = campoLocal.getText();
        String horario = campoHorario.getText();

        if (curTurmaId == -1) {
            Turma turma = new Turma(-1);
            turma.setEstado(tipo.equals("Ativa") ? Turma.EstadoTurma.Ativo : Turma.EstadoTurma.Fin);
            try {
                Disciplina dis = disService.buscarPorCodigo(codDisciplina);
                turma.setDisciplina(dis);
            } catch (SQLException ignored) {
            }
            turma.setLocal(local);
            turma.setHorario(horario);
            try {
                Professor prof = profService.buscarPorCpf(curProfCpf);
                turma.setProfessor(prof);
            } catch (RuntimeException ignored) {
                Professor backup = new Professor(-1);
                backup.setCpf(null);
                turma.setProfessor(backup);
            }
            turmaService.cadastrar(turma, LoginController.curUser);

            for (long num : curAlunoMatriculas) {
                try {
                    Indice indice = new Indice(-1);
                    indice.setTurma(turma);
                    indiceService.matricular(num, indice);
                } catch (SQLException ignored) {
                }
            }
        }
        else {
            Turma turma = new Turma(curTurmaId);
            turma.setEstado(tipo.equals("Ativa") ? Turma.EstadoTurma.Ativo : Turma.EstadoTurma.Fin);
            try {
                Disciplina dis = disService.buscarPorCodigo(codDisciplina);
                turma.setDisciplina(dis);
            } catch (SQLException ignored) {
            }
            turma.setLocal(local);
            turma.setHorario(horario);
            try {
                Professor prof = profService.buscarPorCpf(curProfCpf);
                turma.setProfessor(prof);
            } catch (RuntimeException ignored) {
                Professor backup = new Professor(-1);
                backup.setCpf(null);
                turma.setProfessor(backup);
            }
            turmaService.editar(turma, LoginController.curUser);

            for (long num : curAlunoMatriculas) {
                try {
                    Indice indice = new Indice(-1);
                    indice.setTurma(turma);
                    indiceService.matricular(num, indice);
                } catch (SQLException ignored) {
                }
            }
        }
        close();
    }

    private boolean validateTurma() {
        String tipo = comboStatus.getValue();
        String codDisciplina = campoDisciplina.getText();
        String local = campoLocal.getText();
        String horario = campoHorario.getText();
        if (tipo == null) {
            labelError.setText("Por favor escolha o status.");
            return false;
        }
        if (codDisciplina == null || codDisciplina.isEmpty()) {
            labelError.setText("O código da disciplina não pode estar vazio.");
            return false;
        }
        if (local == null || local.isEmpty()) {
            labelError.setText("O local não pode estar vazio.");
            return false;
        }
        if (horario == null || horario.isEmpty()) {
            labelError.setText("O horário não pode estar vazio.");
            return false;
        }
        try {
            Disciplina dis = disService.buscarPorCodigo(codDisciplina);
            if (dis == null) {
                labelError.setText("Não existe uma disciplina com este código.");
                return false;
            }
        } catch (SQLException ignored) {
            labelError.setText("Não existe uma disciplina com este código.");
            return false;
        }
        return true;
    }

    private void close() {
        curTurmaId = -1;
        curProfCpf = "";
        curAlunoMatriculas.clear();
        lastCodigo = "";
        lastState = "";
        lastHorario = "";
        lastLocal = "";
        WindowUtils.SwitchToWindow(GerenteTurmasView.class, labelError);
    }
}

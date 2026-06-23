package br.edu.ufersa.universidade.model.service;

import br.edu.ufersa.universidade.model.dao.IndiceDAO;
import br.edu.ufersa.universidade.model.dao.TurmaDAO;
import br.edu.ufersa.universidade.model.entities.*;

import java.sql.SQLException;
import java.util.ArrayList;

public class TurmaService {

    private final TurmaDAO turmaDAO = new TurmaDAO();
    private final IndiceDAO indiceDAO = new IndiceDAO();

    public void cadastrar(Turma turma, Usuario solicitante) {
        verificarPermissaoGerente(solicitante);
        validar(turma);
        if (turma.getEstado() == null)
            turma.setEstado(Turma.EstadoTurma.Ativo);
        turmaDAO.salvar(turma);
    }

    public void editar(Turma turma, Usuario solicitante) {
        verificarPermissaoGerente(solicitante);
        validar(turma);
        turmaDAO.atualizar(turma);
    }

    public void deletar(int id, Usuario solicitante) {
        verificarPermissaoGerente(solicitante);
        if (turmaDAO.buscarPorId(id) == null)
            throw new RuntimeException("Turma não encontrada para o id: " + id);
        turmaDAO.deletar(id);
    }

    public Turma buscarPorId(int id) {
        Turma t = turmaDAO.buscarPorId(id);
        if (t == null)
            throw new RuntimeException("Turma não encontrada para o id: " + id);
        return t;
    }

    public ArrayList<Turma> buscarPorProfessor(int idProfessor, Usuario solicitante) {
        if (solicitante.getTipo() == Usuario.TipoUsuario.Prof
                && solicitante.getId() != idProfessor)
            throw new RuntimeException("Acesso negado: professor só pode ver as próprias turmas.");
        if (solicitante.getTipo() == Usuario.TipoUsuario.Aluno)
            throw new RuntimeException("Acesso negado: alunos não podem buscar turmas por professor.");
        return turmaDAO.buscarPorProfessor(idProfessor);
    }

    public ArrayList<Turma> listarTodas() {
        return turmaDAO.listarTodas();
    }

    public ArrayList<Indice> listarIndices(Turma turma) {
        return turmaDAO.listarIndices(turma);
    }

    public void alocarProfessor(int idTurma, Professor professor, Usuario solicitante) {
        verificarPermissaoGerente(solicitante);
        Turma turma = buscarPorId(idTurma);
        turma.setProfessor(professor);
        turmaDAO.atualizar(turma);
    }

    public void fecharTurma(int idTurma, Usuario solicitante) {
        Turma turma = buscarPorId(idTurma);

        boolean ehGerente     = solicitante.getTipo() == Usuario.TipoUsuario.Admin;
        boolean ehProfDaTurma = solicitante.getTipo() == Usuario.TipoUsuario.Prof
                && turma.getProfessor() != null
                && turma.getProfessor().getId() == solicitante.getId();

        if (!ehGerente && !ehProfDaTurma)
            throw new RuntimeException("Acesso negado: apenas o gerente ou o professor da turma podem finalizá-la.");
        if (turma.getEstado() == Turma.EstadoTurma.Fin)
            throw new RuntimeException("Turma já está finalizada.");

        try {
            ArrayList<Indice> indices = indiceDAO.buscarPorTurma(idTurma);
            for (Indice ind : indices) {
                if (ind.getEstado() == Indice.EstadoMatricula.Matr) {
                    boolean aprovado = ind.obterMedia() >= 70 && ind.obterFrequencia() >= 75;
                    ind.setEstado(aprovado ? Indice.EstadoMatricula.Apr : Indice.EstadoMatricula.Rep);
                    indiceDAO.atualizar(ind);
                }
            }
            turma.setEstado(Turma.EstadoTurma.Fin);
            turmaDAO.atualizar(turma);
        } catch (SQLException e) {
            System.err.println("Erro ao fechar turma: " + e.getMessage());
        }
    }

    private void validar(Turma turma) {
        if (turma == null)
            throw new IllegalArgumentException("Turma não pode ser nula.");
        if (turma.getDisciplina() == null)
            throw new IllegalArgumentException("Disciplina da turma não pode ser nula.");
        if (turma.getLocal() == null || turma.getLocal().isBlank())
            throw new IllegalArgumentException("Local da turma não pode ser vazio.");
        if (turma.getHorario() == null || turma.getHorario().isBlank())
            throw new IllegalArgumentException("Horário da turma não pode ser vazio.");
    }

    private void verificarPermissaoGerente(Usuario solicitante) {
        if (solicitante == null || solicitante.getTipo() != Usuario.TipoUsuario.Admin)
            throw new RuntimeException("Acesso negado: apenas o gerente pode realizar esta operação.");
    }
}

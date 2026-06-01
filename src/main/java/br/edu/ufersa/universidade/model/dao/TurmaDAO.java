package br.edu.ufersa.universidade.model.dao;

import br.edu.ufersa.universidade.model.entities.*;

import java.sql.*;
import java.util.ArrayList;

public class TurmaDAO {

    private final Connection connection;

    public TurmaDAO(Connection connection) {
        this.connection = connection;
    }

    private Turma mapear(ResultSet rs) throws SQLException {
        Turma t = new Turma(rs.getInt("id_turma"));
        t.setLocal(rs.getString("local_de_aula"));
        t.setHorario(rs.getString("horario"));
        t.setAulasMinistradas(rs.getInt("aulas_ministradas"));

        // ativo BOOL no banco → EstadoTurma na entidade
        boolean ativo = rs.getBoolean("ativo");
        t.setEstado(ativo ? Turma.EstadoTurma.Ativo : Turma.EstadoTurma.Fin);

        Disciplina disc = new Disciplina(rs.getInt("id_disciplina"));
        disc.setNome(rs.getString("nome_disciplina"));
        disc.setCodigo(rs.getString("codigo_disciplina"));
        t.setDisciplina(disc);

        Professor prof = new Professor(rs.getInt("id_usuario_prof"));
        prof.setNome(rs.getString("nome_professor"));
        prof.setCpf(rs.getString("cpf_professor"));
        t.setProfessor(prof);

        return t;
    }

    private static final String SQL_BASE = """
            SELECT
                t.id_turma, t.aulas_ministradas, t.local_de_aula, t.horario, t.ativo,
                t.id_disciplina,
                d.nome   AS nome_disciplina,
                d.codigo AS codigo_disciplina,
                t.cpf_professor,
                p.cpf    AS cpf_professor,
                p.id_usuario AS id_usuario_prof,
                u.nome   AS nome_professor
            FROM turma t
            JOIN disciplina d ON d.id_disciplina = t.id_disciplina
            JOIN professor p  ON p.cpf = t.cpf_professor
            JOIN usuario u    ON u.id_usuario = p.id_usuario
            """;

    public void salvar(Turma turma) {
        String sql = """
                INSERT INTO turma (id_disciplina, cpf_professor, aulas_ministradas, local_de_aula, horario, ativo)
                VALUES (?, ?, ?, ?, ?, ?)
                """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, turma.getDisciplina().getId());
            ps.setString(2, turma.getProfessor().getCpf());
            ps.setInt(3, turma.getAulasMinistradas());
            ps.setString(4, turma.getLocal());
            ps.setString(5, turma.getHorario());
            ps.setBoolean(6, turma.getEstado() == Turma.EstadoTurma.Ativo);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao salvar turma: " + e.getMessage());
        }
    }

    public void atualizar(Turma turma) {
        String sql = """
                UPDATE turma
                SET id_disciplina = ?, cpf_professor = ?, aulas_ministradas = ?,
                    local_de_aula = ?, horario = ?, ativo = ?
                WHERE id_turma = ?
                """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, turma.getDisciplina().getId());
            ps.setString(2, turma.getProfessor().getCpf());
            ps.setInt(3, turma.getAulasMinistradas());
            ps.setString(4, turma.getLocal());
            ps.setString(5, turma.getHorario());
            ps.setBoolean(6, turma.getEstado() == Turma.EstadoTurma.Ativo);
            ps.setInt(7, turma.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar turma: " + e.getMessage());
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM turma WHERE id_turma = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao deletar turma: " + e.getMessage());
        }
    }

    public Turma buscarPorId(int id) {
        String sql = SQL_BASE + " WHERE t.id_turma = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapear(rs);
        } catch (SQLException e) {
            System.err.println("Erro ao buscar turma por id: " + e.getMessage());
        }
        return null;
    }

    public ArrayList<Turma> buscarPorProfessor(int idProfessor) {
        String sql = SQL_BASE + " WHERE p.id_usuario = ?";
        ArrayList<Turma> lista = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idProfessor);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("Erro ao buscar turmas por professor: " + e.getMessage());
        }
        return lista;
    }

    public ArrayList<Turma> buscarPorDisciplina(int idDisciplina) {
        String sql = SQL_BASE + " WHERE t.id_disciplina = ?";
        ArrayList<Turma> lista = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idDisciplina);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("Erro ao buscar turmas por disciplina: " + e.getMessage());
        }
        return lista;
    }

    public ArrayList<Turma> listarTodas() {
        ArrayList<Turma> lista = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SQL_BASE)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("Erro ao listar turmas: " + e.getMessage());
        }
        return lista;
    }
}

package br.edu.ufersa.universidade.model.dao;

import br.edu.ufersa.universidade.model.entities.*;
import br.edu.ufersa.universidade.utils.DatabaseUtils;

import java.sql.*;
import java.util.ArrayList;

public class TurmaDAO {
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

    private Indice mapearIndice(ResultSet rs) throws SQLException {
        Indice indice = new Indice(rs.getInt("id_indice"));
        Turma turma = new Turma(rs.getInt("id_turma"));
        indice.setTurma(turma);
        indice.setNota1(rs.getInt("nota1"));
        indice.setNota2(rs.getInt("nota2"));
        indice.setNota3(rs.getInt("nota3"));
        indice.setFaltas(rs.getInt("faltas"));
        String estadoStr = rs.getString("estado");
        indice.setEstado(Indice.EstadoMatricula.valueOf(estadoStr));

        Aluno aluno = new Aluno(rs.getInt("id_usuario"));
        aluno.setNome(rs.getString("nome_aluno"));
        aluno.setEndereco(rs.getString("endereco_aluno"));
        aluno.setMatricula(rs.getLong("matricula_aluno"));

        indice.setAluno(aluno);
        return indice;
    }

    private static final String SQL_INDICES = """
            SELECT
                i.id_indice, i.nota1, i.nota2, i.nota3,
                i.faltas, i.estado,
                i.matricula_aluno,
                u.nome AS nome_aluno,
                u.endereco AS endereco_aluno,
                u.id_usuario AS id_usuario,
                i.id_turma AS id_turma
            FROM indice i
            JOIN turma t ON i.id_turma = t.id_turma
            JOIN aluno a ON i.matricula_aluno = a.matricula
            JOIN usuario u ON a.id_usuario = u.id_usuario
            """;

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
        try (PreparedStatement ps = DatabaseUtils.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, turma.getDisciplina().getId());
            ps.setString(2, turma.getProfessor().getCpf());
            ps.setInt(3, turma.getAulasMinistradas());
            ps.setString(4, turma.getLocal());
            ps.setString(5, turma.getHorario());
            ps.setBoolean(6, turma.getEstado() == Turma.EstadoTurma.Ativo);
            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                int idGerado = keys.getInt(1);
                turma.setId(idGerado);
            }
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
        try (PreparedStatement ps = DatabaseUtils.getConnection().prepareStatement(sql)) {
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
        try (PreparedStatement ps = DatabaseUtils.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao deletar turma: " + e.getMessage());
        }
    }

    public Turma buscarPorId(int id) {
        String sql = SQL_BASE + " WHERE t.id_turma = ?";
        try (PreparedStatement ps = DatabaseUtils.getConnection().prepareStatement(sql)) {
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
        try (PreparedStatement ps = DatabaseUtils.getConnection().prepareStatement(sql)) {
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
        try (PreparedStatement ps = DatabaseUtils.getConnection().prepareStatement(sql)) {
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
        try (PreparedStatement ps = DatabaseUtils.getConnection().prepareStatement(SQL_BASE)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("Erro ao listar turmas: " + e.getMessage());
        }
        return lista;
    }

    public ArrayList<Indice> listarIndices(Turma turma) {
        ArrayList<Indice> lista = new ArrayList<>();
        try (PreparedStatement ps = DatabaseUtils.getConnection().prepareStatement(SQL_INDICES)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapearIndice(rs));
        } catch (SQLException e) {
            System.err.println("Erro ao listar alunos da turma: " + e.getMessage());
        }

        return lista;
    }
}

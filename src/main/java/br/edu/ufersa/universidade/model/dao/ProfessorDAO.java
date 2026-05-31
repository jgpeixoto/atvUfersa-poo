package br.edu.ufersa.universidade.model.dao;

import br.edu.ufersa.universidade.model.entities.Professor;

import java.sql.*;
import java.util.ArrayList;

public class ProfessorDAO {

    private Connection connection;

    public ProfessorDAO(Connection connection) {
        this.connection = connection;
    }

    private Professor mapear(ResultSet rs) throws SQLException {
        Professor p = new Professor(rs.getInt("id_usuario"));
        p.setNome(rs.getString("nome"));
        p.setSenha(rs.getString("senha"));
        p.setEndereco(rs.getString("endereco"));
        p.setCpf(rs.getString("cpf"));
        return p;
    }

    public void salvar(Professor professor) {
        String sqlUsuario = "INSERT INTO usuario (nome, senha, endereco, tipo) VALUES (?, ?, ?, 'Prof')";
        String sqlProf    = "INSERT INTO professor (cpf, id_usuario) VALUES (?, ?)";
        try {
            connection.setAutoCommit(false);
            try (PreparedStatement ps1 = connection.prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement ps2 = connection.prepareStatement(sqlProf)) {

                ps1.setString(1, professor.getNome());
                ps1.setString(2, professor.getSenha());
                ps1.setString(3, professor.getEndereco());
                ps1.executeUpdate();

                ResultSet keys = ps1.getGeneratedKeys();
                if (!keys.next()) throw new SQLException("Falha ao obter id gerado para usuario.");
                int idGerado = keys.getInt(1);

                ps2.setString(1, professor.getCpf());
                ps2.setInt(2, idGerado);
                ps2.executeUpdate();

                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao salvar professor: " + e.getMessage());
        }
    }

    public void atualizar(Professor professor) {
        // CPF é chave primária e não deve ser alterado
        String sql = "UPDATE usuario SET nome = ?, senha = ?, endereco = ? WHERE id_usuario = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, professor.getNome());
            ps.setString(2, professor.getSenha());
            ps.setString(3, professor.getEndereco());
            ps.setInt(4, professor.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar professor: " + e.getMessage());
        }
    }

    public void deletar(int id) {
        // ON DELETE CASCADE no banco remove a linha de professor automaticamente
        String sql = "DELETE FROM usuario WHERE id_usuario = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao deletar professor: " + e.getMessage());
        }
    }

    public Professor buscarPorId(int id) {
        String sql = """
                SELECT u.id_usuario, u.nome, u.senha, u.endereco, p.cpf
                FROM usuario u
                JOIN professor p ON u.id_usuario = p.id_usuario
                WHERE u.id_usuario = ?
                """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapear(rs);
        } catch (SQLException e) {
            System.err.println("Erro ao buscar professor por id: " + e.getMessage());
        }
        return null;
    }

    public ArrayList<Professor> buscarPorNome(String nome) {
        String sql = """
                SELECT u.id_usuario, u.nome, u.senha, u.endereco, p.cpf
                FROM usuario u
                JOIN professor p ON u.id_usuario = p.id_usuario
                WHERE u.nome LIKE ?
                """;
        ArrayList<Professor> lista = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + nome + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("Erro ao buscar professor por nome: " + e.getMessage());
        }
        return lista;
    }

    public ArrayList<Professor> listarTodos() {
        String sql = """
                SELECT u.id_usuario, u.nome, u.senha, u.endereco, p.cpf
                FROM usuario u
                JOIN professor p ON u.id_usuario = p.id_usuario
                """;
        ArrayList<Professor> lista = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("Erro ao listar professores: " + e.getMessage());
        }
        return lista;
    }
}

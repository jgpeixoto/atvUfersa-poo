package br.edu.ufersa.universidade.model.dao;
import br.edu.ufersa.universidade.model.entities.Disciplina;
import br.edu.ufersa.universidade.utils.DatabaseUtils;

import java.sql.*;
import java.util.ArrayList;

public class DisciplinaDAO {

    public ArrayList<Disciplina> buscarTodos() throws SQLException {
        ArrayList<Disciplina> lista = new ArrayList<>();

        String sql = "SELECT id_disciplina, nome, codigo FROM disciplina";

        PreparedStatement stmt = DatabaseUtils.getConnection().prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        // Para cada linha retornada, cria um objeto Aluno e adiciona na lista
        while (rs.next()) {
            Disciplina dis = new Disciplina(rs.getInt("id_disciplina"));
            dis.setNome(rs.getString("nome"));
            dis.setCodigo(rs.getString("codigo"));
            lista.add(dis);
        }

        rs.close();
        stmt.close();
        return lista;
    }

    public Disciplina buscarPorId(int id) throws SQLException {
        String sql = "SELECT id_disciplina, nome, codigo FROM disciplina WHERE id_disciplina = ?";

        PreparedStatement stmt = DatabaseUtils.getConnection().prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        Disciplina disciplina = null;
        if (rs.next()) {
            disciplina = new Disciplina(rs.getInt("id_disciplina"));
            disciplina.setNome(rs.getString("nome"));
            disciplina.setCodigo(rs.getString("codigo"));
        }

        rs.close();
        stmt.close();
        return disciplina;
    }
    public Disciplina buscarPorCodigo(String codigo) throws SQLException {
        String sql = "SELECT id_disciplina, nome, codigo FROM disciplina WHERE codigo = ?";

        PreparedStatement stmt = DatabaseUtils.getConnection().prepareStatement(sql);
        stmt.setString(1, codigo);
        ResultSet rs = stmt.executeQuery();

        Disciplina disciplina = null;
        if (rs.next()) {
            disciplina = new Disciplina(rs.getInt("id_disciplina"));
            disciplina.setNome(rs.getString("nome"));
            disciplina.setCodigo(rs.getString("codigo"));
        }

        rs.close();
        stmt.close();
        return disciplina;
    }


    public ArrayList<Disciplina> buscarPorNome(String nome) throws SQLException {
        ArrayList<Disciplina> lista = new ArrayList<>();
        String sql = "SELECT id_disciplina, nome, codigo FROM disciplina WHERE nome LIKE ?";
        PreparedStatement stmt = DatabaseUtils.getConnection().prepareStatement(sql);
        stmt.setString(1, '%' + nome + '%');
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            Disciplina disciplina = new Disciplina(rs.getInt("id_disciplina"));
            disciplina.setNome(rs.getString("nome"));
            disciplina.setCodigo(rs.getString("codigo"));
            lista.add(disciplina);
        }

        rs.close();
        stmt.close();
        return lista;
    }


    public void inserir(Disciplina disciplina) throws SQLException {
        String sqlDisciplina = "INSERT INTO disciplina (nome, codigo) VALUES (?, ?)";
        PreparedStatement stmtDisciplina = DatabaseUtils.getConnection().prepareStatement(sqlDisciplina, Statement.RETURN_GENERATED_KEYS);
        stmtDisciplina.setString(1, disciplina.getNome());
        stmtDisciplina.setString(2, disciplina.getCodigo());
        stmtDisciplina.executeUpdate();

        stmtDisciplina.close();
    }

    public void atualizar(Disciplina disciplina) throws SQLException {
        String sqlDisciplina = "UPDATE disciplina SET nome = ?, codigo = ? WHERE id_disciplina = ?";
        PreparedStatement stmtDisciplina = DatabaseUtils.getConnection().prepareStatement(sqlDisciplina);
        stmtDisciplina.setString(1, disciplina.getNome());
        stmtDisciplina.setString(2, disciplina.getCodigo());
        stmtDisciplina.setInt(3, disciplina.getId());
        stmtDisciplina.executeUpdate();

        stmtDisciplina.close();
    }

    public void deletarPorId(int id) throws SQLException {
        String sql = "DELETE FROM disciplina WHERE id_disciplina = ?";
        PreparedStatement stmt = DatabaseUtils.getConnection().prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.executeUpdate();
        stmt.close();
    }
    public void deletarPorCodigo(String codigo) throws SQLException {
        String sql = "DELETE FROM disciplina WHERE codigo = ?";
        PreparedStatement stmt = DatabaseUtils.getConnection().prepareStatement(sql);
        stmt.setString(1, codigo);
        stmt.executeUpdate();
        stmt.close();
    }
}
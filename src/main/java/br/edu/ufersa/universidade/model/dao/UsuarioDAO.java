package br.edu.ufersa.universidade.model.dao;

import br.edu.ufersa.universidade.model.entities.Aluno;
import br.edu.ufersa.universidade.model.entities.Disciplina;
import br.edu.ufersa.universidade.model.entities.Usuario;
import br.edu.ufersa.universidade.utils.DatabaseUtils;

import java.sql.*;
import java.util.ArrayList;

public class UsuarioDAO {
    public ArrayList<Usuario> buscarTodos() throws SQLException {
        ArrayList<Usuario> lista = new ArrayList<>();

        String sql = "SELECT * FROM usuario";

        PreparedStatement stmt = DatabaseUtils.getConnection().prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        // Para cada linha retornada, cria um objeto Aluno e adiciona na lista
        while (rs.next()) {
            Usuario user = new Usuario(rs.getInt("id_usuario"), Usuario.TipoUsuario.valueOf(rs.getString("tipo")));
            user.setNome(rs.getString("nome"));
            user.setSenha(rs.getString("senha"));
            user.setEndereco(rs.getString("endereco"));
            lista.add(user);
        }

        rs.close();
        stmt.close();
        return lista;
    }

    public Usuario buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE id_usuario = ?";
        PreparedStatement stmt = DatabaseUtils.getConnection().prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        Usuario user = null;
        if (rs.next()) {
            user = new Usuario(rs.getInt("id_usuario"), Usuario.TipoUsuario.valueOf(rs.getString("tipo")));
            user.setNome(rs.getString("nome"));
            user.setSenha(rs.getString("senha"));
            user.setEndereco(rs.getString("endereco"));
        }

        rs.close();
        stmt.close();
        return user;
    }

    public ArrayList<Usuario> buscarPorNome(String nome) throws SQLException {
        ArrayList<Usuario> lista = new ArrayList<>();

        String sql = "SELECT * FROM usuario WHERE nome LIKE ?";

        PreparedStatement stmt = DatabaseUtils.getConnection().prepareStatement(sql);
        stmt.setString(1, "%" + nome + "%");
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Usuario user = new Usuario(rs.getInt("id_usuario"), Usuario.TipoUsuario.valueOf(rs.getString("tipo")));
            user.setNome(rs.getString("nome"));
            user.setSenha(rs.getString("senha"));
            user.setEndereco(rs.getString("endereco"));
            lista.add(user);
        }

        rs.close();
        stmt.close();
        return lista;
    }


    public void inserir(Usuario user) throws SQLException {
        String sqlUsuario = "INSERT INTO usuario (nome, senha, endereco, tipo) VALUES (?, ?, ?, ?)";
        PreparedStatement stmtUsuario = DatabaseUtils.getConnection().prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS);
        stmtUsuario.setString(1, user.getNome());
        stmtUsuario.setString(2, user.getSenha());
        stmtUsuario.setString(3, user.getEndereco());
        stmtUsuario.setString(4, user.getTipo().name());
        stmtUsuario.executeUpdate();
        user.setId(stmtUsuario.getGeneratedKeys().getInt("id_usuario"));
    }

    public void atualizar(Usuario user) throws SQLException {
        String sqlUsuario = "UPDATE usuario SET nome = ?, senha = ?, endereco = ?, tipo = ? WHERE id_usuario = ?";
        PreparedStatement stmtUsuario = DatabaseUtils.getConnection().prepareStatement(sqlUsuario);
        stmtUsuario.setString(1, user.getNome());
        stmtUsuario.setString(2, user.getSenha());
        stmtUsuario.setString(3, user.getEndereco());
        stmtUsuario.setString(4, user.getTipo().name());
        stmtUsuario.setInt(5, user.getId());

        stmtUsuario.executeUpdate();

        stmtUsuario.close();
    }

    public void deletarPorId(int id) throws SQLException {
        String sql = "DELETE FROM usuario WHERE id_usuario = ?";
        PreparedStatement stmt = DatabaseUtils.getConnection().prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.executeUpdate();
        stmt.close();
    }

}

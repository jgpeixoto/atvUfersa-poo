package br.edu.ufersa.universidade.model.service;

import br.edu.ufersa.universidade.model.dao.UsuarioDAO;
import br.edu.ufersa.universidade.model.entities.Usuario;

import java.sql.SQLException;
import java.util.ArrayList;

public class UsuarioService {

    private final UsuarioDAO usuarioDAO;

    public UsuarioService(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public void cadastrar(Usuario user) throws SQLException {
        usuarioDAO.inserir(user);
    }

    public ArrayList<Usuario> buscarTodos() throws SQLException {
        return usuarioDAO.buscarTodos();
    }
    public Usuario buscarPorId(int id) throws SQLException {
        return usuarioDAO.buscarPorId(id);
    }
    public ArrayList<Usuario> buscarPorNome(String nome) throws SQLException {
        return usuarioDAO.buscarPorNome(nome);
    }

    public void atualizar(Usuario user) throws SQLException {
        usuarioDAO.atualizar(user);
    }

    public void deletar(Usuario user) throws SQLException {
        this.deletarPorId(user.getId());
    }

    public void deletarPorId(int id) throws SQLException {
        usuarioDAO.deletarPorId(id);
    }

}

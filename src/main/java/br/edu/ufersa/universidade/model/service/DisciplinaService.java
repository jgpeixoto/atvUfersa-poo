package br.edu.ufersa.universidade.model.service;

import br.edu.ufersa.universidade.model.dao.DisciplinaDAO;
import br.edu.ufersa.universidade.model.entities.Disciplina;

import java.sql.SQLException;
import java.util.ArrayList;

public class DisciplinaService {

    private final DisciplinaDAO disciplinaDAO = new DisciplinaDAO();

    public void cadastrar(Disciplina disciplina) throws SQLException {
        disciplinaDAO.inserir(disciplina);
    }

    public ArrayList<Disciplina> buscarTodos() throws SQLException {
        return disciplinaDAO.buscarTodos();
    }
    public Disciplina buscarPorId(int id) throws SQLException {
        return disciplinaDAO.buscarPorId(id);
    }
    public ArrayList<Disciplina> buscarPorNome(String nome) throws SQLException {
        return disciplinaDAO.buscarPorNome(nome);
    }

    public void atualizar(Disciplina disciplina) throws SQLException {
        disciplinaDAO.atualizar(disciplina);
    }

    public void deletar(Disciplina disciplina) throws SQLException {
        this.deletarPorId(disciplina.getId());
    }

    public void deletarPorId(int id) throws SQLException {
        disciplinaDAO.deletarPorId(id);
    }
}

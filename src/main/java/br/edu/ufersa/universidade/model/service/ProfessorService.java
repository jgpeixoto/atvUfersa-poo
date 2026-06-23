package br.edu.ufersa.universidade.model.service;

import br.edu.ufersa.universidade.model.dao.ProfessorDAO;
import br.edu.ufersa.universidade.model.entities.Professor;
import br.edu.ufersa.universidade.model.entities.Usuario;

import java.util.ArrayList;

public class ProfessorService {

    private final ProfessorDAO professorDAO = new ProfessorDAO();

    public void cadastrar(Professor professor, Usuario solicitante) {
        verificarPermissaoGerente(solicitante);
        validar(professor);
        professorDAO.salvar(professor);
    }

    public void editar(Professor professor, Usuario solicitante) {
        verificarPermissaoGerente(solicitante);
        validar(professor);
        professorDAO.atualizar(professor);
    }

    public void deletar(int id, Usuario solicitante) {
        verificarPermissaoGerente(solicitante);
        if (professorDAO.buscarPorId(id) == null)
            throw new RuntimeException("Professor não encontrado para o id: " + id);
        professorDAO.deletar(id);
    }

    public Professor buscarPorId(int id) {
        Professor p = professorDAO.buscarPorId(id);
        if (p == null)
            throw new RuntimeException("Professor não encontrado para o id: " + id);
        return p;
    }

    public Professor buscarPorCpf(String cpf) {
        Professor p = professorDAO.buscarPorCpf(cpf);
        if (p == null)
            throw new RuntimeException("Professor não encontrado para o CPF: " + cpf);
        return p;
    }

    public ArrayList<Professor> buscarPorNome(String nome) {
        if (nome == null || nome.isBlank())
            throw new IllegalArgumentException("Nome para busca não pode ser vazio.");
        return professorDAO.buscarPorNome(nome);
    }

    public ArrayList<Professor> listarTodos() {
        return professorDAO.listarTodos();
    }

    private void validar(Professor professor) {
        if (professor == null)
            throw new IllegalArgumentException("Professor não pode ser nulo.");
        if (professor.getNome() == null || professor.getNome().isBlank())
            throw new IllegalArgumentException("Nome do professor não pode ser vazio.");
        if (professor.getCpf() == null || professor.getCpf().length() != 11)
            throw new IllegalArgumentException("CPF inválido. Deve conter 11 dígitos.");
        if (professor.getEndereco() == null || professor.getEndereco().isBlank())
            throw new IllegalArgumentException("Endereço do professor não pode ser vazio.");
    }

    private void verificarPermissaoGerente(Usuario solicitante) {
        if (solicitante == null || solicitante.getTipo() != Usuario.TipoUsuario.Admin)
            throw new RuntimeException("Acesso negado: apenas o gerente pode realizar esta operação.");
    }
}

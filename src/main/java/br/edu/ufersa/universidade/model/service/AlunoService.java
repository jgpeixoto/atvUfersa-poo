package br.edu.ufersa.universidade.model.service;

import br.edu.ufersa.universidade.model.dao.AlunoDAO;
import br.edu.ufersa.universidade.model.dao.IndiceDAO;
import br.edu.ufersa.universidade.model.entities.Aluno;
import br.edu.ufersa.universidade.model.entities.Disciplina;
import br.edu.ufersa.universidade.model.entities.Indice;
import java.sql.SQLException;
import java.util.ArrayList;

public class AlunoService {

    // O Service usa os DAOs para acessar o banco
    private final AlunoDAO alunoDAO;
    private final IndiceDAO indiceDAO;

    public AlunoService(AlunoDAO alunoDAO, IndiceDAO indiceDAO) {
        this.alunoDAO = alunoDAO;
        this.indiceDAO = indiceDAO;
    }


    // LISTAR TODOS OS ALUNOS
    // Só repassa pro DAO, sem regra de negócio aqui

    public ArrayList<Aluno> listarTodos() throws SQLException {
        return alunoDAO.buscarTodos();
    }


    // BUSCAR ALUNO POR MATRÍCULA

    public Aluno buscarPorMatricula(long matricula) throws SQLException {
        return alunoDAO.buscarPorMatricula(matricula);
    }


    // CADASTRAR ALUNO
    // Regra de negócio: matrícula não pode ser negativa

    public void cadastrar(Aluno aluno) throws SQLException {
        if (aluno.getMatricula() <= 0) {
            throw new IllegalArgumentException("Matrícula inválida!");
        }
        if (aluno.getNome() == null || aluno.getNome().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio!");
        }
        alunoDAO.inserir(aluno);
    }


    // CALCULAR IRA DO ALUNO
    // Regra de negócio: busca os índices do aluno no banco
    // e calcula a média de todas as disciplinas concluídas

    public double calcularIRA(long matricula) throws SQLException {
        ArrayList<Indice> indices = indiceDAO.buscarPorAluno(matricula);

        if (indices.isEmpty()) return 0;

        double total = 0;
        int count = 0;

        for (Indice indice : indices) {
            // Só conta disciplinas concluídas (aprovado ou reprovado), não as em andamento
            if (indice.getEstado() != Indice.EstadoMatricula.Matr &&
                    indice.getEstado() != Indice.EstadoMatricula.Canc) {
                total += indice.obterMedia();
                count++;
            }
        }

        if (count == 0) return 0;
        return total / count;
    }


    // VERIFICAR SE ALUNO ESTÁ APROVADO EM UMA DISCIPLINA
    // Regra de negócio: média >= 5 e faltas <= 25% das aulas

    public boolean estaAprovado(Indice indice) {
        double media = indice.obterMedia();
        // Considerando máximo de 60 aulas no semestre
        double percentualFaltas = (indice.getFaltas() / 60.0) * 100;

        return media >= 5.0 && percentualFaltas <= 25.0;
    }


    // DELETAR ALUNO

    public void deletar(long matricula) throws SQLException {
        Aluno aluno = alunoDAO.buscarPorMatricula(matricula);
        if (aluno == null) {
            throw new IllegalArgumentException("Aluno não encontrado!");
        }
        alunoDAO.deletar(matricula);
    }

    public ArrayList<Disciplina> obterDisciplinasConcluidas(Aluno aluno) throws SQLException {
        return alunoDAO.obterDisciplinasConcluidas(aluno);
    }
}
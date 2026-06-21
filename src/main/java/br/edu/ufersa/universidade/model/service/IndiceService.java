package br.edu.ufersa.universidade.model.service;

import br.edu.ufersa.universidade.model.dao.IndiceDAO;
import br.edu.ufersa.universidade.model.entities.Indice;
import java.sql.SQLException;
import java.util.ArrayList;

public class IndiceService {

    private final IndiceDAO indiceDAO = new IndiceDAO();

    // LISTAR ÍNDICES DE UM ALUNO

    public ArrayList<Indice> listarPorAluno(long matricula) throws SQLException {
        return indiceDAO.buscarPorAluno(matricula);
    }


    // MATRICULAR ALUNO EM UMA TURMA
    // Regra de negócio: estado inicial sempre é "Matr"

    public void matricular(long matriculaAluno, Indice indice) throws SQLException {
        // Garante que ao matricular, o estado começa como Matriculado
        indice.setEstado(Indice.EstadoMatricula.Matr);
        indice.setNota1(0);
        indice.setNota2(0);
        indice.setNota3(0);
        indice.setFaltas(0);
        indiceDAO.inserir(indice, matriculaAluno);
    }

    // LANÇAR NOTAS
    // Regra de negócio: notas devem ser entre 0 e 100
    // Atualiza o estado automaticamente (Apr ou Rep)

    public void lancarNotas(int idIndice, Indice indice) throws SQLException {
        if (indice.getNota1() < 0 || indice.getNota1() > 100 ||
                indice.getNota2() < 0 || indice.getNota2() > 100 ||
                indice.getNota3() < 0 || indice.getNota3() > 100) {
            throw new IllegalArgumentException("Notas devem ser entre 0 e 100!");
        }

        // Define o estado automaticamente com base na média
        double media = indice.obterMedia();
        if (media >= 50) {
            indice.setEstado(Indice.EstadoMatricula.Apr); // Aprovado
        } else {
            indice.setEstado(Indice.EstadoMatricula.Rep); // Reprovado
        }

        indiceDAO.atualizar(indice);
    }


    // CANCELAR MATRÍCULA
    // Regra de negócio: muda o estado para Canc

    public void cancelarMatricula(int idIndice) throws SQLException {
        Indice indice = indiceDAO.buscarPorId(idIndice);
        if (indice == null) {
            throw new IllegalArgumentException("Índice não encontrado!");
        }
        if (indice.getEstado() != Indice.EstadoMatricula.Matr) {
            throw new IllegalArgumentException("Só é possível cancelar matrículas ativas!");
        }
        indice.setEstado(Indice.EstadoMatricula.Canc);
        indiceDAO.atualizar(indice);
    }
}

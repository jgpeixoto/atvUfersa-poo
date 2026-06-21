package br.edu.ufersa.universidade.model.dao;

import br.edu.ufersa.universidade.model.entities.Indice;
import br.edu.ufersa.universidade.model.entities.Turma;
import br.edu.ufersa.universidade.utils.DatabaseUtils;

import java.sql.*;
import java.util.ArrayList;

public class IndiceDAO {
    // BUSCAR TODOS OS ÍNDICES DE UM ALUNO
    // Recebe a matrícula e retorna todos os índices do aluno

    public ArrayList<Indice> buscarPorAluno(long matricula) throws SQLException {
        ArrayList<Indice> lista = new ArrayList<>();

        String sql = "SELECT * FROM indice WHERE matricula_aluno = ?";
        PreparedStatement stmt = DatabaseUtils.getConnection().prepareStatement(sql);
        stmt.setLong(1, matricula);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            // Cria o Indice com o id vindo do banco
            Indice indice = new Indice(rs.getInt("id_indice"));

            // Cria a Turma e associa ao Indice
            Turma turma = new Turma(rs.getInt("id_turma"));
            indice.setTurma(turma);

            indice.setNota1(rs.getInt("nota1"));
            indice.setNota2(rs.getInt("nota2"));
            indice.setNota3(rs.getInt("nota3"));
            indice.setFaltas(rs.getInt("faltas"));

            // Converte o texto do banco para o enum EstadoMatricula
            String estadoStr = rs.getString("estado");
            indice.setEstado(Indice.EstadoMatricula.valueOf(estadoStr));

            lista.add(indice);
        }

        rs.close();
        stmt.close();
        return lista;
    }


    // BUSCAR ÍNDICE POR ID

    public Indice buscarPorId(int idIndice) throws SQLException {
        String sql = "SELECT * FROM indice WHERE id_indice = ?";
        PreparedStatement stmt = DatabaseUtils.getConnection().prepareStatement(sql);
        stmt.setInt(1, idIndice);
        ResultSet rs = stmt.executeQuery();

        Indice indice = null;
        if (rs.next()) {
            // Cria o Indice com o id vindo do banco
            indice = new Indice(rs.getInt("id_indice"));

            // Cria a Turma e associa ao Indice
            Turma turma = new Turma(rs.getInt("id_turma"));
            indice.setTurma(turma);

            indice.setNota1(rs.getInt("nota1"));
            indice.setNota2(rs.getInt("nota2"));
            indice.setNota3(rs.getInt("nota3"));
            indice.setFaltas(rs.getInt("faltas"));
            indice.setEstado(Indice.EstadoMatricula.valueOf(rs.getString("estado")));
        }

        rs.close();
        stmt.close();
        return indice;
    }


    // INSERIR ÍNDICE
    // Matricula um aluno em uma turma

    public void inserir(Indice indice, long matriculaAluno) throws SQLException {
        String sql = "INSERT INTO indice (matricula_aluno, id_turma, nota1, nota2, nota3, faltas, estado) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement stmt = DatabaseUtils.getConnection().prepareStatement(sql);
        stmt.setLong(1, matriculaAluno);
        stmt.setInt(2, indice.getTurma().getId());
        stmt.setInt(3, indice.getNota1());
        stmt.setInt(4, indice.getNota2());
        stmt.setInt(5, indice.getNota3());
        stmt.setInt(6, indice.getFaltas());
        stmt.setString(7, indice.getEstado().name());
        stmt.executeUpdate();
        stmt.close();
    }


    // ATUALIZAR NOTAS E FALTAS

    public void atualizar(Indice indice) throws SQLException {
        String sql = "UPDATE indice SET nota1 = ?, nota2 = ?, nota3 = ?, faltas = ?, estado = ? " +
                "WHERE id_indice = ?";

        PreparedStatement stmt = DatabaseUtils.getConnection().prepareStatement(sql);
        stmt.setInt(1, indice.getNota1());
        stmt.setInt(2, indice.getNota2());
        stmt.setInt(3, indice.getNota3());
        stmt.setInt(4, indice.getFaltas());
        stmt.setString(5, indice.getEstado().name());
        stmt.setInt(6, indice.getId());
        stmt.executeUpdate();
        stmt.close();
    }


    // DELETAR ÍNDICE

    public void deletar(int idIndice) throws SQLException {
        String sql = "DELETE FROM indice WHERE id_indice = ?";
        PreparedStatement stmt = DatabaseUtils.getConnection().prepareStatement(sql);
        stmt.setInt(1, idIndice);
        stmt.executeUpdate();
        stmt.close();
    }

    public ArrayList<Indice> buscarPorTurma(int idTurma) throws SQLException {
        ArrayList<Indice> lista = new ArrayList<>();
        String sql = "SELECT * FROM indice WHERE id_turma = ?";
        PreparedStatement stmt = DatabaseUtils.getConnection().prepareStatement(sql);
        stmt.setInt(1, idTurma);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Indice indice = new Indice(rs.getInt("id_indice"));
            Turma turma = new Turma(rs.getInt("id_turma"));
            indice.setTurma(turma);
            indice.setNota1(rs.getInt("nota1"));
            indice.setNota2(rs.getInt("nota2"));
            indice.setNota3(rs.getInt("nota3"));
            indice.setFaltas(rs.getInt("faltas"));
            indice.setEstado(Indice.EstadoMatricula.valueOf(rs.getString("estado")));
            lista.add(indice);
        }
        rs.close();
        stmt.close();
        return lista;
    }
}
package br.edu.ufersa.universidade.dao;

import br.edu.ufersa.universidade.model.entities.Aluno;
import java.sql.*;
import java.util.ArrayList;

public class AlunoDAO {

    // Conexão com o banco de dados, recebida pelo construtor
    private Connection connection;

    public AlunoDAO(Connection connection) {
        this.connection = connection;
    }


    // BUSCAR TODOS OS ALUNOS
    // Faz SELECT nas tabelas aluno + usuario (JOIN)
    // e retorna uma lista com todos os alunos

    public ArrayList<Aluno> buscarTodos() throws SQLException {
        ArrayList<Aluno> lista = new ArrayList<>();

        String sql = "SELECT u.id_usuario, u.nome, u.senha, u.endereco, a.matricula " +
                "FROM aluno a " +
                "JOIN usuario u ON a.id_usuario = u.id_usuario";

        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        // Para cada linha retornada, cria um objeto Aluno e adiciona na lista
        while (rs.next()) {
            Aluno aluno = new Aluno(rs.getInt("id_usuario"));
            aluno.setNome(rs.getString("nome"));
            aluno.setSenha(rs.getString("senha"));
            aluno.setEndereco(rs.getString("endereco"));
            aluno.setMatricula(rs.getLong("matricula"));
            lista.add(aluno);
        }

        rs.close();
        stmt.close();
        return lista;
    }


    // BUSCAR ALUNO POR MATRÍCULA
    // Retorna um único aluno ou null se não encontrar

    public Aluno buscarPorMatricula(long matricula) throws SQLException {
        String sql = "SELECT u.id_usuario, u.nome, u.senha, u.endereco, a.matricula " +
                "FROM aluno a " +
                "JOIN usuario u ON a.id_usuario = u.id_usuario " +
                "WHERE a.matricula = ?";

        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setLong(1, matricula); // substitui o ? pelo valor da matrícula
        ResultSet rs = stmt.executeQuery();

        Aluno aluno = null;
        if (rs.next()) {
            aluno = new Aluno(rs.getInt("id_usuario"));
            aluno.setNome(rs.getString("nome"));
            aluno.setSenha(rs.getString("senha"));
            aluno.setEndereco(rs.getString("endereco"));
            aluno.setMatricula(rs.getLong("matricula"));
        }

        rs.close();
        stmt.close();
        return aluno;
    }


    // BUSCAR ALUNO POR NOME
    // Usa LIKE para busca parcial (ex: "Jo" encontra "João")

    public ArrayList<Aluno> buscarPorNome(String nome) throws SQLException {
        ArrayList<Aluno> lista = new ArrayList<>();

        String sql = "SELECT u.id_usuario, u.nome, u.senha, u.endereco, a.matricula " +
                "FROM aluno a " +
                "JOIN usuario u ON a.id_usuario = u.id_usuario " +
                "WHERE u.nome LIKE ?";

        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, "%" + nome + "%"); // % significa "qualquer coisa antes/depois"
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Aluno aluno = new Aluno(rs.getInt("id_usuario"));
            aluno.setNome(rs.getString("nome"));
            aluno.setSenha(rs.getString("senha"));
            aluno.setEndereco(rs.getString("endereco"));
            aluno.setMatricula(rs.getLong("matricula"));
            lista.add(aluno);
        }

        rs.close();
        stmt.close();
        return lista;
    }


    // INSERIR ALUNO
    // Primeiro insere na tabela usuario, depois na tabela aluno

    public void inserir(Aluno aluno) throws SQLException {
        // Passo 1: insere na tabela usuario
        String sqlUsuario = "INSERT INTO usuario (nome, senha, endereco, tipo) VALUES (?, ?, ?, 'Aluno')";
        PreparedStatement stmtUsuario = connection.prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS);
        stmtUsuario.setString(1, aluno.getNome());
        stmtUsuario.setString(2, aluno.getSenha());
        stmtUsuario.setString(3, aluno.getEndereco());
        stmtUsuario.executeUpdate();

        // Pega o id gerado automaticamente pelo banco
        ResultSet chave = stmtUsuario.getGeneratedKeys();
        int idUsuario = 0;
        if (chave.next()) {
            idUsuario = chave.getInt(1);
        }

        // Passo 2: insere na tabela aluno com o id gerado
        String sqlAluno = "INSERT INTO aluno (matricula, id_usuario) VALUES (?, ?)";
        PreparedStatement stmtAluno = connection.prepareStatement(sqlAluno);
        stmtAluno.setLong(1, aluno.getMatricula());
        stmtAluno.setInt(2, idUsuario);
        stmtAluno.executeUpdate();

        stmtAluno.close();
        stmtUsuario.close();
    }


    // DELETAR ALUNO POR MATRÍCULA

    public void deletar(long matricula) throws SQLException {
        String sql = "DELETE FROM aluno WHERE matricula = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setLong(1, matricula);
        stmt.executeUpdate();
        stmt.close();
    }
}
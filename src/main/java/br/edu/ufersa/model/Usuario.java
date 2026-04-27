package br.edu.ufersa.model;

public class Usuario {
    public enum TipoUsuario {
        Aluno,
        Prof,
        Admin
    }

    protected int id;
    protected String nome;
    protected String senha;
    protected String endereco;
    protected TipoUsuario tipo;

    public Usuario(int id, TipoUsuario tipo) {
        this.id = id;
        this.tipo = tipo;
    }

    public int getId() { // apenas get, dado apenas ao instanciar
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public TipoUsuario getTipo() {
        return tipo;
    }

    public void setTipo(TipoUsuario tipo) {
        this.tipo = tipo;
    }

    public static Aluno cadastrarAluno(String nome, String endereco, long matricula) {
        Aluno al = new Aluno(ids++);
        al.setNome(nome);
        al.setEndereco(endereco);
        al.setMatricula(matricula);
        return al;
    }

    public static Professor cadastrarProfessor(String nome, String endereco, String cpf) {
        Professor prof = new Professor(ids++);
        prof.setNome(nome);
        prof.setEndereco(endereco);
        prof.setCpf(cpf);
        return prof;
    }

    public static Usuario cadastrarAdmin(String nome, String endereco) {
        Usuario admin = new Usuario(ids++, TipoUsuario.Admin);
        admin.setNome(nome);
        admin.setEndereco(endereco);
        return admin;
    }

    private static int ids = 0; // temporário, indexação seria lidado pelo banco de dados
}
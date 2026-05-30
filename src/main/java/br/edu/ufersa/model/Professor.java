package br.edu.ufersa.model;

public class Professor extends Usuario {
    private String cpf;
    private Turma[] turmas;

    public Professor(int id) {
        super(id, TipoUsuario.Prof);
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        if (cpf.length() == 11) // 11 digitos no CPF
            this.cpf = cpf;
    }

    public Turma[] getTurmas() { // Get only por ser lista
        /*
            Inserir query de banco de dados para busca aqui numa
            implementação real
            SELECT * FROM turmas WHERE id_professor == {this.id}
        */
        return turmas; 
    }
}

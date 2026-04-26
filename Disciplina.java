public class Disciplina {
    private int id;
    private String nome;
    private String codigo;

    public Disciplina(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    static Disciplina[] buscar(String nome) {
        /*
            inserir query de banco de dados para busca aqui numa
            implementação real
            SELECT * FROM disciplinas WHERE nome LIKE %{this.nome}%
        */
        return new Disciplina[0];
    }
}

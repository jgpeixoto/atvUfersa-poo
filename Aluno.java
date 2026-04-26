import java.util.Arrays;
import java.util.List;

public class Aluno extends Usuario {

    private long matricula;
    private Indice[] indices;

    // Construtor
    public Aluno(int id) {
        super(id, TipoUsuario.Aluno);
    }

    public long getMatricula() {
        return matricula;
    }

    public void setMatricula(long matricula) {
        this.matricula = matricula;
    }

    // Calcula o IRA (Índice de Rendimento Acadêmico)
    public double obterIRA() {
        if (indices == null || indices.length == 0)
            return 0;

        int disciplinas = 0;
        double notaTotal = 0;

        for (; disciplinas < this.indices.length; disciplinas++) {
            notaTotal += this.indices[disciplinas].obterMedia();
        }

        return notaTotal / disciplinas;
    }

    // Busca índice pela turma
    public Indice obterIndice(Turma t) {
        if (indices == null) return null;

        for (int i = 0; i < this.indices.length; i++) {
            if (this.indices[i].getTurma().getId() == t.getId())
                return this.indices[i];
        }
        return null;
    }

    // Busca índice pela disciplina
    public Indice obterIndice(Disciplina t) {
        if (indices == null) return null;

        for (int i = 0; i < this.indices.length; i++) {
            if (this.indices[i].getTurma().getDisciplina().getId() == t.getId())
                return this.indices[i];
        }
        return null;
    }

    // Retorna apenas índices concluídos (não matriculados)
    public Indice[] obterConcluidos() {
        if (indices == null) return new Indice[0];

        List<Indice> array = Arrays.asList(this.indices);

        // Remove os que ainda estão matriculados
        array.removeIf((Indice i) -> {
            return i.getEstado() == Indice.EstadoMatricula.Matr;
        });

        return array.toArray(new Indice[0]);
    }

    // Simulação de busca no banco
    public static Aluno buscar(String nome) {
        /*
            Em um sistema real, aqui seria feita uma consulta no banco:

            SELECT * FROM alunos WHERE nome LIKE '%nome%';
        */
        return null;
    }
}
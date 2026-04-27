package br.edu.ufersa.model;
import java.util.ArrayList;

public class Turma {
    public enum EstadoTurma {
        Ativo,
        Fin
    }

    private int id;
    private Disciplina disciplina;
    private Professor professor;
    private Indice[] indices;
    private int aulasMinistradas;
    private String local;
    private String horario; 
    // Dias-Turno-Horarios ; 
    // Ex: 24T45 - Segunda e quarta à tarde, do quarto ao quinto horário
    private EstadoTurma estado;

    public Turma(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    public Disciplina getDisciplina() {
        return disciplina;
    }
    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }
    public Professor getProfessor() {
        return professor;
    }
    public void setProfessor(Professor professor) {
        this.professor = professor;
    }
    public Indice[] getIndices() {
        return indices;
    }
    public void setIndices(Indice[] indices) {
        this.indices = indices;
    }
    public int getAulasMinistradas() {
        return aulasMinistradas;
    }
    public void setAulasMinistradas(int aulasMinistradas) {
        this.aulasMinistradas = aulasMinistradas;
    }
    public String getLocal() {
        return local;
    }
    public void setLocal(String local) {
        this.local = local;
    }
    public String getHorario() {
        return horario;
    }
    public void setHorario(String horario) {
        this.horario = horario;
    }
    public EstadoTurma getEstado() {
        return estado;
    }
    public void setEstado(EstadoTurma estado) {
        this.estado = estado;
    }

    public Aluno[] obterAlunos() {
        ArrayList<Aluno> list = new ArrayList<Aluno>();
        for (int i = 0; i < this.indices.length; i++)
        {
            list.add(this.indices[i].getAluno());
        }
        return (Aluno[])list.toArray();
    }

    public double obterFrequenciaTurma() {
        double frTotal = 0;
        int numAlunos = 0;
        for (; numAlunos < this.indices.length; numAlunos++)
        {
            frTotal += this.indices[numAlunos].obterFrequencia();
        }
        return frTotal / numAlunos;
    }

    public double obterMediaTurma() {
        double notaTotal = 0;
        int numAlunos = 0;
        for (; numAlunos < this.indices.length; numAlunos++)
        {
            notaTotal += this.indices[numAlunos].obterMedia();
        }
        return notaTotal / numAlunos;
    }

    public void fecharTurma() {
        for (int i = 0; i < this.indices.length; i++)
        {
            Indice ind = this.indices[i];
            if (ind.getEstado() == Indice.EstadoMatricula.Matr) {
                if (ind.obterMedia() >= 70 && ind.obterFrequencia() >= 75) 
                    ind.setEstado(Indice.EstadoMatricula.Apr); // Deve ter nota >= 70 e Frequencia >= 75%
                else
                    ind.setEstado(Indice.EstadoMatricula.Rep);
            }
        }
        this.setEstado(EstadoTurma.Fin);
    }

    public static Turma buscar(Disciplina disciplina) {
        /*
            Inserir query de banco de dados para busca aqui numa
            implementação real
            SELECT * FROM turmas WHERE id_disciplina == {disciplina.getId()}
        */
       return null;
    }
}
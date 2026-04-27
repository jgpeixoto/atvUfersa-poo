package br.com.ufersa.model;
public class Indice {

    // Enum que representa o estado da matrícula do aluno na turma
    public enum EstadoMatricula {
        Matr, // Matriculado
        Apr,  // Aprovado
        Rep,  // Reprovado
        Canc  // Cancelado
    }

    private int id;
    private Aluno aluno;
    private Turma turma;

    // Notas do aluno (0 a 100)
    private int nota1;
    private int nota2;
    private int nota3;

    private int faltas;
    private EstadoMatricula estado;

    // Construtor
    public Indice(int id) {
        this.id = id;
    }

    // Getter do ID
    public int getId() {
        return id;
    }

    // Getters e Setters básicos
    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public int getNota1() {
        return nota1;
    }

    // Define a nota apenas se for válida
    public void setNota1(int nota1) {
        if (isValidGrade(nota1))
            this.nota1 = nota1;
    }

    public int getNota2() {
        return nota2;
    }

    public void setNota2(int nota2) {
        if (isValidGrade(nota2)) // pequena correção
            this.nota2 = nota2;
    }

    public int getNota3() {
        return nota3;
    }

    public void setNota3(int nota3) {
        if (isValidGrade(nota3)) // pequena correção
            this.nota3 = nota3;
    }

    public int getFaltas() {
        return faltas;
    }

    public void setFaltas(int faltas) {
        this.faltas = faltas;
    }

    public EstadoMatricula getEstado() {
        return estado;
    }

    public void setEstado(EstadoMatricula estado) {
        this.estado = estado;
    }

    // Verifica se a nota está entre 0 e 100
    private boolean isValidGrade(int nota) {
        return nota >= 0 && nota <= 100;
    }

    // Calcula a média das 3 notas
    public int obterMedia() {
        return (nota1 + nota2 + nota3) / 3;
    }

    // Calcula a frequência do aluno na turma
    public int obterFrequencia() {
        // evita divisão por zero
        if (turma == null || turma.getAulasMinistradas() == 0)
            return 0;

        return ((turma.getAulasMinistradas() - faltas) * 100) / turma.getAulasMinistradas();
    }
}
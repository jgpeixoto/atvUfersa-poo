package br.edu.ufersa.universidade.model.entities;
import java.util.ArrayList;

public class Turma {
    public enum EstadoTurma {
        Ativo,
        Fin
    }

    private int id;
    private Disciplina disciplina;
    private Professor professor;
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
    public void setId(int id) { this.id = id; }
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
}
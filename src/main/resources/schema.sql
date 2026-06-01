CREATE TABLE usuario (
  id_usuario INTEGER PRIMARY KEY AUTO_INCREMENT,
  nome TEXT,
  senha TEXT,
  endereco TEXT,
  tipo ENUM('Aluno', 'Prof', 'Admin')
);

CREATE TABLE professor (
    cpf VARCHAR(11) PRIMARY KEY DEFAULT '00000000000',
    id_usuario INTEGER,
    CONSTRAINT fk_usuario_prof
    FOREIGN KEY (id_usuario)
    REFERENCES usuario(id_usuario)
    ON DELETE CASCADE
);

CREATE TABLE aluno (
    matricula BIGINT PRIMARY KEY,
    id_usuario INTEGER,
    CONSTRAINT fk_usuario_aluno
    FOREIGN KEY (id_usuario)
    REFERENCES usuario(id_usuario)
    ON DELETE CASCADE
);

CREATE TABLE disciplina (
    id_disciplina INTEGER PRIMARY KEY AUTO_INCREMENT,
    nome TEXT,
    codigo TEXT
);

CREATE TABLE turma (
    id_turma INTEGER PRIMARY KEY AUTO_INCREMENT,
    id_disciplina INTEGER,
    cpf_professor VARCHAR(11),
    aulas_ministradas INTEGER,
    local_de_aula TEXT,
    horario TEXT,
    ativo BOOL,
    CONSTRAINT fk_professor_turma
    FOREIGN KEY (cpf_professor)
    REFERENCES professor(cpf)
    ON DELETE CASCADE,
    CONSTRAINT fk_disciplina_turma
    FOREIGN KEY (id_disciplina)
    REFERENCES disciplina(id_disciplina)
    ON DELETE CASCADE
);

CREATE TABLE indice (
    id_indice INTEGER PRIMARY KEY AUTO_INCREMENT,
    matricula_aluno BIGINT,
    id_turma INTEGER,
    nota1 INTEGER,
    nota2 INTEGER,
    nota3 INTEGER,
    faltas INTEGER,
    estado ENUM('Matr', 'Apr', 'Rep', 'Canc'),
    CONSTRAINT fk_aluno_indice
    FOREIGN KEY (matricula_aluno)
    REFERENCES aluno(matricula)
    ON DELETE CASCADE,
    CONSTRAINT fk_turma_indice
    FOREIGN KEY (id_turma)
    REFERENCES turma(id_turma)
    ON DELETE CASCADE
);

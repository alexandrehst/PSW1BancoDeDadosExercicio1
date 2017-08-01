# PSW1BancoDeDadosExercicio1
Este exercício contém um modelo inicial de um objeto de negócio (Aluno) que tem conhecimento da estrutura do banco de dados e é responsável por todas as operações no banco.

**ATENÇÃO** Execute os seguintes comandos para baixar o commit correto:

```bash
git clone 
```

## Configurações

Copie o código abaixo para criar as tabelas do banco de dados:

```sql
DROP TABLE ALUNOS;
DROP TABLE MATERIAS;

CREATE TABLE MATERIAS(
    ID INTEGER NOT NULL CONSTRAINT MATERIAS_PK PRIMARY KEY,
    DESCRICAO VARCHAR(50)
);

CREATE TABLE ALUNOS(
    ID INTEGER NOT NULL CONSTRAINT ALUNOS_PK PRIMARY KEY,
    NOME VARCHAR(50),
    RA INTEGER
);

ALTER TABLE ALUNOS
ADD CONSTRAINT RA_UC UNIQUE (RA);
```

A string de conexão está preparada para o banco de dados do Glassfish. Caso necessário, faça a alteação.

## Exercício 1
Vamos analisar a implementação da classe `Aluno`.
- `getProximoId`: executa um SELECT MAX e obtem o próximo número da PK
- `insere`: usa o próprio objeto e o `getProximoId` para montar o SQL de inserção no banco.
- `getAluno`: método `static` que devolve um objeto `Aluno` preenchido a partir de um RA
- `getAlunos`: método `static`que devolve um `ArrayList` de alunos.

Implemente os métodos:
- `altera`: altera o nome do aluno, a partir dos valores do objeto.
- `exclui`: exclui o aluno.

## Exercício 2
Crie uma classe `Materia` com os atribuitos (a partir da tabela MATERIAS)
Crie os métodos de acesso e alteração de dados semelhante aos existentes na classe `Aluno`

## Exercício 3

1. Execute o seguinte SQL para alterar a tabela `ALUNOS`

```sql
ALTER TABLE ALUNOS
ADD COLUMN FK_MATERIA INT CONSTRAINT MATERIA_FK REFERENCES MATERIAS; 
```
*Agora um aluno deve ter uma matéria*

2. Crie um atributo `materia` na classe `Aluno`

3. Altere os métodos `inserir` e `alterar` para permitir a inserção e atualização da matéria do aluno.

## Exercício 4

Na classe `Aluno` crie um método `static` chamado `obterAlunosMateria` que recebe uma `Materia` e devolve um `ArrayList` dos alunos daquela matéria

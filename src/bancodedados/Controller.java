/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bancodedados;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLNonTransientConnectionException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author alexandretorres
 */
public class Controller {
            
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String url = "jdbc:derby://localhost:1527/BD";
        String user = "usuario";
        String password = "senha";
               
        try(
            Connection conn = DriverManager.getConnection(url, user, password);                        
        ){
            System.out.println("---- Criando uma matéria ----");
            Materia m = new Materia("PSW");
            m.insere(conn);
            System.out.println("Matéria inserida\n");
            
            System.out.println("--- Criando um objeto aluno (com matéria) ---");
           
            Aluno a = new Aluno(284213,"José da Silva",m);
            
            System.out.println("--- Inserindo no Bando de dados ---");
            int linhas = a.insere(conn);
            if (linhas > 0 ) System.out.println("Inserido com sucesso (" + linhas +" linhas inseridas)");
            
            mostraAlunos(conn);
            
           
            System.out.println("--- Alterando ---");
            a.setNome("Novo nome");
            linhas = a.altera(conn);
  
            mostraAlunos(conn);
            
            System.out.println("--- Excluindo --- ");
            a.exclui(conn);
            
            mostraAlunos(conn);
            
            System.out.println("---- mostrando a matéria do aluno ---");
            System.out.println(a.getMateria(conn).getDescricao());
                        
        }catch(SQLNonTransientConnectionException e){
            System.out.println("Erro ao conectar ao servidor. Verifique se foi iniciado.");
        }catch(SQLException e){
            System.out.println("Erro no acesso ao banco de dados");
            e.printStackTrace();
        }
        
    }
    
    public static void mostraAlunos(Connection conn) throws SQLException{
        System.out.println("--- Mostrando os alunos ---");
        ArrayList<Aluno> alunos = Aluno.getAlunos(conn);

        for (Aluno aluno:alunos){
            System.out.println(aluno.getRa() + " " + aluno.getNome());
        }
    }

    
}

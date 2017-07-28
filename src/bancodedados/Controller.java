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
            Aluno a = Aluno.getAluno(conn, 1);
            
            Aluno b = new Aluno(2,"José da Silva");
            b.insere(conn);
                        
            System.out.println("Mostrando os alunos");
            ArrayList<Aluno> alunos = Aluno.getAlunos(conn);
            
            for (Aluno aluno:alunos){
                System.out.println(aluno.getRa() + " " + aluno.getNome());
            }
            
            
        }catch(SQLException e){
            System.out.println("Erro no acesso ao banco de dados");
            e.printStackTrace();
        }
        
    }

    
}

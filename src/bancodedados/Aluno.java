/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bancodedados;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

/**
 *
 * @author alexandretorres
 */
public class Aluno {
    private String nome;
    private int ra;    
    
    public Aluno(int ra, String nome){
        this.ra = ra;
        this.nome = nome;
    }
    
    public int insere(Connection conn) throws SQLException{
        PreparedStatement stat = conn.prepareStatement("INSERT INTO ALUNOS(NOME, RA) "
                                                + "VALUES (?,?)");
        stat.setString(1, nome);
        stat.setInt(2, ra);
        int linhas = 0;
        
        try{
            linhas = stat.executeUpdate();
        }catch(SQLIntegrityConstraintViolationException e){
            System.out.println("Aluno " + getRa() + " já existe");
        }
        
        return linhas;

    }
    
    public static Aluno getAluno(Connection conn, int ra) throws SQLException{
        Aluno a = null;
        String select = "SELECT * FROM ALUNOS WHERE RA = " + ra;
        Statement stat = conn.createStatement();
        ResultSet rs = stat.executeQuery(select);
        
        while (rs.next()) {
            String nome = rs.getString("NOME");
            a = new Aluno(ra, nome);
            break;
        }
        
        return a;                
    }

    public static ArrayList getAlunos(Connection conn) throws SQLException{
        ArrayList<Aluno> alunos = new ArrayList<>();

        String select = "SELECT * FROM ALUNOS";
        Statement stat = conn.createStatement();
        ResultSet rs = stat.executeQuery(select);
        
        while (rs.next()) {
            alunos.add( 
                    new Aluno (rs.getInt("RA"), rs.getString("NOME"))
            );
        }
        
        return alunos;     
    }
    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the ra
     */
    public int getRa() {
        return ra;
    }

    /**
     * @param ra the ra to set
     */
    public void setRa(int ra) {
        this.ra = ra;
    }
    
    
}

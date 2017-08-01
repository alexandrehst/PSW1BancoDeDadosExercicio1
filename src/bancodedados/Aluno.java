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
    private int id;
    private String nome;
    private int ra;  
    private int idMateria;
    private Materia materia;
    
    public Aluno(int ra, String nome){
        this.ra = ra;
        this.nome = nome;
    }
    
    public Aluno(int id, int ra, String nome){
        this(ra, nome);
        this.id = id;
    }
    
    public Aluno( int ra, String nome, Materia materia){
        this(ra, nome);
        this.materia = materia;
        this.idMateria = materia.getId();
    }
    
    public int insere(Connection conn) throws SQLException{
        PreparedStatement stat = conn.prepareStatement("INSERT INTO ALUNOS(ID, NOME, RA, FK_MATERIA) "
                                                + "VALUES (?, ?,?, ?)");
        this.id = Aluno.getProximoId(conn);
        stat.setInt(1, this.id);
        stat.setString(2, nome);
        stat.setInt(3, ra);
        stat.setInt(4, idMateria);
        int linhas = 0;
        
        linhas = stat.executeUpdate();

        
        return linhas;
    }

    public int altera(Connection conn) throws SQLException{
        String sql = "UPDATE ALUNOS SET RA = ?, NOME = ?, FK_MATERIA = ? WHERE ID = ?";
        PreparedStatement stat = conn.prepareStatement(sql);
        
        stat.setInt(1, ra);
        stat.setString(2, nome);
        stat.setInt(3, idMateria);
        stat.setInt(4, this.id);
        
        int linhas = 0;

        linhas = stat.executeUpdate();
        
        return linhas;
    }
    
    public int exclui(Connection conn) throws SQLException{
        String sql = "DELETE FROM ALUNOS WHERE ID = ?";
        PreparedStatement stat = conn.prepareStatement(sql);

        stat.setInt(1   , this.id);
        
        int linhas = 0;

        linhas = stat.executeUpdate();
        
        return linhas;
    }
    
    private static int getProximoId(Connection conn) throws SQLException{
        String select = "SELECT MAX(ID) FROM ALUNOS";
        Statement stat = conn.createStatement();
        ResultSet rs = stat.executeQuery(select);
        
        int id = 0;
        if (rs.next())
            id = rs.getInt(1);
        
        id++;
        System.out.println(id);
        return id;
       
    }
    
    public static Aluno getAluno(Connection conn, int ra) throws SQLException{
        Aluno a = null;
        String select = "SELECT * FROM ALUNOS WHERE RA = " + ra;
        Statement stat = conn.createStatement();
        ResultSet rs = stat.executeQuery(select);
        
        while (rs.next()) {
            String nome = rs.getString("NOME");
            int id = rs.getInt("ID");
            a = new Aluno(id, ra, nome);
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
        
    public Materia getMateria(Connection conn) throws SQLException{
        if (this.materia != null)
            return this.materia;
        
        if (this.idMateria == 0)
            return null;
        
        return Materia.getMateria(conn, idMateria);
    }
    
    public void setMateria(Connection conn, Materia materia) throws SQLException{
        this.idMateria = materia.getId();
        this.materia = materia;
        this.altera(conn);        
    }
    
    
}

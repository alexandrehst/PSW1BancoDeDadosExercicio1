/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bancodedados;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author alexandretorres
 */
public class Materia {
    private int id;
    private String descricao;
    
    public Materia(int id, String descricao){
        this.id = id;
        this.descricao = descricao;
    }
    
    public Materia(String descricao){
        this.descricao = descricao;
    }
    
    public int insere(Connection conn) throws SQLException{
        String sql = "INSERT INTO MATERIAS(ID, DESCRICAO) VALUES (?,?)";
        PreparedStatement stat = conn.prepareStatement(sql);
        this.id = Materia.getProximoId(conn);
        stat.setInt(1, this.id);
        stat.setString(2, descricao);

        int linhas = 0;
        
        linhas = stat.executeUpdate();

        
        return linhas;
    }

    public int altera(Connection conn) throws SQLException{
        String sql = "UPDATE MATERIAS SET DESCRICAO = ? WHERE ID = ?";
        PreparedStatement stat = conn.prepareStatement(sql);

        stat.setString(1, descricao);
        //stat.setInt(2, ra);
        stat.setInt(2, this.id);
        
        int linhas = 0;

        linhas = stat.executeUpdate();
        
        return linhas;
    }
    
    public int exclui(Connection conn) throws SQLException{
        String sql = "DELETE FROM MATERIAS WHERE ID = ?";
        PreparedStatement stat = conn.prepareStatement(sql);

        stat.setInt(1, this.id);
        
        int linhas = 0;

        linhas = stat.executeUpdate();
        
        return linhas;
    }
    
    private static int getProximoId(Connection conn) throws SQLException{
        String select = "SELECT MAX(ID) FROM MATERIAS";
        Statement stat = conn.createStatement();
        ResultSet rs = stat.executeQuery(select);
        
        int id = 0;
        if (rs.next())
            id = rs.getInt(1);
        
        id++;
        System.out.println(id);
        return id;
       
    }
    
    public static Materia getMateria(Connection conn, int id) throws SQLException{
        Materia m = null;
        String select = "SELECT * FROM MATERIAS WHERE ID = " + id;
        Statement stat = conn.createStatement();
        ResultSet rs = stat.executeQuery(select);
        
        while (rs.next()) {
            String descricao = rs.getString("DESCRICAO");
            m = new Materia(id, descricao);
            break;
        }
        
        return m;                
    }

    public static ArrayList getMaterias(Connection conn) throws SQLException{
        ArrayList<Materia> materias = new ArrayList<>();

        String select = "SELECT * FROM MATERIAS";
        Statement stat = conn.createStatement();
        ResultSet rs = stat.executeQuery(select);
        
        while (rs.next()) {
            materias.add( 
                    new Materia(rs.getInt("ID"), rs.getString("DESCRICAO"))
            );
        }
        
        return materias;     
    }
    /**
     * @return the descricao
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * @param descricao the descricao to set
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public int getId(){
        return this.id;
    }
    
    
}

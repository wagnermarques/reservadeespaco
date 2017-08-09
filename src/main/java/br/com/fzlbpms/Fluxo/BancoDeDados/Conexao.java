/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fzlbpms.Fluxo.BancoDeDados;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    public static Connection getConexaoGeral() throws ClassNotFoundException, SQLException {
        //Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        //String comandoConexao = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb, *.accdb)};DBQ=";
        //String caminho = "//192.168.0.172/BancoDeDados/Database21.accdb";
        String caminho = "jdbc:sqlserver://192.168.0.151:1433;databaseName=Database21From172SQL;user=sa;password=adminfoipgg";
        return DriverManager.getConnection(caminho,"SA", "adminfoipgg");
    }

    public static Connection getConexaoSetor() throws ClassNotFoundException, SQLException {
        //Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  

        //String comandoConexao = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb, *.accdb)};DBQ=";
        //String caminho = "//192.168.0.80/Banco de Dados/Sistemas/SistemasAccess/Manutenção.accdb"; // Caminho do BD
         String comandoConexao = "jdbc:sqlserver://192.168.0.151:1433;databaseName=ManutençãoSQL;user=sa;password=adminfoipgg";  
        
        //return DriverManager.getConnection(comandoConexao + caminho, "Admin", "manut123");
        return DriverManager.getConnection(comandoConexao, "SA", "adminfoipgg");
    }

    public static Connection getConexaoLogin() throws ClassNotFoundException, SQLException {
        //Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
        //String comandoConexao = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb, *.accdb)};DBQ=";
        //String caminho = "//192.168.0.80/Banco de Dados/Aux1CAD.mdb"; // Caminho do BD
        String comandoConexao = "jdbc:sqlserver://192.168.0.151:1433;databaseName=Aux1CADFrom80SQL;user=sa;password=adminfoipgg"; 
        return DriverManager.getConnection(comandoConexao,"SA","adminfoipgg");
    }
    
    public static Connection getConexaoMysql() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/fluxo", "root", "ipgg");
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fzlbpms.Fluxo.Logicas;

import br.com.fzlbpms.Fluxo.BancoDeDados.OperacaoBD;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Alessandro
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        boolean ok = true;
        try {
            // TODO code application logic here
            OperacaoBD bd = new OperacaoBD();
            bd.atualizaAccessSolicitante();
        } catch (ClassNotFoundException ex) {
            ok = false;
            Logger.getLogger(NewMain.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            ok = false;
            Logger.getLogger(NewMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (ok){
        JOptionPane.showMessageDialog(null, "Sucesso");}
        else {
            JOptionPane.showMessageDialog(null, "Erro");
        }
       
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fzlbpms.Fluxo.Logicas;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ascosta
 */
public class Horario {
    
    private Time[] horario0 = {Time.valueOf("07:00:00"), Time.valueOf("08:00:00")};
    private Time[] horario1 = {Time.valueOf("08:00:00"), Time.valueOf("09:00:00")};
    private Time[] horario2 = {Time.valueOf("09:00:00"), Time.valueOf("10:00:00")};
    private Time[] horario3 = {Time.valueOf("10:00:00"), Time.valueOf("11:00:00")};
    private Time[] horario4 = {Time.valueOf("11:00:00"), Time.valueOf("12:00:00")};
    private Time[] horario5 = {Time.valueOf("12:00:00"), Time.valueOf("13:00:00")};
    private Time[] horario6 = {Time.valueOf("13:00:00"), Time.valueOf("14:00:00")};
    private Time[] horario7 = {Time.valueOf("14:00:00"), Time.valueOf("15:00:00")};
    private Time[] horario8 = {Time.valueOf("15:00:00"), Time.valueOf("16:00:00")};
    private Time[] horario9 = {Time.valueOf("16:00:00"), Time.valueOf("17:00:00")};
    private Time[] horario10 = {Time.valueOf("17:00:00"), Time.valueOf("18:00:00")};
    private Time[] horario11 = {Time.valueOf("18:00:00"), Time.valueOf("19:00:00")};
    private Object[] listaHorarios = {horario0, horario1, horario2, horario3, horario4, horario5,
        horario6, horario7, horario8, horario9, horario10, horario11};
    private List<Integer> listaHorariosDisponiveis;
    
    public Horario() {
        listaHorariosDisponiveis = new ArrayList<Integer>();
    }
    
    public Time[] getHorario(int horarioEscolhido) {
        return (Time[]) listaHorarios[horarioEscolhido];
    }
    
    public void setHorariosDisponíveis(int nHorario) {
        listaHorariosDisponiveis.add(nHorario);
    }
    
    public List<Integer> getHorariosDisponiveis() {
        return listaHorariosDisponiveis;
    }
    
    public void setAllFree() {
        int i;
        for (i = 0; i < 12; i++) {
            listaHorariosDisponiveis.add(i);
        }
    }
}

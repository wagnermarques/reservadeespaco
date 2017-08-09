/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fzlbpms.Fluxo.Logicas;

import java.sql.Time;
import java.util.Date;

/**
 *
 * @author cssantana
 */
public class SolicitacaoEspaco {

    String localSolicitado;
    String coordenacaoSolicitante;
    String nomeAtividade;
    String executorAtividade;
    String solicitante;
    Date dataAtividade;
    Date dataSolicitacao;
    Time horaSolicitacao;
    Time horaAlimentacao;
    Time horaAtividade;
    Time horaTermino;
    int numeroParticipantes;
    int semanas;
    int codigo;
    boolean alimentacao;
    boolean anoTodo;
    boolean aprovacao;
    boolean card1;
    boolean card2;
    boolean card3;
    boolean card4;
    boolean card5;
    boolean card6;
    boolean dataShow;
    boolean exclusao;
    boolean notebook;
    boolean caixasSom;
    boolean dvd;
    boolean televisao;
    boolean videoCassete;
    boolean mesaSom;
    boolean microfone;
    String observacaoCliente;
    String observacaoGestor;

    public String getObservacaoCliente() {
        return observacaoCliente;
    }

    public void setObservacaoCliente(String observacaoCliente) {
        this.observacaoCliente = observacaoCliente;
    }

    public String getObservacaoGestor() {
        return observacaoGestor;
    }

    public void setObservacaoGestor(String observacaoGestor) {
        this.observacaoGestor = observacaoGestor;
    }
    

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getSolicitante() {
        return solicitante;
    }

    public boolean isAprovacao() {
        return aprovacao;
    }

    public void setAprovacao(boolean aprovacao) {
        this.aprovacao = aprovacao;
    }

    public boolean isExclusao() {
        return exclusao;
    }

    public void setExclusao(boolean exclusao) {
        this.exclusao = exclusao;
    }

    public void setSolicitante(String solicitante) {
        this.solicitante = solicitante;
    }
            
    public Time getHoraAlimentacao() {
        return horaAlimentacao;
    }

    public void setHoraAlimentacao(Time horaAlimentacao) {
        this.horaAlimentacao = horaAlimentacao;
    }

    public boolean isCaixasSom() {
        return caixasSom;
    }

    public void setCaixasSom(boolean caixasSom) {
        this.caixasSom = caixasSom;
    }

    public boolean isDataShow() {
        return dataShow;
    }

    public void setDataShow(boolean dataShow) {
        this.dataShow = dataShow;
    }

    public boolean isDVD() {
        return dvd;
    }

    public void setDvd(boolean dvd) {
        this.dvd = dvd;
    }

    public boolean isFilmar() {
        return filmar;
    }

    public void setFilmar(boolean filmar) {
        this.filmar = filmar;
    }

    public boolean isFotografar() {
        return fotografar;
    }

    public void setFotografar(boolean fotografar) {
        this.fotografar = fotografar;
    }

    public boolean isMesaSom() {
        return mesaSom;
    }

    public void setMesaSom(boolean mesaSom) {
        this.mesaSom = mesaSom;
    }

    public boolean isMicrofone() {
        return microfone;
    }

    public void setMicrofone(boolean microfone) {
        this.microfone = microfone;
    }

    public boolean isNotebook() {
        return notebook;
    }

    public void setNotebook(boolean notebook) {
        this.notebook = notebook;
    }

    public boolean isTelevisao() {
        return televisao;
    }

    public void setTelevisao(boolean televisao) {
        this.televisao = televisao;
    }

    public boolean isVideoCassete() {
        return videoCassete;
    }

    public void setVideoCassete(boolean videoCassete) {
        this.videoCassete = videoCassete;
    }
    boolean fotografar;
    boolean filmar;

    public boolean isAlimentacao() {
        return alimentacao;
    }

    public void setAlimentacao(boolean alimentacao) {
        this.alimentacao = alimentacao;
    }

    public boolean isAnoTodo() {
        return anoTodo;
    }

    public void setAnoTodo(boolean anoTodo) {
        this.anoTodo = anoTodo;
    }

    public boolean isCard1() {
        return card1;
    }

    public void setCard1(boolean card1) {
        this.card1 = card1;
    }

    public boolean isCard2() {
        return card2;
    }

    public void setCard2(boolean card2) {
        this.card2 = card2;
    }

    public boolean isCard3() {
        return card3;
    }

    public void setCard3(boolean card3) {
        this.card3 = card3;
    }

    public boolean isCard4() {
        return card4;
    }

    public void setCard4(boolean card4) {
        this.card4 = card4;
    }

    public boolean isCard5() {
        return card5;
    }

    public void setCard5(boolean card5) {
        this.card5 = card5;
    }

    public boolean isCard6() {
        return card6;
    }

    public void setCard6(boolean card6) {
        this.card6 = card6;
    }

    public String getCoordenacaoSolicitante() {
        return coordenacaoSolicitante;
    }

    public void setCoordenacaoSolicitante(String coordenacaoSolicitante) {
        this.coordenacaoSolicitante = coordenacaoSolicitante;
    }

    public Date getDataAtividade() {
        return dataAtividade;
    }

    public void setDataAtividade(Date dataAtividade) {
        this.dataAtividade = dataAtividade;
    }

    public Date getDataSolicitacao() {
        return dataSolicitacao;
    }

    public void setDataSolicitacao(Date dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    public String getExecutorAtividade() {
        return executorAtividade;
    }

    public void setExecutorAtividade(String executorAtividade) {
        this.executorAtividade = executorAtividade;
    }

    public Time getHoraAtividade() {
        return horaAtividade;
    }

    public void setHoraAtividade(Time horaAtividade) {
        this.horaAtividade = horaAtividade;
    }

    public Time getHoraSolicitacao() {
        return horaSolicitacao;
    }

    public void setHoraSolicitacao(Time horaSolicitacao) {
        this.horaSolicitacao = horaSolicitacao;
    }

    public Time getHoraTermino() {
        return horaTermino;
    }

    public void setHoraTermino(Time horaTermino) {
        this.horaTermino = horaTermino;
    }

    public String getLocalSolicitado() {
        return localSolicitado;
    }

    public void setLocalSolicitado(String localSolicitado) {
        this.localSolicitado = localSolicitado;
    }

    public String getNomeAtividade() {
        return nomeAtividade;
    }

    public void setNomeAtividade(String nomeAtividade) {
        this.nomeAtividade = nomeAtividade;
    }

    public int getNumeroParticipantes() {
        return numeroParticipantes;
    }

    public void setNumeroParticipantes(int numeroParticipantes) {
        this.numeroParticipantes = numeroParticipantes;
    }

    public int getSemanas() {
        return semanas;
    }

    public void setSemanas(int semanas) {
        this.semanas = semanas;
    }
}

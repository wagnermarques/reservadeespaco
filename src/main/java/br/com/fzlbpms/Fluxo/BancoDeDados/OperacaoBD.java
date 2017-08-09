/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fzlbpms.Fluxo.BancoDeDados;

import br.com.fzlbpms.Fluxo.Logicas.SolicitacaoEspaco;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OperacaoBD {

    public List<String> getSetores() throws SQLException, ClassNotFoundException {
        conexao = Conexao.getConexaoSetor();
        String sql = "select NomeDoSetor as NomeDoSetor from dbo.Setores order by NomeDoSetor";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        ResultSet pesquisa = stmt.executeQuery();
        ResultSetMetaData metaData = pesquisa.getMetaData();
        int colCount = metaData.getColumnCount();        
        String col1Label = metaData.getColumnLabel(1);
        List<String> setores = new ArrayList<String>();        
        while (pesquisa.next()) {
            String setor = new String();
            setor = pesquisa.getString("NomeDoSetor");
          if(null != setor){
              boolean codicao = (!setor.equals("ATELIÊ 3")) && (!setor.equals("COPA 3º ANDAR"))
                    && (!setor.equals("COPA SUBSOLO")) && (!setor.equals("ENFERMAGEM 1º ANDAR"))
                    && (!setor.equals("FARMÁCIA - ALTO CUSTO")) && (!setor.equals("INFOCENTRO"))
                    && (!setor.equals("INFORMÁTICA - ALDO")) && (!setor.equals("INFORMÁTICA - DILSON"))
                    && (!setor.equals("INFORMÁTICA - REGULAÇÃO")) && (!setor.equals("INFORMÁTICA - WAGNER"))
                    && (!setor.equals("ORIENTAÇÃO DE EXAMES")) && (!setor.equals("P.A.B.X."))
                    && (!setor.equals("PORTARIA")) && (!setor.equals("PROTEC - MARCIA"))
                    && (!setor.equals("RADIOLOGIA")) && (!setor.equals("SAME - 1º ANDAR"))
                    && (!setor.equals("SAME - 2º ANDAR")) && (!setor.equals("SAME - 2º ANDAR (HALL ELEVADOR)"))
                    && (!setor.equals("SAME - AGENDAMENTO")) && (!setor.equals("SERVIÇO SOCIAL"))
                    && (!setor.equals("SUBFROTA")) && (!setor.equals("SUPRIMENTOS"));
            if (codicao) {
                setores.add(setor);
            }
          }else{
              System.out.println("Setor era nulo "+ setor);
          }//if setor nao nulo
        }//while
        pesquisa.close();
        stmt.close();
        conexao.close();
        return setores;
    }

    public boolean salvar(SolicitacaoEspaco solicitacao) throws ClassNotFoundException {
        boolean result = true;
        try {
            conexao = Conexao.getConexaoGeral();
            String campos = "Alimentacao,AnoTodo,Card1,Card2,Card3,Card4,Card5,Card6,"
                    + "CoordSolicitante,Data,DataDaSolicitacao,ExecutorDaAtividade,"
                    + "Hora,HoraDaSolicitacao,HoraTermino,LocalSolicitado,NomeDaAtividade,"
                    + "NumeroDeParticipantes,Semanas,DataShow,Notebook,CaixasDeSom,DVD,Televisao,"
                    + "VideoCassete,MesaDeSom,Microfone,Fotografar,Filmar,Solicitante,HorarioDaAlimentacao,"
                    + "ObservacoesCliente";
            String sql = "insert into AgendaFluxo(" + campos + ") values"
                    + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement stmt = conexao.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmt.setBoolean(1, solicitacao.isAlimentacao());
            stmt.setBoolean(2, solicitacao.isAnoTodo());
            stmt.setBoolean(3, solicitacao.isCard1());
            stmt.setBoolean(4, solicitacao.isCard2());
            stmt.setBoolean(5, solicitacao.isCard3());
            stmt.setBoolean(6, solicitacao.isCard4());
            stmt.setBoolean(7, solicitacao.isCard5());
            stmt.setBoolean(8, solicitacao.isCard6());
            stmt.setString(9, solicitacao.getCoordenacaoSolicitante());
            stmt.setDate(10, new java.sql.Date(solicitacao.getDataAtividade().getTime()));
            stmt.setDate(11, new java.sql.Date(solicitacao.getDataSolicitacao().getTime()));
            stmt.setString(12, solicitacao.getExecutorAtividade());
            stmt.setTime(13, solicitacao.getHoraAtividade());
            stmt.setTime(14, solicitacao.getHoraSolicitacao());
            stmt.setTime(15, solicitacao.getHoraTermino());
            stmt.setString(16, solicitacao.getLocalSolicitado());
            stmt.setString(17, solicitacao.getNomeAtividade());
            stmt.setInt(18, solicitacao.getNumeroParticipantes());
            stmt.setInt(19, solicitacao.getSemanas());
            stmt.setBoolean(20, solicitacao.isDataShow());
            stmt.setBoolean(21, solicitacao.isNotebook());
            stmt.setBoolean(22, solicitacao.isCaixasSom());
            stmt.setBoolean(23, solicitacao.isDVD());
            stmt.setBoolean(24, solicitacao.isTelevisao());
            stmt.setBoolean(25, solicitacao.isVideoCassete());
            stmt.setBoolean(26, solicitacao.isMesaSom());
            stmt.setBoolean(27, solicitacao.isMicrofone());
            stmt.setBoolean(28, solicitacao.isFotografar());
            stmt.setBoolean(29, solicitacao.isFilmar());
            stmt.setString(30, solicitacao.getSolicitante());
            if (solicitacao.isCard1() || solicitacao.isCard2() || solicitacao.isCard3()
                    || solicitacao.isCard4() || solicitacao.isCard5() || solicitacao.isCard6()) {
                stmt.setTime(31, solicitacao.getHoraAlimentacao());
            } else {
                stmt.setTime(31, null);
            }
            stmt.setString(32, solicitacao.getObservacaoCliente());
            stmt.executeUpdate();
            stmt.close();
            conexao.close();
        } catch (SQLException ex) {
            Logger.getLogger(OperacaoBD.class.getName()).log(Level.SEVERE, null, ex);
            result = false;
        }
        return result;
    }

    public boolean isDataDisponivel(String local, Date dataSolicitada, Time horaInicio, Time horaTermino) throws ClassNotFoundException, SQLException {
        boolean result = true;
        String sql = new String();
        conexao = Conexao.getConexaoGeral();
        PreparedStatement stmt;
        if (local.equals("LANCHONETE - EVENTOS")) {
            sql = "select Hora,HoraTermino from AgendaFluxo where (((LocalSolicitado = ?) or (LocalSolicitado = ?) or (LocalSolicitado = ?)) and (Data = ?) and (Aprovacao = ?))";
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, local);
            stmt.setString(2, "LANCHONETE - JOGOS");
            stmt.setString(3, "LANCHONETE - DANÇAS");
            stmt.setDate(4, new java.sql.Date(dataSolicitada.getTime()));
            stmt.setBoolean(5, true);
        } else if (local.equals("LANCHONETE - JOGOS") || local.equals("LANCHONETE - DANÇAS")) {
            sql = "select Hora,HoraTermino from AgendaFluxo where (((LocalSolicitado = ?) or (LocalSolicitado = ?)) and (Data = ?) and (Aprovacao = ?))";
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, local);
            stmt.setString(2, "LANCHONETE - EVENTOS");
            stmt.setDate(3, new java.sql.Date(dataSolicitada.getTime()));
            stmt.setBoolean(4, true);
        } else {
            sql = "select Hora,HoraTermino from AgendaFluxo where ((LocalSolicitado = ?) and (Data = ?) and (Aprovacao = ?))";
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, local);
            stmt.setDate(2, new java.sql.Date(dataSolicitada.getTime()));
            stmt.setBoolean(3, true);
        }
        ResultSet consulta = stmt.executeQuery();
        while (consulta.next()) {
            Time horaInicioAgendada = consulta.getTime("Hora");
            Time horaTerminoAgendada = consulta.getTime("HoraTermino");
            if (horaInicio.before(horaInicioAgendada) && horaTermino.after(horaInicioAgendada)) {
                result = false;
                break;
            } else if (horaInicio.after(horaInicioAgendada) && horaTermino.before(horaTerminoAgendada)) {
                result = false;
                break;
            } else if (horaInicio.before(horaTerminoAgendada) && horaTermino.after(horaTerminoAgendada)) {
                result = false;
                break;
            } else if (horaInicio.equals(horaInicioAgendada) || horaTermino.equals(horaTerminoAgendada)) {
                result = false;
                break;
            }
        }
        consulta.close();
        stmt.close();
        conexao.close();
        return result;
    }

    public List<SolicitacaoEspaco> getSolicitacoesPorMes(int mes, int ano, String local, boolean alimentacao, boolean equipamento) throws ParseException, ClassNotFoundException, SQLException {
        Date hoje = new Date();
        DateFormat formataMesAno = new SimpleDateFormat("MM");
        int mesAtual, anoAtual;
        mesAtual = Integer.parseInt(formataMesAno.format(hoje));
        formataMesAno = new SimpleDateFormat("yyyy");
        anoAtual = Integer.parseInt(formataMesAno.format(hoje));
        String stringDataPrimeiroDiaConsulta = null;
        int primeiroDiaConsulta;
        /*if ((mes == mesAtual) && (ano == anoAtual)) {
            formataMesAno = new SimpleDateFormat("dd");
            primeiroDiaConsulta = Integer.parseInt(formataMesAno.format(hoje));
            stringDataPrimeiroDiaConsulta = primeiroDiaConsulta + "/" + mes + "/" + ano;
        } else {*/
            stringDataPrimeiroDiaConsulta = "01/" + mes + "/" + ano;
        //}
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date dataPrimeiroDiaMes = df.parse(stringDataPrimeiroDiaConsulta);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dataPrimeiroDiaMes);
        int ultimoDia = calendar.getMaximum(Calendar.DAY_OF_MONTH);
        stringDataPrimeiroDiaConsulta = ultimoDia + "/" + mes + "/" + ano;
        Date ultimoDiaMes = df.parse(stringDataPrimeiroDiaConsulta);
        List<SolicitacaoEspaco> solicitacoes = new ArrayList<SolicitacaoEspaco>();
        conexao = Conexao.getConexaoGeral();
        String sql;
        PreparedStatement stmt = null;
        ResultSet consulta;
        if ("TODOS OS LOCAIS".equals(local)) {
            if (alimentacao && equipamento) {
                sql = "select * from AgendaFluxo where ((Data between ? and ?) and (Aprovacao = ?)) order by Data, Hora";
                stmt = conexao.prepareStatement(sql);
                stmt.setDate(1, new java.sql.Date(dataPrimeiroDiaMes.getTime()));
                stmt.setDate(2, new java.sql.Date(ultimoDiaMes.getTime()));
                stmt.setBoolean(3, true);
            } else if (alimentacao) {
                sql = "select * from AgendaFluxo where ((Data between ? and ?) and (Alimentacao = ?) and (Aprovacao = ?)) order by Data, Hora";
                stmt = conexao.prepareStatement(sql);
                stmt.setDate(1, new java.sql.Date(dataPrimeiroDiaMes.getTime()));
                stmt.setDate(2, new java.sql.Date(ultimoDiaMes.getTime()));
                stmt.setBoolean(3, alimentacao);
                stmt.setBoolean(4, true);
            } else {
                sql = "select * from AgendaFluxo where ((Data between ? and ?) and (Aprovacao = ?) and ("
                        + "(DVD = ?) or (DataShow = ?) or (Televisao = ?) or (Videocassete = ?) "
                        + "or (Microfone = ?) or (Notebook = ?) or (CaixasDeSom = ?) or (MesaDeSom = ?) "
                        + "or (Fotografar = ?) or (Filmar = ?))) order by Data, Hora";
                stmt = conexao.prepareStatement(sql);
                stmt.setDate(1, new java.sql.Date(dataPrimeiroDiaMes.getTime()));
                stmt.setDate(2, new java.sql.Date(ultimoDiaMes.getTime()));
                stmt.setBoolean(3, true);
                stmt.setBoolean(4, true);
                stmt.setBoolean(5, true);
                stmt.setBoolean(6, true);
                stmt.setBoolean(7, true);
                stmt.setBoolean(8, true);
                stmt.setBoolean(9, true);
                stmt.setBoolean(10, true);
                stmt.setBoolean(11, true);
                stmt.setBoolean(12, true);
                stmt.setBoolean(13, true);
            }
        } else {
            if (alimentacao && equipamento) {
                sql = "select * from AgendaFluxo where ((Data between ? and ?) and (Aprovacao = ?) and (LocalSolicitado = ?)) order by Data, Hora";
                stmt = conexao.prepareStatement(sql);
                stmt.setDate(1, new java.sql.Date(dataPrimeiroDiaMes.getTime()));
                stmt.setDate(2, new java.sql.Date(ultimoDiaMes.getTime()));
                stmt.setBoolean(3, true);
                stmt.setString(4, local);
            } else if (alimentacao) {
                sql = "select * from AgendaFluxo where ((Data between ? and ?) and (Alimentacao = ?) and (Aprovacao = ?) and (LocalSolicitado = ?)) order by Data, Hora";
                stmt = conexao.prepareStatement(sql);
                stmt.setDate(1, new java.sql.Date(dataPrimeiroDiaMes.getTime()));
                stmt.setDate(2, new java.sql.Date(ultimoDiaMes.getTime()));
                stmt.setBoolean(3, alimentacao);
                stmt.setBoolean(4, true);
                stmt.setString(5, local);
            } else if (equipamento) {
                sql = "select * from AgendaFluxo where ((Data between ? and ?) and (Aprovacao = ?) and ("
                        + "(DVD = ?) or (DataShow = ?) or (Televisao = ?) or (Videocassete = ?) "
                        + "or (Microfone = ?) or (Notebook = ?) or (CaixasDeSom = ?) or (MesaDeSom = ?) "
                        + "or (Fotografar = ?) or (Filmar = ?)) and (LocalSolicitado = ?)) order by Data, Hora";
                stmt = conexao.prepareStatement(sql);
                stmt.setDate(1, new java.sql.Date(dataPrimeiroDiaMes.getTime()));
                stmt.setDate(2, new java.sql.Date(ultimoDiaMes.getTime()));
                stmt.setBoolean(3, true);
                stmt.setBoolean(4, true);
                stmt.setBoolean(5, true);
                stmt.setBoolean(6, true);
                stmt.setBoolean(7, true);
                stmt.setBoolean(8, true);
                stmt.setBoolean(9, true);
                stmt.setBoolean(10, true);
                stmt.setBoolean(11, true);
                stmt.setBoolean(12, true);
                stmt.setBoolean(13, true);
                stmt.setString(14, local);
            }
        }
        consulta = stmt.executeQuery();
        while (consulta.next()) {
            SolicitacaoEspaco solicitacao = new SolicitacaoEspaco();
            solicitacao.setDataAtividade(consulta.getDate("Data"));

            solicitacao.setLocalSolicitado(consulta.getString("LocalSolicitado"));

            solicitacao.setHoraAtividade(consulta.getTime("Hora"));
            solicitacao.setHoraTermino(consulta.getTime("HoraTermino"));
            solicitacao.setHoraAlimentacao(consulta.getTime("HorarioDaAlimentacao"));


            solicitacao.setCard1(consulta.getBoolean("Card1"));
            solicitacao.setCard2(consulta.getBoolean("Card2"));
            solicitacao.setCard3(consulta.getBoolean("Card3"));
            solicitacao.setCard4(consulta.getBoolean("Card4"));
            solicitacao.setCard5(consulta.getBoolean("Card5"));
            solicitacao.setCard6(consulta.getBoolean("Card6"));

            solicitacao.setNomeAtividade(consulta.getString("NomeDaAtividade"));
            solicitacao.setExecutorAtividade(consulta.getString("ExecutorDaAtividade"));
            solicitacao.setNumeroParticipantes(consulta.getInt("NumeroDeParticipantes"));

            solicitacao.setCaixasSom(consulta.getBoolean("CaixasDeSom"));
            solicitacao.setDataShow(consulta.getBoolean("DataShow"));
            solicitacao.setDvd(consulta.getBoolean("DVD"));
            solicitacao.setFilmar(consulta.getBoolean("Filmar"));
            solicitacao.setFotografar(consulta.getBoolean("Fotografar"));
            solicitacao.setMesaSom(consulta.getBoolean("MesaDeSom"));
            solicitacao.setMicrofone(consulta.getBoolean("Microfone"));
            solicitacao.setNotebook(consulta.getBoolean("Notebook"));
            solicitacao.setTelevisao(consulta.getBoolean("Televisao"));
            solicitacao.setVideoCassete(consulta.getBoolean("VideoCassete"));


            solicitacoes.add(solicitacao);
        }
        consulta.close();
        stmt.close();
        conexao.close();
        return solicitacoes;
    }

    public int verificaLoginSenha(String login, String senha) throws ClassNotFoundException, SQLException {
        int result = 0;
        String sql = "select ID from TblAcesso where ((Login = ?) and (Senha = ?) )";
        conexao = Conexao.getConexaoLogin();
        PreparedStatement stmt = conexao.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, login);
        stmt.setString(2, senha);
        // and (MAC = ?) stmt.setBoolean(3, true);
        ResultSet consulta = stmt.executeQuery();
        if (consulta.last()) {
            result = consulta.getInt("ID");
        }
        consulta.close();
        stmt.close();
        conexao.close();
        return result;
    }

    public List<SolicitacaoEspaco> getSolicitacoesCliente(String nomeCliente) throws ClassNotFoundException, SQLException {
        List<SolicitacaoEspaco> solicitacoes = new ArrayList<SolicitacaoEspaco>();
        conexao = Conexao.getConexaoGeral();
        String sqlSolicitacao;
        PreparedStatement stmtSolicitacao;

        sqlSolicitacao = "select * from AgendaFluxo where (Solicitante = ?) order by Codigo";
        stmtSolicitacao = conexao.prepareStatement(sqlSolicitacao);
        stmtSolicitacao.setString(1, nomeCliente);
        
        ResultSet consulta = stmtSolicitacao.executeQuery();
        while (consulta.next()) {
            SolicitacaoEspaco solicitacao = new SolicitacaoEspaco();

            //Alimentacao
            if (consulta.getBoolean("Alimentacao")) {
                solicitacao.setAlimentacao(true);
                solicitacao.setCard1(consulta.getBoolean("Card1"));
                solicitacao.setCard2(consulta.getBoolean("Card2"));
                solicitacao.setCard3(consulta.getBoolean("Card3"));
                solicitacao.setCard4(consulta.getBoolean("Card4"));
                solicitacao.setCard5(consulta.getBoolean("Card5"));
                solicitacao.setCard6(consulta.getBoolean("Card6"));
            }

            //Equipamentos
            solicitacao.setCaixasSom(consulta.getBoolean("CaixasDeSom"));
            solicitacao.setDataShow(consulta.getBoolean("DataShow"));
            solicitacao.setDvd(consulta.getBoolean("DVD"));
            solicitacao.setFilmar(consulta.getBoolean("Filmar"));
            solicitacao.setFotografar(consulta.getBoolean("Fotografar"));
            solicitacao.setMesaSom(consulta.getBoolean("MesaDeSom"));
            solicitacao.setMicrofone(consulta.getBoolean("Microfone"));
            solicitacao.setNotebook(consulta.getBoolean("Notebook"));
            solicitacao.setTelevisao(consulta.getBoolean("Televisao"));
            solicitacao.setVideoCassete(consulta.getBoolean("VideoCassete"));

            //Dados Gerais
            solicitacao.setCodigo(consulta.getInt("Codigo"));

            solicitacao.setSolicitante(consulta.getString("Solicitante"));

            solicitacao.setCoordenacaoSolicitante(consulta.getString("CoordSolicitante"));
            solicitacao.setLocalSolicitado(consulta.getString("LocalSolicitado"));
            solicitacao.setNomeAtividade(consulta.getString("NomeDaAtividade"));
            solicitacao.setExecutorAtividade(consulta.getString("ExecutorDaAtividade"));
            solicitacao.setDataSolicitacao(consulta.getDate("DataDaSolicitacao"));
            solicitacao.setHoraSolicitacao(consulta.getTime("HoraDaSolicitacao"));
            solicitacao.setDataAtividade(consulta.getDate("Data"));
            solicitacao.setHoraAtividade(consulta.getTime("Hora"));
            solicitacao.setHoraTermino(consulta.getTime("HoraTermino"));
            solicitacao.setHoraAlimentacao(consulta.getTime("HorarioDaAlimentacao"));
            solicitacao.setNumeroParticipantes(consulta.getInt("NumeroDeParticipantes"));
            solicitacao.setAnoTodo(consulta.getBoolean("AnoTodo"));
            solicitacao.setSemanas(consulta.getInt("Semanas"));
            solicitacao.setAprovacao(consulta.getBoolean("Aprovacao"));
            solicitacao.setExclusao(consulta.getBoolean("Exclusao"));
            solicitacao.setObservacaoCliente(consulta.getString("ObservacoesCliente"));
            solicitacao.setObservacaoGestor(consulta.getString("Observacoes"));
            solicitacoes.add(solicitacao);

        }
        consulta.close();
        stmtSolicitacao.close();
        conexao.close();

        return solicitacoes;
    }

    public List<SolicitacaoEspaco> getSolicitacoesGestao(int mes, int ano, boolean aprovacao, boolean exclusao) throws ParseException, ClassNotFoundException, SQLException {
        String dataString = "01/" + mes + "/" + ano;
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date primeiroDiaMes = df.parse(dataString);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(primeiroDiaMes);
        int dia = calendar.getMaximum(Calendar.DAY_OF_MONTH);
        dataString = dia + "/" + mes + "/" + ano;
        Date ultimoDiaMes = df.parse(dataString);
        List<SolicitacaoEspaco> solicitacoes = new ArrayList<SolicitacaoEspaco>();
        conexao = Conexao.getConexaoGeral();
        String sqlSolicitacao;
        PreparedStatement stmtSolicitacao;
        if (aprovacao && exclusao) {
            sqlSolicitacao = "select * from AgendaFluxo where (Data between ? and ?) order by Codigo";
            stmtSolicitacao = conexao.prepareStatement(sqlSolicitacao);
            stmtSolicitacao.setDate(1, new java.sql.Date(primeiroDiaMes.getTime()));
            stmtSolicitacao.setDate(2, new java.sql.Date(ultimoDiaMes.getTime()));
        } else {
            sqlSolicitacao = "select * from AgendaFluxo where ((Data between ? and ?) and (Aprovacao = ?) and (Exclusao = ?)) order by Codigo";
            stmtSolicitacao = conexao.prepareStatement(sqlSolicitacao);
            stmtSolicitacao.setDate(1, new java.sql.Date(primeiroDiaMes.getTime()));
            stmtSolicitacao.setDate(2, new java.sql.Date(ultimoDiaMes.getTime()));
            stmtSolicitacao.setBoolean(3, aprovacao);
            stmtSolicitacao.setBoolean(4, exclusao);
        }
        ResultSet consulta = stmtSolicitacao.executeQuery();
        while (consulta.next()) {
            SolicitacaoEspaco solicitacao = new SolicitacaoEspaco();

            //Alimentacao
            if (consulta.getBoolean("Alimentacao")) {
                solicitacao.setAlimentacao(true);
                solicitacao.setCard1(consulta.getBoolean("Card1"));
                solicitacao.setCard2(consulta.getBoolean("Card2"));
                solicitacao.setCard3(consulta.getBoolean("Card3"));
                solicitacao.setCard4(consulta.getBoolean("Card4"));
                solicitacao.setCard5(consulta.getBoolean("Card5"));
                solicitacao.setCard6(consulta.getBoolean("Card6"));
            }

            //Equipamentos
            solicitacao.setCaixasSom(consulta.getBoolean("CaixasDeSom"));
            solicitacao.setDataShow(consulta.getBoolean("DataShow"));
            solicitacao.setDvd(consulta.getBoolean("DVD"));
            solicitacao.setFilmar(consulta.getBoolean("Filmar"));
            solicitacao.setFotografar(consulta.getBoolean("Fotografar"));
            solicitacao.setMesaSom(consulta.getBoolean("MesaDeSom"));
            solicitacao.setMicrofone(consulta.getBoolean("Microfone"));
            solicitacao.setNotebook(consulta.getBoolean("Notebook"));
            solicitacao.setTelevisao(consulta.getBoolean("Televisao"));
            solicitacao.setVideoCassete(consulta.getBoolean("VideoCassete"));

            //Dados Gerais
            solicitacao.setCodigo(consulta.getInt("Codigo"));

            solicitacao.setSolicitante(consulta.getString("Solicitante"));

            solicitacao.setCoordenacaoSolicitante(consulta.getString("CoordSolicitante"));
            solicitacao.setLocalSolicitado(consulta.getString("LocalSolicitado"));
            solicitacao.setNomeAtividade(consulta.getString("NomeDaAtividade"));
            solicitacao.setExecutorAtividade(consulta.getString("ExecutorDaAtividade"));
            solicitacao.setDataSolicitacao(consulta.getDate("DataDaSolicitacao"));
            solicitacao.setHoraSolicitacao(consulta.getTime("HoraDaSolicitacao"));
            solicitacao.setDataAtividade(consulta.getDate("Data"));
            solicitacao.setHoraAtividade(consulta.getTime("Hora"));
            solicitacao.setHoraTermino(consulta.getTime("HoraTermino"));
            solicitacao.setHoraAlimentacao(consulta.getTime("HorarioDaAlimentacao"));
            solicitacao.setNumeroParticipantes(consulta.getInt("NumeroDeParticipantes"));
            solicitacao.setAnoTodo(consulta.getBoolean("AnoTodo"));
            solicitacao.setSemanas(consulta.getInt("Semanas"));
            solicitacao.setAprovacao(consulta.getBoolean("Aprovacao"));
            solicitacao.setExclusao(consulta.getBoolean("Exclusao"));
            solicitacao.setObservacaoCliente(consulta.getString("ObservacoesCliente"));
            solicitacao.setObservacaoGestor(consulta.getString("Observacoes"));
            solicitacoes.add(solicitacao);

        }
        consulta.close();
        stmtSolicitacao.close();
        conexao.close();

        return solicitacoes;
    }

    public void atualizaBanco(SolicitacaoEspaco solicitacao, boolean aprovacao, boolean exclusao, int alterarMaisDeUmRegistro, String observacao) throws ClassNotFoundException, SQLException {
        if (solicitacao.isAnoTodo() && (alterarMaisDeUmRegistro == 0)) {
            atualizaRegistrosAnoTodo(solicitacao, aprovacao, exclusao, observacao);
        } else if ((!solicitacao.isAnoTodo()) && (solicitacao.getSemanas() > 1) && (alterarMaisDeUmRegistro == 0)) {
            atualizaRegistroSemanas(solicitacao, aprovacao, exclusao, observacao);
        } else {
            atualizaUmRegistro(solicitacao, aprovacao, exclusao);
        }
    }

    private void atualizaRegistroSemanas(SolicitacaoEspaco solicitacao, boolean aprovacao, boolean exclusao, String observacao) throws ClassNotFoundException, SQLException {
        String sql;
        PreparedStatement stmtUpdate;
        PreparedStatement stmtOrganiza;
        Connection conexaoUpdate;
        Connection conexaoOrganiza;
        sql = "update AgendaFluxo set Aprovacao = ?, Exclusao = ?, Observacoes = ? where "
                + "((DataDaSolicitacao = ?) and (HoraDaSolicitacao = ?) and "
                + "(LocalSolicitado = ?) and (Semanas >= ?))";
        conexaoUpdate = Conexao.getConexaoGeral();
        stmtUpdate = conexaoUpdate.prepareStatement(sql);
        stmtUpdate.setBoolean(1, aprovacao);
        stmtUpdate.setBoolean(2, exclusao);
        
        stmtUpdate.setString(3, observacao);
        stmtUpdate.setDate(4, new java.sql.Date(solicitacao.getDataSolicitacao().getTime()));
        stmtUpdate.setTime(5, solicitacao.getHoraSolicitacao());
        stmtUpdate.setString(6, solicitacao.getLocalSolicitado());
        stmtUpdate.setInt(7, 1);
        stmtUpdate.executeUpdate();
        stmtUpdate.close();
        conexaoUpdate.close();
        if (aprovacao) {
            sql = "select Codigo, Data, Hora, HoraTermino, LocalSolicitado from AgendaFluxo where "
                    + "((DataDaSolicitacao = ?) and (HoraDaSolicitacao = ?) and "
                    + "(LocalSolicitado = ?) "
                    + "and (Semanas >= ?))";
            conexaoOrganiza = Conexao.getConexaoGeral();
            stmtOrganiza = conexaoOrganiza.prepareStatement(sql);
            stmtOrganiza.setDate(1, new java.sql.Date(solicitacao.getDataSolicitacao().getTime()));
            stmtOrganiza.setTime(2, solicitacao.getHoraSolicitacao());
            stmtOrganiza.setString(3, solicitacao.getLocalSolicitado());
            stmtOrganiza.setInt(4, 1);
            ResultSet consulta = stmtOrganiza.executeQuery();
            while (consulta.next()) {
                SolicitacaoEspaco solicitacaoAtual = new SolicitacaoEspaco();
                solicitacaoAtual.setCodigo(consulta.getInt("Codigo"));
                solicitacaoAtual.setDataAtividade(consulta.getDate("Data"));
                solicitacaoAtual.setHoraAtividade(consulta.getTime("Hora"));
                solicitacaoAtual.setHoraTermino(consulta.getTime("HoraTermino"));
                solicitacaoAtual.setLocalSolicitado(consulta.getString("LocalSolicitado"));
                tiraDupliciade(solicitacaoAtual);
            }
            consulta.close();
            stmtOrganiza.close();
            conexaoOrganiza.close();
        }
    }

    private void atualizaRegistrosAnoTodo(SolicitacaoEspaco solicitacao, boolean aprovacao, boolean exclusao, String observacao) throws ClassNotFoundException, SQLException {
        String sql;
        PreparedStatement stmtUpdate;
        PreparedStatement stmtOrganiza;
        Connection conexaoUpdate;
        Connection conexaoOrganiza;
        sql = "update AgendaFluxo set Aprovacao = ?, Exclusao = ?, Observacoes = ? where "
                + "((DataDaSolicitacao = ?) and (HoraDaSolicitacao = ?) and "
                + "(LocalSolicitado = ?) and (AnoTodo = ?))";
        conexaoUpdate = Conexao.getConexaoGeral();
        stmtUpdate = conexaoUpdate.prepareStatement(sql);
        stmtUpdate.setBoolean(1, aprovacao);
        stmtUpdate.setBoolean(2, exclusao);
        
        stmtUpdate.setString(3, observacao);
        
        stmtUpdate.setDate(4, new java.sql.Date(solicitacao.getDataSolicitacao().getTime()));
        stmtUpdate.setTime(5, solicitacao.getHoraSolicitacao());
        stmtUpdate.setString(6, solicitacao.getLocalSolicitado());
        stmtUpdate.setBoolean(7, true);
        stmtUpdate.executeUpdate();
        stmtUpdate.close();
        conexaoUpdate.close();
        if (aprovacao) {
            sql = "select Codigo, Data, Hora, HoraTermino, LocalSolicitado from AgendaFluxo where "
                    + "((DataDaSolicitacao = ?) and (HoraDaSolicitacao = ?) and "
                    + "(LocalSolicitado = ?) "
                    + "and (AnoTodo = ?))";
            conexaoOrganiza = Conexao.getConexaoGeral();
            stmtOrganiza = conexaoOrganiza.prepareStatement(sql);
            stmtOrganiza.setDate(1, new java.sql.Date(solicitacao.getDataSolicitacao().getTime()));
            stmtOrganiza.setTime(2, solicitacao.getHoraSolicitacao());
            stmtOrganiza.setString(3, solicitacao.getLocalSolicitado());
            stmtOrganiza.setBoolean(4, solicitacao.isAnoTodo());
            ResultSet consulta = stmtOrganiza.executeQuery();
            while (consulta.next()) {
                SolicitacaoEspaco solicitacaoAtual = new SolicitacaoEspaco();
                solicitacaoAtual.setCodigo(consulta.getInt("Codigo"));
                solicitacaoAtual.setDataAtividade(consulta.getDate("Data"));
                solicitacaoAtual.setHoraAtividade(consulta.getTime("Hora"));
                solicitacaoAtual.setHoraTermino(consulta.getTime("HoraTermino"));
                solicitacaoAtual.setLocalSolicitado(consulta.getString("LocalSolicitado"));
                tiraDupliciade(solicitacaoAtual);
            }
            consulta.close();
            stmtOrganiza.close();
            conexaoOrganiza.close();
        }
    }
    
    public void atualizaObsCliente(String obs,int codigo) throws ClassNotFoundException, SQLException {
        conexao = Conexao.getConexaoGeral();
        String sql = "update AgendaFluxo set ObservacoesCliente = ? where Codigo = ?";
        PreparedStatement stmt =  conexao.prepareStatement(sql);
        stmt.setString(1, obs);
        stmt.setInt(2, codigo);
        stmt.executeUpdate();
        stmt.close();
        conexao.close();
    }

    private void atualizaUmRegistro(SolicitacaoEspaco solicitacao, boolean aprovacao, boolean exclusao) throws ClassNotFoundException, SQLException {
        String sql;
        PreparedStatement stmt;
        sql = "update AgendaFluxo set Aprovacao = ?, Exclusao = ? where Codigo = ?";
        conexao = Conexao.getConexaoGeral();
        stmt = conexao.prepareStatement(sql);
        stmt.setBoolean(1, aprovacao);
        stmt.setBoolean(2, exclusao);
        stmt.setInt(3, solicitacao.getCodigo());
        stmt.executeUpdate();
        stmt.close();
        conexao.close();
        if (aprovacao) {
            tiraDupliciade(solicitacao);
        }
    }

    private void tiraDupliciade(SolicitacaoEspaco solicitacao) throws ClassNotFoundException, SQLException {
        String sql = "update AgendaFluxo set Aprovacao = ?, Exclusao = ?, Observacoes = ? where "
                + "((((? < Hora) and (? > Hora)) or "
                + "((? >= Hora) and (? <= HoraTermino)) or "
                + "((? < HoraTermino) and (? > HoraTermino))) and "
                + "(LocalSolicitado = ?) and (Data = ?) and (Codigo <> ?))";
        Connection conexao2 = Conexao.getConexaoGeral();
        PreparedStatement stmt2 = conexao2.prepareStatement(sql);
        stmt2.setBoolean(1, false);
        stmt2.setBoolean(2, true);
        stmt2.setString(3, "Espaço previamente agendado para outra atividade");
        stmt2.setTime(4, solicitacao.getHoraAtividade());
        stmt2.setTime(5, solicitacao.getHoraTermino());
        stmt2.setTime(6, solicitacao.getHoraAtividade());
        stmt2.setTime(7, solicitacao.getHoraTermino());
        stmt2.setTime(8, solicitacao.getHoraAtividade());
        stmt2.setTime(9, solicitacao.getHoraTermino());
        stmt2.setString(10, solicitacao.getLocalSolicitado());
        stmt2.setDate(11, new java.sql.Date(solicitacao.getDataAtividade().getTime()));
        stmt2.setInt(12, solicitacao.getCodigo());
        stmt2.executeUpdate();
        stmt2.close();
        conexao2.close();
    }

    public void transfereDados() throws ClassNotFoundException, SQLException {
        String sqlQuery = "select * from AgendaFluxo where Codigo order by Codigo";
        String campos = "Alimentacao,AnoTodo,Card1,Card2,Card3,Card4,Card5,Card6,"
                + "CoordSolicitante,Data,DataDaSolicitacao,ExecutorDaAtividade,"
                + "Hora,HoraDaSolicitacao,HoraTermino,LocalSolicitado,NomeDaAtividade,"
                + "NumeroDeParticipantes,Semanas,DataShow,Notebook,CaixasDeSom,DVD,Televisao,"
                + "VideoCassete,MesaDeSom,Microfone,Fotografar,Filmar,Solicitante,HorarioDaAlimentacao,"
                + "Aprovacao,Exclusao,Observacoes";
        String sqlInsert = "insert into AgendaFluxo(" + campos + ") values"
                + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        conexao = Conexao.getConexaoGeral();
        Connection conexaoMysql = Conexao.getConexaoMysql();

        PreparedStatement stmtQuery = conexao.prepareStatement(sqlQuery);
        //stmtQuery.setInt(1, 2303);
        ResultSet consulta = stmtQuery.executeQuery();
        while (consulta.next()) {
            PreparedStatement stmt = conexaoMysql.prepareStatement(sqlInsert);
            stmt.setBoolean(1, consulta.getBoolean("Alimentacao"));//solicitacao.isAlimentacao);
            stmt.setBoolean(2, consulta.getBoolean("AnoTodo"));//solicitacao.isAnoTodo());
            stmt.setBoolean(3, consulta.getBoolean("Card1"));//solicitacao.isCard1());
            stmt.setBoolean(4, consulta.getBoolean("Card2"));//solicitacao.isCard2());
            stmt.setBoolean(5, consulta.getBoolean("Card3"));//solicitacao.isCard3());
            stmt.setBoolean(6, consulta.getBoolean("Card4"));//solicitacao.isCard4());
            stmt.setBoolean(7, consulta.getBoolean("Card5"));//solicitacao.isCard5());
            stmt.setBoolean(8, consulta.getBoolean("Card6"));//solicitacao.isCard6());
            stmt.setString(9, consulta.getString("CoordSolicitante"));//solicitacao.getCoordenacaoSolicitante());
            stmt.setDate(10, new java.sql.Date(consulta.getDate("Data").getTime()));
            stmt.setDate(11, new java.sql.Date(consulta.getDate("DataDaSolicitacao").getTime()));
            stmt.setString(12, consulta.getString("ExecutorDaAtividade"));//solicitacao.getExecutorAtividade());
            stmt.setTime(13, consulta.getTime("Hora"));//solicitacao.getHoraAtividade());
            stmt.setTime(14, consulta.getTime("HoraDaSolicitacao"));//solicitacao.getHoraSolicitacao());
            stmt.setTime(15, consulta.getTime("HoraTermino"));//solicitacao.getHoraTermino());
            stmt.setString(16, consulta.getString("LocalSolicitado"));//solicitacao.getLocalSolicitado());
            stmt.setString(17, consulta.getString("NomeDaAtividade"));//solicitacao.getNomeAtividade());
            stmt.setInt(18, consulta.getInt("NumeroDeParticipantes"));//solicitacao.getNumeroParticipantes());
            stmt.setInt(19, consulta.getInt("Semanas"));//solicitacao.getSemanas());
            stmt.setBoolean(20, consulta.getBoolean("DataShow"));//solicitacao.isDataShow());
            stmt.setBoolean(21, consulta.getBoolean("Notebook"));//solicitacao.isNotebook());
            stmt.setBoolean(22, consulta.getBoolean("CaixasDeSom"));//solicitacao.isCaixasSom());
            stmt.setBoolean(23, consulta.getBoolean("DVD"));//solicitacao.isDVD());
            stmt.setBoolean(24, consulta.getBoolean("Televisao"));//solicitacao.isTelevisao());
            stmt.setBoolean(25, consulta.getBoolean("VideoCassete"));//solicitacao.isVideoCassete());
            stmt.setBoolean(26, consulta.getBoolean("MesaDeSom"));//solicitacao.isMesaSom());
            stmt.setBoolean(27, consulta.getBoolean("Microfone"));//solicitacao.isMicrofone());
            stmt.setBoolean(28, consulta.getBoolean("Fotografar"));//solicitacao.isFotografar());
            stmt.setBoolean(29, consulta.getBoolean("Filmar"));//solicitacao.isFilmar());
            stmt.setString(30, consulta.getString("Solicitante"));//solicitacao.getIDSolicitante());
            Time alimentacao = consulta.getTime("HorarioDaAlimentacao");
            if (alimentacao != null) {
                stmt.setTime(31, alimentacao);//solicitacao.getHoraAlimentacao());
            } else {
                stmt.setTime(31, null);
            }
            stmt.setBoolean(32, consulta.getBoolean("Aprovacao"));
            stmt.setBoolean(33, consulta.getBoolean("Exclusao"));
            stmt.setString(34, consulta.getString("Observacoes"));
            stmt.executeUpdate();

            stmt.close();
        }
        consulta.close();
        stmtQuery.close();
        conexao.close();
        conexaoMysql.close();
    }

    public void atualizaAccessSolicitante() throws ClassNotFoundException, SQLException {
        String sqlQueryIDSolicitante = "select IDSolicitante, Codigo from AgendaFluxo order by Codigo";
        Connection conexaoQueryIDSolicitante = Conexao.getConexaoGeral();
        PreparedStatement stmtQueryIDSolicitante = conexaoQueryIDSolicitante.prepareStatement(sqlQueryIDSolicitante);
        ResultSet consultaIDSolicitante = stmtQueryIDSolicitante.executeQuery();

        String sqlUpdate = "update AgendaFluxo set Solicitante = ? where IDSolicitante = ?";
        Connection conexaoUpdate = Conexao.getConexaoGeral();

        while (consultaIDSolicitante.next()) {
            int id = consultaIDSolicitante.getInt("IDSolicitante");
            String nomeSolicitante = getNomeSolicitante(id);
            PreparedStatement stmt = conexaoUpdate.prepareStatement(sqlUpdate);
            stmt.setString(1, nomeSolicitante);
            stmt.setInt(2, id);
            stmt.executeUpdate();
            stmt.close();
        }

        consultaIDSolicitante.close();
        stmtQueryIDSolicitante.close();
        conexaoQueryIDSolicitante.close();
        conexaoUpdate.close();
    }

    public String getNomeSolicitante(int id) throws ClassNotFoundException, SQLException {
        String nome = null;
        String sql = "select Nome from TblAcesso where ID = ?";
        Connection conexaoAux1CAD = Conexao.getConexaoLogin();
        PreparedStatement stmt = conexaoAux1CAD.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet consulta = stmt.executeQuery();
        consulta.next();
        nome = consulta.getString("Nome");
        consulta.close();
        stmt.close();
        conexaoAux1CAD.close();
        return nome;
    }

    public void clearRegistros() throws ClassNotFoundException, SQLException {
        String sql = "update AgendaFluxo set Aprovacao = ?, Exclusao = ? where((Aprovacao = ?) or (Exclusao = ?))";
        conexao = Conexao.getConexaoGeral();
        PreparedStatement stmt = conexao.prepareStatement(sql);
        stmt.setBoolean(1, false);
        stmt.setBoolean(2, false);
        stmt.setBoolean(3, true);
        stmt.setBoolean(4, true);
        stmt.executeUpdate();
        stmt.close();
        conexao.close();
    }

    public void atualizaObsGestor(String observacao, int codigo) throws ClassNotFoundException, SQLException {
        String sql = "update AgendaFluxo set Observacoes = ? where Codigo = ?";
        Connection conexao1 = Conexao.getConexaoGeral();
        PreparedStatement stmt = conexao1.prepareStatement(sql);
        stmt.setString(1, observacao);
        stmt.setInt(2, codigo);
        stmt.executeUpdate();
        stmt.close();
        conexao1.close();
    }
    private Connection conexao;
}

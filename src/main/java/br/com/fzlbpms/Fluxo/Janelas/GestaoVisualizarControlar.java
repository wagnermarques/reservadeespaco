/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * GestaoVisualizarControlar.java
 *
 * Created on 11/01/2012, 07:12:41
 */
package br.com.fzlbpms.Fluxo.Janelas;

import br.com.fzlbpms.Fluxo.BancoDeDados.OperacaoBD;
import br.com.fzlbpms.Fluxo.Logicas.SolicitacaoEspaco;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


public class GestaoVisualizarControlar extends javax.swing.JDialog {

    /**
     * Creates new form GestaoVisualizarControlar
     */
    public GestaoVisualizarControlar(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    public GestaoVisualizarControlar(java.awt.Frame parent, boolean modal, boolean aprovado, boolean exclusao, int mes, int ano) throws ParseException, ClassNotFoundException, SQLException {
        super(parent, modal);
        initComponents();
        this.mes = mes;
        this.ano = ano;
        this.aprovacao = aprovado;
        this.excluido = exclusao;
        this.indice = 0;
        if (aprovado && (!exclusao)) {
            this.setTitle(this.getTitle() + "Atividades Agendadas");
        } else if ((!aprovado) && exclusao) {
            this.setTitle(this.getTitle() + "Atividades Negadas");
        } else if (aprovado && exclusao) {
            this.setTitle(this.getTitle() + "Todas as Atividades");
        } else {
            this.setTitle(this.getTitle() + "Atividades em Espera");
        }
        bd = new OperacaoBD();
        solicitacoes = bd.getSolicitacoesGestao(this.mes, this.ano, this.aprovacao, this.excluido);
        if (solicitacoes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Não há solicitações nas condições pedidas", "Visualizaçao de Solicitações", JOptionPane.ERROR_MESSAGE);
            dispose();
        } else {
            MostraDados();
        }
    }

    private void MostraDados() {
        if (indice == solicitacoes.size()) {
            indice = 0;
        } else if (indice == -1) {
            indice = solicitacoes.size() - 1;
        }
        jrbAceitar.setSelected(false);
        jrbNegar.setSelected(false);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        jlCodigo.setText("Código  " + solicitacoes.get(indice).getCodigo());
        jtfSolicitante.setText(solicitacoes.get(indice).getSolicitante());
        jtfCoordenacaoSol.setText(solicitacoes.get(indice).getCoordenacaoSolicitante());
        jtfLocal.setText(solicitacoes.get(indice).getLocalSolicitado());
        jtfNomeAtiv.setText(solicitacoes.get(indice).getNomeAtividade());
        jtfExecutorAtiv.setText(solicitacoes.get(indice).getExecutorAtividade());
        jtfDataDaSol.setText(df.format(solicitacoes.get(indice).getDataSolicitacao()));
        jtfHoraDaSol.setText(solicitacoes.get(indice).getHoraSolicitacao() + "");
        jtfData.setText(df.format(solicitacoes.get(indice).getDataAtividade()));
        jtfHoraIni.setText(solicitacoes.get(indice).getHoraAtividade() + "");
        jtfHoraFim.setText(solicitacoes.get(indice).getHoraTermino() + "");
        if (solicitacoes.get(indice).isAlimentacao()) {
            jtfHoraRef.setText(solicitacoes.get(indice).getHoraAlimentacao() + "");
            if (solicitacoes.get(indice).isCard1()) {
                jtfCardapio.setText("Nº1");
            } else if (solicitacoes.get(indice).isCard2()) {
                jtfCardapio.setText("Nº2");
            } else if (solicitacoes.get(indice).isCard3()) {
                jtfCardapio.setText("Nº3");
            } else if (solicitacoes.get(indice).isCard4()) {
                jtfCardapio.setText("Nº4");
            } else if (solicitacoes.get(indice).isCard5()) {
                jtfCardapio.setText("Nº5");
            } else if (solicitacoes.get(indice).isCard6()) {
                jtfCardapio.setText("Nº6");
            }
        } else {
            jtfCardapio.setText(null);
        }
        jtfParticipantes.setText(solicitacoes.get(indice).getNumeroParticipantes() + "");
        if (solicitacoes.get(indice).isAnoTodo()) {
            jtfAno.setText("Sim");
        } else {
            jtfAno.setText("Não");
        }
        jtaEquipamentos.setText("");
        if (solicitacoes.get(indice).isCaixasSom()) {
            jtaEquipamentos.append("Caixas de Som   ");
        }
        if (solicitacoes.get(indice).isDVD()) {
            jtaEquipamentos.append("DVD   ");
        }
        if (solicitacoes.get(indice).isDataShow()) {
            jtaEquipamentos.append("DataShow   ");
        }
        if (solicitacoes.get(indice).isFilmar()) {
            jtaEquipamentos.append("Video   ");
        }
        if (solicitacoes.get(indice).isFotografar()) {
            jtaEquipamentos.append("Foto   ");
        }
        if (solicitacoes.get(indice).isMesaSom()) {
            jtaEquipamentos.append("Mesa de Som   ");
        }
        if (solicitacoes.get(indice).isMicrofone()) {
            jtaEquipamentos.append("Microfone   ");
        }
        if (solicitacoes.get(indice).isNotebook()) {
            jtaEquipamentos.append("Notebook   ");
        }
        if (solicitacoes.get(indice).isTelevisao()) {
            jtaEquipamentos.append("Televisão   ");
        }
        if (solicitacoes.get(indice).isVideoCassete()) {
            jtaEquipamentos.append("Videocassete   ");
        }
        jtfSemanas.setText(solicitacoes.get(indice).getSemanas() + "");
        if (solicitacoes.get(indice).isAprovacao()) {
            jtfSituacao.setText("Confirmado");
            jrbAceitar.setSelected(true);
        } else if (solicitacoes.get(indice).isExclusao()) {
            jtfSituacao.setText("Negado");
            jrbNegar.setSelected(true);
        } else {
            jtfSituacao.setText("Em espera");
        }
        jtfObsCliente.setText(solicitacoes.get(indice).getObservacaoCliente());
        jtfObsGestor.setText(solicitacoes.get(indice).getObservacaoGestor());

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jtfSolicitante = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jtfCoordenacaoSol = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jtfLocal = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jtfNomeAtiv = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jtfExecutorAtiv = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jtfDataDaSol = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jtfData = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jtfHoraDaSol = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jtfHoraIni = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jtfHoraFim = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jtfHoraRef = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jtfCardapio = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jtfParticipantes = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jtfAno = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jtfSemanas = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtaEquipamentos = new javax.swing.JTextArea();
        jrbAceitar = new javax.swing.JRadioButton();
        jrbNegar = new javax.swing.JRadioButton();
        jLabel22 = new javax.swing.JLabel();
        jtfSituacao = new javax.swing.JTextField();
        jbAnterior = new javax.swing.JButton();
        jbProximo = new javax.swing.JButton();
        jbSair = new javax.swing.JButton();
        jlCodigo = new javax.swing.JLabel();
        jbUltimo = new javax.swing.JButton();
        jbPrimeiro = new javax.swing.JButton();
        jtfObsCliente = new javax.swing.JTextField();
        jtfObsGestor = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Controle de Agendamento - ");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("INSTITUTO PAULISTA DE GERIATRIA E GERONTOLOGIA");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("DIRETORIA TÉCNICA");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("\"JOSÉ ERMÍRIO DE MORAES\"");

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("Índice1_1.jpeg"))); // NOI18N

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("SECRETARIA DE ESTADO DA SAÚDE");

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("logo_ipgg2.gif"))); // NOI18N

        jLabel1.setText("Solicitante");

        jtfSolicitante.setEditable(false);

        jLabel2.setText("Coord. Sol.");

        jtfCoordenacaoSol.setEditable(false);

        jLabel9.setText("Local");

        jtfLocal.setEditable(false);

        jLabel10.setText("Nome Ativ.");

        jtfNomeAtiv.setEditable(false);

        jLabel11.setText("Executor");

        jtfExecutorAtiv.setEditable(false);

        jLabel12.setText("Data da Solicitação");

        jtfDataDaSol.setEditable(false);

        jLabel13.setText("Data da Atividade");

        jtfData.setEditable(false);

        jLabel14.setText("Hora da Sol.");

        jtfHoraDaSol.setEditable(false);

        jLabel15.setText("Hora do Início");

        jtfHoraIni.setEditable(false);

        jLabel16.setText("Hora do Término");

        jtfHoraFim.setEditable(false);

        jLabel17.setText("Hora Ref.");

        jtfHoraRef.setEditable(false);

        jLabel18.setText("Cardápio");

        jtfCardapio.setEditable(false);

        jLabel19.setText("Participantes");

        jtfParticipantes.setEditable(false);

        jLabel20.setText("Evento Para o Ano");

        jtfAno.setEditable(false);

        jLabel21.setText("Semanas");

        jtfSemanas.setEditable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Equipamentos"));

        jtaEquipamentos.setColumns(20);
        jtaEquipamentos.setEditable(false);
        jtaEquipamentos.setLineWrap(true);
        jtaEquipamentos.setRows(5);
        jtaEquipamentos.setWrapStyleWord(true);
        jScrollPane1.setViewportView(jtaEquipamentos);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jrbAceitar.setText("Aceitar");
        jrbAceitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrbAceitarActionPerformed(evt);
            }
        });

        jrbNegar.setText("Negar");
        jrbNegar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrbNegarActionPerformed(evt);
            }
        });

        jLabel22.setText("Situação");

        jtfSituacao.setEditable(false);

        jbAnterior.setText("Anterior");
        jbAnterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAnteriorActionPerformed(evt);
            }
        });

        jbProximo.setText("Próximo");
        jbProximo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbProximoActionPerformed(evt);
            }
        });

        jbSair.setText("Sair");
        jbSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSairActionPerformed(evt);
            }
        });

        jlCodigo.setText("Código      ");

        jbUltimo.setText("Último");
        jbUltimo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbUltimoActionPerformed(evt);
            }
        });

        jbPrimeiro.setText("Primeiro");
        jbPrimeiro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbPrimeiroActionPerformed(evt);
            }
        });

        jtfObsCliente.setEditable(false);

        jLabel23.setText("Obs Cliente");

        jLabel24.setText("Obs Gestor");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel1)
                                            .addComponent(jLabel9)
                                            .addComponent(jLabel11))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jtfLocal)
                                            .addComponent(jtfSolicitante)
                                            .addComponent(jtfExecutorAtiv)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel13)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jtfData, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel18)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jtfCardapio, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel19)
                                            .addComponent(jLabel15))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jtfHoraIni)
                                            .addComponent(jtfParticipantes, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(16, 16, 16))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel10))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jtfNomeAtiv)
                                    .addComponent(jtfCoordenacaoSol)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel16)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel20))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jtfDataDaSol, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jtfHoraFim)
                                    .addComponent(jtfAno, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel14)
                                    .addComponent(jLabel17)
                                    .addComponent(jLabel21))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jtfHoraRef)
                                    .addComponent(jtfHoraDaSol)
                                    .addComponent(jtfSemanas, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jrbAceitar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jrbNegar))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel22)
                                    .addComponent(jLabel23)
                                    .addComponent(jLabel24))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jtfObsGestor, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jtfObsCliente, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jtfSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(82, 82, 82)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6))
                                .addGap(111, 111, 111)
                                .addComponent(jLabel8))
                            .addComponent(jlCodigo))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jbPrimeiro)
                        .addGap(18, 18, 18)
                        .addComponent(jbUltimo)
                        .addGap(18, 18, 18)
                        .addComponent(jbAnterior)
                        .addGap(18, 18, 18)
                        .addComponent(jbProximo)
                        .addGap(18, 18, 18)
                        .addComponent(jbSair)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addComponent(jlCodigo)
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jtfSolicitante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jtfCoordenacaoSol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jtfLocal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(jtfNomeAtiv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jtfExecutorAtiv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(jtfDataDaSol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(jtfHoraDaSol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jtfData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfHoraIni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(jtfHoraFim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(jtfHoraRef, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jtfCardapio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19)
                    .addComponent(jtfParticipantes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20)
                    .addComponent(jtfAno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21)
                    .addComponent(jtfSemanas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel22)
                            .addComponent(jtfSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jtfObsCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel23))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jtfObsGestor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel24))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jrbAceitar)
                            .addComponent(jrbNegar))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbAnterior)
                    .addComponent(jbProximo)
                    .addComponent(jbSair)
                    .addComponent(jbUltimo)
                    .addComponent(jbPrimeiro))
                .addContainerGap())
        );

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-818)/2, (screenSize.height-572)/2, 818, 572);
    }// </editor-fold>//GEN-END:initComponents

    
    private void jbProximoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbProximoActionPerformed
        // TODO add your handling code here:
        if (solicitacoes.isEmpty()) {
            dispose();
        } else {

            int alterarMaisDeUmRegistro = 1;
            if (jtfObsGestor.getText() == null ? solicitacoes.get(indice).getObservacaoGestor() != null : !jtfObsGestor.getText().equals(solicitacoes.get(indice).getObservacaoGestor())) {
                try {
                    bd.atualizaObsGestor(jtfObsGestor.getText(), solicitacoes.get(indice).getCodigo());
                    solicitacoes.get(indice).setObservacaoGestor(jtfObsGestor.getText());
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(GestaoVisualizarControlar.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(GestaoVisualizarControlar.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if ((jrbAceitar.isSelected() != solicitacoes.get(indice).isAprovacao())
                    || (jrbNegar.isSelected() != solicitacoes.get(indice).isExclusao())) {
                if (solicitacoes.get(indice).isAnoTodo() || (solicitacoes.get(indice).getSemanas() > 1)) {
                    alterarMaisDeUmRegistro = JOptionPane.showConfirmDialog(this, "Deseja alterar os demais registros");
                }
                Atualizar(alterarMaisDeUmRegistro);
            } else {
                indice++;
            }
            if (solicitacoes.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Não há solicitações nas condições pedidas", "Visualizaçao de Solicitações", JOptionPane.ERROR_MESSAGE);
                dispose();
            } else {
                MostraDados();
            }
        }
    }//GEN-LAST:event_jbProximoActionPerformed

    private void jbAnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAnteriorActionPerformed
        // TODO add your handling code here:
        if (solicitacoes.isEmpty()) {
            dispose();
        } else {

            int alterarMaisDeUmRegistro = 1;
            if (jtfObsGestor.getText() == null ? solicitacoes.get(indice).getObservacaoGestor() != null : !jtfObsGestor.getText().equals(solicitacoes.get(indice).getObservacaoGestor())) {
                try {
                    bd.atualizaObsGestor(jtfObsGestor.getText(), solicitacoes.get(indice).getCodigo());
                    solicitacoes.get(indice).setObservacaoGestor(jtfObsGestor.getText());
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(GestaoVisualizarControlar.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(GestaoVisualizarControlar.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if ((jrbAceitar.isSelected() != solicitacoes.get(indice).isAprovacao())
                    || (jrbNegar.isSelected() != solicitacoes.get(indice).isExclusao())) {
                if (solicitacoes.get(indice).isAnoTodo() || (solicitacoes.get(indice).getSemanas() > 1)) {
                    alterarMaisDeUmRegistro = JOptionPane.showConfirmDialog(this, "Deseja alterar os demais registros");
                }
                Atualizar(alterarMaisDeUmRegistro);
            } else {
                indice--;
            }
            if (solicitacoes.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Não há solicitações nas condições pedidas", "Visualizaçao de Solicitações", JOptionPane.ERROR_MESSAGE);
                dispose();
            } else {
                MostraDados();
            }
        }
    }//GEN-LAST:event_jbAnteriorActionPerformed

    private void jrbAceitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrbAceitarActionPerformed
        // TODO add your handling code here:
        if (jrbAceitar.isSelected()) {
            jrbNegar.setSelected(false);
        }
    }//GEN-LAST:event_jrbAceitarActionPerformed

    private void jrbNegarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrbNegarActionPerformed
        // TODO add your handling code here:
        if (jrbNegar.isSelected()) {
            jrbAceitar.setSelected(false);
        }
    }//GEN-LAST:event_jrbNegarActionPerformed

    private void jbSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSairActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_jbSairActionPerformed

    private void jbPrimeiroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbPrimeiroActionPerformed
        // TODO add your handling code here:
        indice = 0;
        MostraDados();
    }//GEN-LAST:event_jbPrimeiroActionPerformed

    private void jbUltimoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbUltimoActionPerformed
        // TODO add your handling code here:
        indice = solicitacoes.size() - 1;
        MostraDados();
    }//GEN-LAST:event_jbUltimoActionPerformed

    private void Atualizar(int alterarMaisDeUmRegistro) {
        try {
            int continua = 0;
            boolean isEspacoReservado = false;
            int indiceLocal = indice;
            if (alterarMaisDeUmRegistro == 0) {
                if (jrbAceitar.isSelected()) {
                    indiceLocal = indice;
                    for (indiceLocal = indice; solicitacoes.get(indiceLocal).getNomeAtividade().equals(solicitacoes.get(indice).getNomeAtividade())
                            && solicitacoes.get(indiceLocal).getDataSolicitacao().equals(solicitacoes.get(indice).getDataSolicitacao())
                            && solicitacoes.get(indiceLocal).getHoraSolicitacao().equals(solicitacoes.get(indice).getHoraSolicitacao())
                            && (!isEspacoReservado) && (indiceLocal < solicitacoes.size()-1); indiceLocal++) {
                        isEspacoReservado = !bd.isDataDisponivel(solicitacoes.get(indiceLocal).getLocalSolicitado(), solicitacoes.get(indiceLocal).getDataAtividade(), solicitacoes.get(indiceLocal).getHoraAtividade(), solicitacoes.get(indiceLocal).getHoraTermino());
                    }
                    if (isEspacoReservado) {
                        continua = JOptionPane.showConfirmDialog(this, "Já existe um evento agendado, deseja substituí-lo");
                    }

                }
            } else {
                if (jrbAceitar.isSelected()) {
                    isEspacoReservado = !bd.isDataDisponivel(solicitacoes.get(indiceLocal).getLocalSolicitado(), solicitacoes.get(indiceLocal).getDataAtividade(), solicitacoes.get(indiceLocal).getHoraAtividade(), solicitacoes.get(indiceLocal).getHoraTermino());
                    if (isEspacoReservado) {
                        continua = JOptionPane.showConfirmDialog(this, "Já existe um evento agendado, deseja substituí-lo");
                    }
                }
            }
            if (continua == 0) {
                bd.atualizaBanco(solicitacoes.get(indice), jrbAceitar.isSelected(), jrbNegar.isSelected(), alterarMaisDeUmRegistro, jtfObsGestor.getText());
                solicitacoes = bd.getSolicitacoesGestao(mes, ano, aprovacao, excluido);
                indice = 0;
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GestaoVisualizarControlar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(GestaoVisualizarControlar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(GestaoVisualizarControlar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GestaoVisualizarControlar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GestaoVisualizarControlar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GestaoVisualizarControlar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GestaoVisualizarControlar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the dialog
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                GestaoVisualizarControlar dialog = new GestaoVisualizarControlar(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    private boolean aprovacao;
    private boolean excluido;
    private List<SolicitacaoEspaco> solicitacoes;
    private int indice;
    private int mes;
    private int ano;
    private OperacaoBD bd;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbAnterior;
    private javax.swing.JButton jbPrimeiro;
    private javax.swing.JButton jbProximo;
    private javax.swing.JButton jbSair;
    private javax.swing.JButton jbUltimo;
    private javax.swing.JLabel jlCodigo;
    private javax.swing.JRadioButton jrbAceitar;
    private javax.swing.JRadioButton jrbNegar;
    private javax.swing.JTextArea jtaEquipamentos;
    private javax.swing.JTextField jtfAno;
    private javax.swing.JTextField jtfCardapio;
    private javax.swing.JTextField jtfCoordenacaoSol;
    private javax.swing.JTextField jtfData;
    private javax.swing.JTextField jtfDataDaSol;
    private javax.swing.JTextField jtfExecutorAtiv;
    private javax.swing.JTextField jtfHoraDaSol;
    private javax.swing.JTextField jtfHoraFim;
    private javax.swing.JTextField jtfHoraIni;
    private javax.swing.JTextField jtfHoraRef;
    private javax.swing.JTextField jtfLocal;
    private javax.swing.JTextField jtfNomeAtiv;
    private javax.swing.JTextField jtfObsCliente;
    private javax.swing.JTextField jtfObsGestor;
    private javax.swing.JTextField jtfParticipantes;
    private javax.swing.JTextField jtfSemanas;
    private javax.swing.JTextField jtfSituacao;
    private javax.swing.JTextField jtfSolicitante;
    // End of variables declaration//GEN-END:variables
}

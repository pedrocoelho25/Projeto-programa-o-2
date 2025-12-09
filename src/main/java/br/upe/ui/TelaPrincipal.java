package br.upe.ui;

import br.upe.controller.TarefaControlador;
import br.upe.model.Tarefa;
import br.upe.model.TarefaTableModel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.MouseEvent; //Classes usadas para as funcionalidades
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TelaPrincipal {
    private JPanel pnlMain;
    private JTextField txtDescricaoTarefa;
    private JButton btnAdicionarTarefa;
    private JPanel pnlAdicionar;
    private JTable tblTarefas;
    private JCheckBox chkExibirFinalizadas;

    private List<Tarefa> tarefas;

    private TarefaControlador controlador;

    public TelaPrincipal() {
        super();
        tarefas = new ArrayList<>();

        
        //MUDANÇAS



        //FUNCIONALIDADE NOVA: ocultar botão adicionar
        txtDescricaoTarefa.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                btnAdicionarTarefa.setEnabled(!txtDescricaoTarefa.getText().isEmpty());
            }
        });

        
        
        //botão enter sem key Listener
        txtDescricaoTarefa.addActionListener(e->{
            if(!txtDescricaoTarefa.getText().isEmpty()) {
                adicionarTarefa(txtDescricaoTarefa.getText());
                txtDescricaoTarefa.setText("");
            }
        });


        //existente
        btnAdicionarTarefa.addActionListener(e -> { 
            adicionarTarefa(txtDescricaoTarefa.getText()); 
            txtDescricaoTarefa.setText("");
        });
        chkExibirFinalizadas.addActionListener(e -> {
            boolean selecionado = ((JCheckBox) e.getSource()).isSelected();
            controlador.exibirFinalizadas(selecionado);
        });

    }

    private void adicionarTarefa(String texto) {
        Tarefa tarefa = new Tarefa(texto, tarefas.size());
        controlador.adicionarTarefaAtiva(tarefa); 
        tblTarefas.revalidate(); 
        tblTarefas.repaint(); 
    }
    //mudanças
    //FUNCIONALIDADE NOVA: Deletar tarefa
    public void deletarTarefa(int indice){
        if(indice == -1){
            JOptionPane.showMessageDialog(
                    null,
                    "Por favor selecione uma tarefa válida!",
                    "AVISO",JOptionPane.WARNING_MESSAGE
            );
           return;
        }
        TarefaTableModel tabela = controlador.getTarefaTableModel();
        Tarefa tarefa = tabela.getTarefa(indice);
        int opcao = JOptionPane.showConfirmDialog(
                null, "Deseja deletar tarefa: "+ tarefa.getDescricao() + "?",
                "Deletar tarefa", JOptionPane.YES_NO_OPTION
        );
        if (opcao == JOptionPane.YES_OPTION) {
            controlador.removerTarefa(indice);
            tabela.fireTableDataChanged();
        }


    }

    public JPanel getPnlMain() {
        return this.pnlMain;
    }

    private void createUIComponents() {
        controlador = new TarefaControlador();
        tblTarefas = new JTable(controlador.getTarefaTableModel());
        tblTarefas.getColumnModel().getColumn(0).setMaxWidth(20);
        //Listener para o click do mouse
        tblTarefas.addMouseListener(new java.awt.event.MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2){
                int indice = tblTarefas.getSelectedRow();
                deletarTarefa(indice);
                }

            }
        });
    }

}

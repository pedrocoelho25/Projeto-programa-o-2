package br.upe.ui;

import br.upe.controller.TarefaControlador;
import br.upe.model.Tarefa;
import br.upe.model.TarefaTableModel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.MouseEvent;

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
        //mudanças
        txtDescricaoTarefa.addActionListener(e->{
            adicionarTarefa(txtDescricaoTarefa.getText());
            txtDescricaoTarefa.setText("");

        });


        //existente
        btnAdicionarTarefa.addActionListener(e -> { // funçao que faz com que o computador "ouça" oq esta escrito
            adicionarTarefa(txtDescricaoTarefa.getText());  //pegar texto do atributo colocar no campo de texto
            txtDescricaoTarefa.setText(""); //limpar o texto do campo de texto
        });
        chkExibirFinalizadas.addActionListener(e -> {
            boolean selecionado = ((JCheckBox) e.getSource()).isSelected();
            controlador.exibirFinalizadas(selecionado);
        });

    }

    private void adicionarTarefa(String texto) {
        Tarefa tarefa = new Tarefa(texto, tarefas.size()); //cria uma nova tarefa da classe Tarefa e ver o tamanho do texto
        controlador.adicionarTarefaAtiva(tarefa); // controla toda a função de adicionar a tarefa
        tblTarefas.revalidate(); // reavalia os componentes do painel e suas dimensões
        tblTarefas.repaint(); //reescreve texto no campo
    }
    //mudanças
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
        JOptionPane.showConfirmDialog(
                null, "Deseja deletar tarefa: "+ tarefa.getDescricao() + "?",
                "Deletar tarefa", JOptionPane.YES_NO_OPTION
        );

//        controlador.removerTarefa(tarefa);
        tabela.fireTableDataChanged();

    }

    public JPanel getPnlMain() {
        return this.pnlMain;
    }

    private void createUIComponents() {
        controlador = new TarefaControlador();
        tblTarefas = new JTable(controlador.getTarefaTableModel());
        tblTarefas.getColumnModel().getColumn(0).setMaxWidth(20);
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

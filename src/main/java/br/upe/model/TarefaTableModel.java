package br.upe.model;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

public class TarefaTableModel extends AbstractTableModel {
    private List<Tarefa> tarefasFinalizadas;
    private List<Tarefa> tarefasAtivas;

    private boolean exibirFinalizadas;

    public TarefaTableModel() {
        tarefasAtivas = new ArrayList<>();
        tarefasFinalizadas = new ArrayList<>();
    }

    @Override
    public int getRowCount() {
        return getTodasAsTarefas().size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Tarefa tarefa = getTodasAsTarefas().get(rowIndex);
        switch (columnIndex) {
            case 0 : return tarefa.isFinalizada();
            case 1 : return tarefa.getDescricao();
        }
        return null;
    }
    public Class getColumnClass(int c) {
        switch (c) {
            case 0 : return Boolean.class;
            case 1 : return String.class;
        }
        return null;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 0;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Tarefa tarefa = getTodasAsTarefas().get(rowIndex);
        Boolean novoStatus = (Boolean) aValue;
        if (novoStatus){
            tarefasAtivas.remove(tarefa);
            tarefasFinalizadas.add(tarefa);
        }else{
            tarefasAtivas.add(tarefa);
            tarefasFinalizadas.remove(tarefa);
        }
        this.fireTableDataChanged();
    }


    public void setExibirFinalizadas(boolean selecionado) {
        this.exibirFinalizadas = selecionado;
        if (this.exibirFinalizadas) {
            this.tarefasAtivas.addAll(this.tarefasFinalizadas);
        } else {
            this.tarefasAtivas.removeAll(this.tarefasFinalizadas);
        }
        this.fireTableDataChanged();
    }
    //mudanças
    private List<Tarefa> getTodasAsTarefas(){
        if (this.exibirFinalizadas){
            ArrayList<Tarefa> todas = new ArrayList<Tarefa>(); 
            todas.addAll(tarefasAtivas); 
            todas.addAll(tarefasFinalizadas); 
            return todas;
        }
        return tarefasAtivas;
    }

    public Tarefa getTarefa(int indice){
        List<Tarefa> tarefas = getTodasAsTarefas();
        if(indice >= 0 && indice < tarefas.size()){
            return tarefas.get(indice);
        }

        return null;
    }
///


    public List<Tarefa> getTarefasFinalizadas() {
        return tarefasFinalizadas;
    }

    public void setTarefasFinalizadas(List<Tarefa> tarefasFinalizadas) {
        this.tarefasFinalizadas = tarefasFinalizadas;

    }

    public List<Tarefa> getTarefasAtivas() {
        return tarefasAtivas;
    }

    public void setTarefasAtivas(List<Tarefa> tarefasAtivas) {
        this.tarefasAtivas = tarefasAtivas;
    }

    public boolean isExibirFinalizadas() {
        return exibirFinalizadas;
    }

}
